package jp.glory.practice.simple.flux;

import org.junit.Before;
import org.junit.Test;

public class FluxCreateByPushTest {


    FluxCreateByPush sut = null;

    @Before
    public void setUp() {

        sut = new FluxCreateByPush(3);
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
