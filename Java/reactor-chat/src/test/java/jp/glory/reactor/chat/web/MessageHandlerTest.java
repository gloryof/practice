package jp.glory.reactor.chat.web;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.entity.MessageMatcher;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.MessageNotify;
import jp.glory.reactor.chat.usecase.message.AddMessage;
import jp.glory.reactor.chat.usecase.message.AddMessageDelay;
import jp.glory.reactor.chat.web.request.MessageRequest;
import jp.glory.reactor.chat.web.response.MessageResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MessageHandlerTest {

    private MessageHandler sut = null;

    private MessageNotify mockNotify = null;
    private AddMessage mockAdd = null;
    private AddMessageDelay mockDelay = null;

    private WebTestClient client = null;

    @BeforeEach
    void setUp() {

        mockNotify = mock(MessageNotify.class);
        mockAdd = mock(AddMessage.class);
        mockDelay = mock(AddMessageDelay.class);

        sut = new MessageHandler(mockAdd, mockDelay, mockNotify);

        final Routing routing = new Routing();
        client = WebTestClient.bindToRouterFunction(routing.routeSetting(sut, mock(UserHandler.class))).build();
    }

    @Nested
    @DisplayName("getMessagesのテスト")
    class TestGetMessages {

        @BeforeEach
        void setUp() {

            final Message message1 = new Message(new Name("test-user-1"),  "test-content-1");
            final Message message2 = new Message(new Name("test-user-2"),  "test-content-2");

            doReturn(Flux.just(message1, message2)).when(mockNotify).getFlux();
        }

        @Test
        @DisplayName("ステータスはOK、Content-Typeはtext/event-stream、Messageの中身が返る")
        void assertResponse() {

            FluxExchangeResult<MessageResponse> result =
                    client.get().uri("/messages")
                        .accept(MediaType.TEXT_EVENT_STREAM)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(MessageResponse.class);

            Flux<MessageResponse> flux = result.getResponseBody();

            StepVerifier.create(flux)
                .expectNextMatches(new MessageResponseMatcher("test-user-1", "test-content-1"))
                .expectNextMatches(new MessageResponseMatcher("test-user-2", "test-content-2"))
                .thenCancel()
                .verify();
        }
    }

    @Nested
    @DisplayName("addMessageのテスト")
    class TestAddMessage {

        @Test
        @DisplayName("addMessageユースケースが実行される")
        void assertExecution() {

            final MessageRequest request = new MessageRequest();
            request.setUsername("test-user");
            request.setMessage("test-message");

            final Message expect = new Message(new Name("test-user"), "test-message");
            final MessageMatcher matchMessage = new MessageMatcher(expect);

            client.post().uri("/messages/add")
                .body(Mono.just(request), MessageRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();


            verify(mockAdd, times(1)).accept(argThat(v -> matchMessage.test(List.of(v))));
        }
    }

    @Nested
    @DisplayName("addMessageDelayのテスト")
    class TestAddMessageDelay {

        @Test
        @DisplayName("addMessageDelayユースケースが実行される")
        void assertExecution() {

            final MessageRequest request = new MessageRequest();
            request.setUsername("test-user");
            request.setMessage("test-message");

            final Message expect = new Message(new Name("test-user"), "test-message");
            final MessageMatcher matchMessage = new MessageMatcher(expect);

            client.post().uri("/messages/addDelay")
                .body(Mono.just(request), MessageRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();


            verify(mockDelay, times(1)).accept(argThat(v -> matchMessage.test(List.of(v))));
        }
    }

    private class MessageResponseMatcher implements Predicate<MessageResponse> {

        private final String expectedName;
        private final String expectedContent;

        private MessageResponseMatcher(final String expectedName, final String expectedContent) {

            this.expectedName = expectedName;
            this.expectedContent = expectedContent;
        }

        @Override
        public boolean test(final MessageResponse actual) {

            if (!expectedName.equals(actual.getName())) {

                return false;
            }

            return expectedContent.equals(actual.getContent());
        }
    }
}
