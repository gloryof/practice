package jp.glory.reactor.chat.domain.entity;

import java.util.List;
import java.util.function.Predicate;

public class MessageMatcher implements Predicate<List<Message>> {

    private final List<Message> expecteds;

    public MessageMatcher(final Message expect) {

        this.expecteds = List.of(expect);
    }


    public MessageMatcher(final List<Message> expecteds) {

        this.expecteds = expecteds;
    }

    @Override
    public boolean test(final List<Message> actual) {

        final int expectedSize = expecteds.size();
        if (actual.size() != expectedSize) {

            return false;
        }

        for (int i = 0; i < expectedSize; i++) {

            if (!match(actual.get(i), expecteds.get(i))) {

                return false;
            }
        }

        return true;
    }

    private boolean match(final Message actual, final Message expected) {


        if (!expected.getName().getValue().equals(actual.getName().getValue())) {

            return false;
        }

        return expected.getContent().equals(actual.getContent());
    }
}
