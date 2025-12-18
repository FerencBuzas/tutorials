package com.baeldung.concurrent.cyclicbarrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierCountExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrierCountExample.class);

    private final int count;
    private final CyclicBarrier cyclicBarrier;

    public CyclicBarrierCountExample(int count) {
        LOGGER.info("CyclicBarrierCountExample() count={}", count);

        this.count = count;
        cyclicBarrier = new CyclicBarrier(count);
    }

    public boolean callTwiceInSameThread() {
        LOGGER.info("callTwiceInSameThread()");
        logBarrier("before starting the new thread");

        Thread t = new Thread(() -> {
            logBarrier("before 2 await() calls");
            try {
                cyclicBarrier.await();
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        t.start();
        logBarrier("end of callTwiceInSameThread()");

        return cyclicBarrier.isBroken();
    }

    private void logBarrier(String prefix) {
        LOGGER.debug("{} - isBroken={}, numberWaiting={}, parties={}", prefix, cyclicBarrier.isBroken(),
                cyclicBarrier.getNumberWaiting(), cyclicBarrier.getParties());
    }
    public static void main(String[] args) {

        CyclicBarrierCountExample ex = new CyclicBarrierCountExample(7);
        ex.callTwiceInSameThread();
    }
}
