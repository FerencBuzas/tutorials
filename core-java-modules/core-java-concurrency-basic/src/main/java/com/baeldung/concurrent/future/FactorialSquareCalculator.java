package com.baeldung.concurrent.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RecursiveTask;

public class FactorialSquareCalculator extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(FactorialSquareCalculator.class);

    final private Integer n;

    FactorialSquareCalculator(Integer n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        LOGGER.debug("compute() n={}", n);
        
        if (n <= 1) {
            return n;
        }

        LOGGER.debug("n = {}, creating a calculator for {}", n, n-1);
        FactorialSquareCalculator calculator = new FactorialSquareCalculator(n - 1);

        // NOTE: my (Feri's) experience is that if n is little (<= 10), this goes in one thread.
        //  with n = 33, already 4 threads are created.
        //
        LOGGER.debug("n = {}, calling fork()", n);
        calculator.fork();

        LOGGER.debug("n = {}, returning n * n + calculator.join()", n);
        return n * n + calculator.join();
    }
}
