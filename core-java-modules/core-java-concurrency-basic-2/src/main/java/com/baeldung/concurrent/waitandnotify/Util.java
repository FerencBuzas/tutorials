package com.baeldung.concurrent.waitandnotify;

import java.util.concurrent.ThreadLocalRandom;

public class Util {

    static void sleep(int origin, int bound) {

        int sleepMs = ThreadLocalRandom.current().nextInt(origin, bound);
        try {
            Thread.sleep(sleepMs);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread Interrupted");
        }
    }

    static void wait(Object o) {

        try {
            o.wait();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread Interrupted");
        }
    }
}
