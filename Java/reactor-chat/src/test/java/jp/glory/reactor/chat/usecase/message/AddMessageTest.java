package jp.glory.reactor.chat.usecase.message;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.entity.MessageMatcher;
import jp.glory.reactor.chat.domain.repository.MessageRepository;
import jp.glory.reactor.chat.domain.value.Name;

class AddMessageTest {

    private AddMessage sut = null;

    private MessageRepository mockRepository = null;

    @BeforeEach
    public void setUp() {

        mockRepository = mock(MessageRepository.class);
        sut = new AddMessage(mockRepository);
    }


    @Test
    @DisplayName("追加されることの確認")
    void testAdded() {

        final Message expect = new Message(new Name("test-user"), "test-content");
        final MessageMatcher matchMessage = new MessageMatcher(expect);

        sut.accept(expect);

        verify(mockRepository).notifyToUser(argThat(v -> matchMessage.test(List.of(v))));
    }

}
