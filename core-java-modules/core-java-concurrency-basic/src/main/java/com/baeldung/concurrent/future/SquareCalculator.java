package com.baeldung.concurrent.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class SquareCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SquareCalculator.class);

    private final ExecutorService executor;

    SquareCalculator(ExecutorService executor) {
        this.executor = executor;
    }

    Future<Integer> calculate(Integer input) {

        return executor.submit(() -> {
            LOGGER.debug("calculate() input={}, sleeping 1s", input);

            Thread.sleep(1000);
            return input * input;
        });
    }
}
