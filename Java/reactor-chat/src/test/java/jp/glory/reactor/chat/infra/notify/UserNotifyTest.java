package jp.glory.reactor.chat.infra.notify;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jp.glory.reactor.chat.domain.entity.User;
import jp.glory.reactor.chat.domain.value.Name;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class UserNotifyTest {

    private UserNotify sut = null;

    @BeforeEach
    void setUp() {

        sut = new UserNotify();
    }

    @Test
    @DisplayName("publishでユーザのリストが通知される")
    void testPublish() {

        final User expected1 = new User(new Name("test-user-1"));
        final User expected2 = new User(new Name("test-user-1"));

        Flux<List<User>> flux = sut.getFlux();

        StepVerifier.withVirtualTime(() -> flux)
            .expectSubscription()
            .then(() -> sut.publish(List.of(expected1, expected2)))
            .expectNextMatches(new UserMatcher(List.of(expected1, expected2)))
            .then(() -> sut.complete())
            .verifyComplete();
            
    }

    private class UserMatcher implements Predicate<List<User>> {

        private final List<User> expected;

        private UserMatcher(final List<User> expected) {

            this.expected = expected;
        }

        @Override
        public boolean test(final List<User> actual) {


            if (actual.size() != expected.size()) {

                return false;
            }

            for (int i = 0; i < expected.size(); i++) {

                final Name actualName = actual.get(i).getName();
                final Name expectedName = expected.get(i).getName();

                if (!actualName.getValue().equals(expectedName.getValue())) {

                    return false;
                }
            }

            return true;
        }

        
    }
}
