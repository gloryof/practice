package jp.glory.reactor.chat.usecase.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jp.glory.reactor.chat.domain.repository.UserRepository;
import jp.glory.reactor.chat.domain.value.Name;

class LeaveUserTest {

    private LeaveUser sut = null;
    private UserRepository mockRepository = null;

    @BeforeEach
    void setUp() {

        mockRepository = mock(UserRepository.class);
        sut = new LeaveUser(mockRepository);
    }

    @Test
    @DisplayName("ユーザが削除される")
    void assertLeave() {

        final Name name = new Name("test");

        sut.accept(name);

        verify(mockRepository, times(1)).delete(name);
    }
}
