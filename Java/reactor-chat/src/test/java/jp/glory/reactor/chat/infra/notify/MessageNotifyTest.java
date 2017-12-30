package jp.glory.reactor.chat.infra.notify;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jp.glory.reactor.chat.domain.entity.Message;
import jp.glory.reactor.chat.domain.value.Name;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class MessageNotifyTest {

    private MessageNotify sut = null;

    @BeforeEach
    void setUp() {

        sut = new MessageNotify();
    }

    @Test
    @DisplayName("publishが実行されるとFluxに通知される")
    void testPublish() {

        final Message expected1 = new Message(new Name("test-user-1"), "test-content-1");
        final Message expected2 = new Message(new Name("test-user-2"), "test-content-2");

        Flux<Message> flux = sut.getFlux();

        StepVerifier.withVirtualTime(() -> flux)
                    .expectSubscription()
                    .then(() -> sut.publish(expected1))
                    .expectNextMatches(new MessageMatcher(expected1))
                    .then(() -> sut.publish(expected2))
                    .expectNextMatches(new MessageMatcher(expected2))
                    .then(() -> sut.complete())
                    .verifyComplete();

    }

    private class MessageMatcher implements Predicate<Message> {

        private final Message expected;

        private MessageMatcher(final Message expected) {

            this.expected = expected;
        }

        @Override
        public boolean test(final Message arg) {

            if (arg == null) {

                return false;
            }

            if (!expected.getName().getValue().equals(arg.getName().getValue())) {

                return false;
            }

            return expected.getContent().equals(arg.getContent());
        }

        
    }
}
