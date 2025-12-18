package com.baeldung.concurrent.future;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertEquals;

public class FactorialSquareCalculatorUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FactorialSquareCalculatorUnitTest.class);

    @Test
    public void whenCalculatesFactorialSquare_thenReturnCorrectValue() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(10);

        forkJoinPool.execute(calculator);

        assertEquals("The sum of the squares from 1 to 10 is 385", 385, calculator.join().intValue());
    }


    // By Feri
    @Test
    public void testCalcFactSquare() {

        final int n = 3;
        LOGGER.info("testCalcFactSquare() n = {}", n);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialSquareCalculator calculator = new FactorialSquareCalculator(n);
        forkJoinPool.execute(calculator);

        LOGGER.info("The result for {}: {}", n, calculator.join());
    }
}
