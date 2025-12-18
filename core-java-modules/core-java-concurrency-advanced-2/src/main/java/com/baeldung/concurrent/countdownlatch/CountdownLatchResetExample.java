package com.baeldung.concurrent.countdownlatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CountdownLatchResetExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountdownLatchResetExample.class);

    private int count;
    private int threadCount;
    private final AtomicInteger updateCount;

    CountdownLatchResetExample(int count, int threadCount) {
        LOGGER.debug("CountdownLatchResetExample() count={} threadCount={}", count, threadCount);

        updateCount = new AtomicInteger(0);
        this.count = count;
        this.threadCount = threadCount;
    }

    public int countWaits() {
        LOGGER.debug("countWaits() - creating a latch, count={}", count);

        CountDownLatch countDownLatch = new CountDownLatch(count);
        ExecutorService es = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            es.execute(() -> {
                long prevValue = countDownLatch.getCount();
                countDownLatch.countDown();
                LOGGER.debug("prevValue={} latch after countDown: {}", prevValue, countDownLatch.getCount());
                if (countDownLatch.getCount() != prevValue) {
                    updateCount.incrementAndGet();
                    LOGGER.debug("  !=, updateCount: {}", updateCount.get());
                }
            });
        }
        
        es.shutdown();
        return updateCount.get();
    }

    public static void main(String[] args) {

        CountdownLatchResetExample ex = new CountdownLatchResetExample(5, 20);
        LOGGER.info("Count: {}", ex.countWaits());
    }
}
