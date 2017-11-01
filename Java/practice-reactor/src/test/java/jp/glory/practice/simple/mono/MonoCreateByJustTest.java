package jp.glory.practice.simple.mono;

import org.junit.Before;
import org.junit.Test;

public class MonoCreateByJustTest {

    MonoCreateByJust sut = null;

    @Before
    public void setUp() {

        sut = new MonoCreateByJust();
    }

    @Test
    public void testSubscribe()  {

        sut.subscribe();
    }

    @Test
    public void testDoOn() {

        sut.doOn();
    }

    @Test
    public void testFilter() {

        sut.filter();
    }

}
