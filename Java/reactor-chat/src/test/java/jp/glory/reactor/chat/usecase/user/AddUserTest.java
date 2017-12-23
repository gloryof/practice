package jp.glory.reactor.chat.usecase.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.repository.UserRepository;
import jp.glory.reactor.chat.domain.value.Name;

class AddUserTest {

    private AddUser sut = null;
    private UserRepository mockRepository = null;

    @BeforeEach
    void setUp() {

        mockRepository = mock(UserRepository.class);
        sut = new AddUser(mockRepository);
    }

    @Test
    @DisplayName("ユーザが追加され他のユーザに通知される")
    void assertAdd() {

        final User user = new User(new Name("test"));

        sut.accept(user);

        verify(mockRepository, times(1)).add(user);
        verify(mockRepository, times(1)).notifyToUsers();
    }
}
