package com.baeldung.concurrent.future;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Feri, this class is missing from Baeldung's demo
 */
class FutureDemoTest {

    private FutureDemo futureDemo;

    @BeforeEach
    void setUp() {
        futureDemo = new FutureDemo();
    }

    @Test
    void invokeNormal() {
        futureDemo.invoke(false, false);
    }

    @Test
    void invokeWithCancelMayInterrupt() {
        futureDemo.invoke(true, true);
    }

    @Test
    void invokeWithCancelMayNotInterrupt() {
        futureDemo.invoke(true, false);
    }
}