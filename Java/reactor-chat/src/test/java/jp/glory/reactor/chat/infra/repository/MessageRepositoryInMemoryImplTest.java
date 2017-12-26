package jp.glory.reactor.chat.infra.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.stubbing.VoidAnswer1;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.value.Name;
import jp.glory.reactor.chat.infra.notify.MessageNotify;
import reactor.test.scheduler.VirtualTimeScheduler;

class MessageRepositoryInMemoryImplTest {

    private MessageRepositoryInMemoryImpl sut;
    private MessageNotify mockNotify;

    @BeforeEach
    void setUp() {

        mockNotify = mock(MessageNotify.class);

        sut = new MessageRepositoryInMemoryImpl(mockNotify);
    }

    @Nested
    @DisplayName("notifyToUserのテスト")
    class TestNotifyToUser {

        @Test
        @DisplayName("通知処理が実行される")
        void assertNotify() {

            final Message message = new Message(new Name("test-name"), "test-content");

            sut.notifyToUser(message);

            verify(mockNotify, times(1)).publish(message);
        }
    }

    @Nested
    @DisplayName("notifyDelayToUserのテスト")
    class TestNotifyDelayToUser {

        @Test
        @DisplayName("通知処理が実行される")
        void assertNotify() throws InterruptedException {

            final List<Message> messages = IntStream.rangeClosed(1, 5)
                                            .mapToObj(i -> new Message(new Name("test-name"), "test-content-" + i))
                                            .collect(Collectors.toList());

            final List<Message> actualMessage = new ArrayList<Message>();

            doAnswer(AdditionalAnswers.answerVoid(answerMockNotify(actualMessage)))
                    .when(mockNotify).publish(ArgumentMatchers.any());

            final VirtualTimeScheduler scheduler = VirtualTimeScheduler.getOrSet();

            sut.notifyDelayToUser(messages);

            scheduler.advanceTimeBy(Duration.ofSeconds(10));

            assertEquals(5, actualMessage.size());

            assertAll("1stData",
                    () -> assertEquals("test-name", actualMessage.get(0).getName().getValue()),
                    () -> assertEquals("test-content-1", actualMessage.get(0).getContent()));

            assertAll("2ndData",
                    () -> assertEquals("test-name", actualMessage.get(1).getName().getValue()),
                    () -> assertEquals("test-content-2", actualMessage.get(1).getContent()));

            assertAll("3rdData",
                    () -> assertEquals("test-name", actualMessage.get(2).getName().getValue()),
                    () -> assertEquals("test-content-3", actualMessage.get(2).getContent()));

            assertAll("4thData",
                    () -> assertEquals("test-name", actualMessage.get(3).getName().getValue()),
                    () -> assertEquals("test-content-4", actualMessage.get(3).getContent()));

            assertAll("5thData",
                    () -> assertEquals("test-name", actualMessage.get(4).getName().getValue()),
                    () -> assertEquals("test-content-5", actualMessage.get(4).getContent()));
        }
    }

    private static VoidAnswer1<Message> answerMockNotify(final List<Message> targetList) {

        return new VoidAnswer1<Message>() {

            @Override
            public void answer(Message v) throws Throwable {
                targetList.add(v);
            }
        };
    }
}
