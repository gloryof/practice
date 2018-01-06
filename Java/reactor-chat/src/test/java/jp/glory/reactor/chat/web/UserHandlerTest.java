package jp.glory.reactor.chat.web;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.argThat;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.UserNotify;
import jp.glory.reactor.chat.usecase.user.AddUser;
import jp.glory.reactor.chat.usecase.user.LeaveUser;
import jp.glory.reactor.chat.web.request.LeaveRequest;
import jp.glory.reactor.chat.web.request.UserRequest;
import jp.glory.reactor.chat.web.response.UserDetailResponse;
import jp.glory.reactor.chat.web.response.UsersResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserHandlerTest {

    private UserHandler sut = null;

    private AddUser mockAddUser;
    private LeaveUser mockLeaveUser;
    private UserNotify mockNotify;

    private WebTestClient client = null;

    @BeforeEach
    void setUp() {

        mockAddUser = mock(AddUser.class);
        mockLeaveUser = mock(LeaveUser.class);
        mockNotify = mock(UserNotify.class);

        sut = new UserHandler(mockAddUser, mockLeaveUser, mockNotify);

        final Routing routing = new Routing();
        client = WebTestClient.bindToRouterFunction(routing.routeSetting(mock(MessageHandler.class), sut)).build();
    }

    @Nested
    @DisplayName("getUsersのテスト")
    class TestGetUsers {

        private User user1 = null;
        private User user2 = null;

        @BeforeEach
        void setUp() {

            user1 = new User(new Name("test-user-1"));
            user2 = new User(new Name("test-user-2"));

            doReturn(Flux.just(List.of(user1), List.of(user1, user2))).when(mockNotify).getFlux();
        }

        @Test
        @DisplayName("ステータスはOK、Content-Typeはtext/event-stream、Userリストの中身が返る")
        void assertResponse() {

            FluxExchangeResult<UsersResponse> actualResult =
                    client.get().uri("/users")
                        .accept(MediaType.TEXT_EVENT_STREAM)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(UsersResponse.class);

            Flux<UsersResponse> flux = actualResult.getResponseBody();

            StepVerifier.create(flux)
                .expectNextMatches(new UseResponseMatcher(List.of(user1)))
                .expectNextMatches(new UseResponseMatcher(List.of(user1, user2)))
                .thenCancel()
                .verify();
        }
    }

    @Nested
    @DisplayName("addUserのテスト")
    class TestAddUser {

        @Test
        @DisplayName("addUserユースケースが実行される")
        void testExecution() {

            final UserRequest request = new UserRequest();
            request.setName("test-user");

            client.post().uri("/users/add")
                .body(Mono.just(request), UserRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

            verify(mockAddUser, times(1)).accept(argThat(new UserMatcher("test-user")));
        }
    }

    @Nested
    @DisplayName("leaveUserのテスト")
    class TestLeaveUser {

        @Test
        @DisplayName("leaveUserユースケースが実行される")
        void testExecution() {

            final LeaveRequest request = new LeaveRequest();
            request.setName("test-user");

            client.post().uri("/users/leave")
                .body(Mono.just(request), LeaveRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

            verify(mockLeaveUser, times(1)).accept(argThat(new NameMatcher("test-user")));
        }
    }

    private class NameMatcher implements ArgumentMatcher<Name> {

        private final String expectedName;

        private NameMatcher(final String expectedName) {

            this.expectedName = expectedName;
        }

        @Override
        public boolean matches(final Name argument) {

            return expectedName.equals(argument.getValue());
        }
    }


    private class UserMatcher implements ArgumentMatcher<User> {

        private final String expectedName;

        private UserMatcher(final String expectedName) {

            this.expectedName = expectedName;
        }

        @Override
        public boolean matches(final User argument) {

            return expectedName.equals(argument.getName().getValue());
        }
    }

    private class UseResponseMatcher implements Predicate<UsersResponse> {

        private final List<User> expected;

        private UseResponseMatcher(final List<User> expected) {

            this.expected = expected;
        }

        @Override
        public boolean test(final UsersResponse arg) {

            final int expectedSize = expected.size();

            final List<UserDetailResponse> actualUsers = arg.getUsers();
            if (expectedSize != actualUsers.size() || expectedSize != arg.getCount()) {

                return false;
            }

            for (int i = 0; i < expectedSize; i++) {

                final UserDetailResponse actualUser = actualUsers.get(i);
                final User expectedUser = expected.get(i);

                if (!expectedUser.getName().getValue().equals(actualUser.getName())) {

                    return false;
                }
            }

            return true;
        }

    }
}
