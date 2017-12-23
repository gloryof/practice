package jp.glory.reactor.chat.usecase.message;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.entity.MessageMatcher;
import jp.glory.reactor.chat.domain.repository.MessageRepository;
import jp.glory.reactor.chat.domain.value.Name;

class AddMessageDelayTest {

    private AddMessageDelay sut = null;

    private MessageRepository mockRepository = null;

    @BeforeEach
    public void setUp() {

        mockRepository = mock(MessageRepository.class);
        sut = new AddMessageDelay(mockRepository);
    }

    @Test
    @DisplayName("追加されることの確認")
    void testAdded() {

        final Message param = new Message(new Name("test-user"), "test-content");

        final List<Message> expect = IntStream.rangeClosed(1, 5)
                                            .mapToObj(v -> new Message(param.getName(), param.getContent() + "-" + v))
                                            .collect(Collectors.toList());
        final MessageMatcher matchMessage = new MessageMatcher(expect);

        sut.accept(param);

        verify(mockRepository).notifyDelayToUser(argThat(matchMessage::test));
    }
}
