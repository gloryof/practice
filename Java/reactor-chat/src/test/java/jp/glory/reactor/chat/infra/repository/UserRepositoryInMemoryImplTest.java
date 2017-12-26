package jp.glory.reactor.chat.infra.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.UserNotify;

class UserRepositoryInMemoryImplTest {

    private UserRepositoryInMemoryImpl sut = null;
    private UserNotify mockNotify = null;

    @BeforeEach
    void setUp() {

        mockNotify = mock(UserNotify.class);

        sut = new UserRepositoryInMemoryImpl(mockNotify);
    }

    @Nested
    @DisplayName("addのテスト")
    class TestAdd {

        @Test
        @DisplayName("実行するとユーザが追加される")
        void assertValue() {

            sut.add(new User(new Name("test-user1")));
            sut.add(new User(new Name("test-user2")));
            sut.add(new User(new Name("test-user3")));

            final List<User> actual =  sut.findAll();

            assertEquals(3, actual.size());

            assertEquals("test-user1", actual.get(0).getName().getValue());
            assertEquals("test-user2", actual.get(1).getName().getValue());
            assertEquals("test-user3", actual.get(2).getName().getValue());
        }


        @Test
        @DisplayName("同じユーザ名は追加されない")
        void assertSameValue() {

            sut.add(new User(new Name("test-user1")));
            sut.add(new User(new Name("test-user1")));
            sut.add(new User(new Name("test-user1")));

            final List<User> actual =  sut.findAll();

            assertEquals(1, actual.size());

            assertEquals("test-user1", actual.get(0).getName().getValue());
        }
    }

    @Nested
    @DisplayName("notifyToUsersのテスト")
    class TestNotifyToUsers {

        private List<String> names = null;

        @BeforeEach
        void setUp() {

            names = IntStream.rangeClosed(1, 3)
                        .mapToObj(i -> "test-user" + i)
                        .collect(Collectors.toList());

            names.forEach(v -> sut.add(new User(new Name(v))));
        }

        @Test
        @DisplayName("現在設定されているリストが通知される")
        void assertValues() {

            sut.notifyToUsers();

            verify(mockNotify, times(1)).publish(argThat(new UsersMatcher(names)));
        }
    }

    @Nested
    @DisplayName("deleteのテスト")
    class TestDelete {

        private List<String> names = null;

        @BeforeEach
        void setUp() {

            names = IntStream.rangeClosed(1, 3)
                        .mapToObj(i -> "test-user" + i)
                        .collect(Collectors.toList());

            names.forEach(v -> sut.add(new User(new Name(v))));
        }

        @Test
        @DisplayName("存在している値の場合は削除され、削除後のデータで通知される")
        void assertExistValue() {

            final String deleteKey = "test-user2";

            sut.delete(new Name(deleteKey));

            final List<User> actual =  sut.findAll();

            assertEquals("test-user1", actual.get(0).getName().getValue());
            assertEquals("test-user3", actual.get(1).getName().getValue());

            List<String> expectNames = names.stream()
                                            .filter(v -> !deleteKey.equals(v))
                                            .collect(Collectors.toList());
            verify(mockNotify, times(1)).publish(argThat(new UsersMatcher(expectNames)));
        }

        @Test
        @DisplayName("存在していない値の場合は削除されず、実行前のデータで通知される")
        void assertNotExistValue() {

            final String deleteKey = "test-user4";

            sut.delete(new Name(deleteKey));

            final List<User> actual =  sut.findAll();

            assertEquals("test-user1", actual.get(0).getName().getValue());
            assertEquals("test-user2", actual.get(1).getName().getValue());
            assertEquals("test-user3", actual.get(2).getName().getValue());

            verify(mockNotify, times(1)).publish(argThat(new UsersMatcher(names)));
        }
    }

    private class UsersMatcher implements ArgumentMatcher<List<User>> {

        private final List<String> names;

        private UsersMatcher(final List<String> names) {

            this.names = names;
        }

        @Override
        public boolean matches(final List<User> argument) {

            if (argument.size() != names.size()) {

                return false;
            }

            for (int i = 0; i < argument.size(); i++) {

                if (!names.get(i).equals(argument.get(i).getName().getValue())) {

                    return false;
                }
            }
            return true;
        }
    }
}
