package jp.glory.sample.nonweb;

import org.springframework.stereotype.Component;

@Component
public class CodeChecker {

    public boolean isValid(int value) {

        if (value < 10) {

            return true;
        }

        return false;
    }
}
