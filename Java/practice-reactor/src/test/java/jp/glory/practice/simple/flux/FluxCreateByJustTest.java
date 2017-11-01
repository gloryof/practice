package jp.glory.practice.simple.flux;

import org.junit.Before;
import org.junit.Test;

public class FluxCreateByJustTest {

    FluxCreateByJust sut = null;

    @Before
    public void setUp() {

        sut = new FluxCreateByJust();
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
