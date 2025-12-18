package com.baeldung.concurrent.countdownlatch;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BrokenWorker implements Runnable {

    private static boolean TO_DIE = true;

    private final List<String> outputScraper;
    private final CountDownLatch countDownLatch;

    BrokenWorker(final List<String> outputScraper, final CountDownLatch countDownLatch) {
        this.outputScraper = outputScraper;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        if (TO_DIE) {
            throw new RuntimeException("Oh dear");
        }

        countDownLatch.countDown();
        outputScraper.add("Counted down");
    }
}
