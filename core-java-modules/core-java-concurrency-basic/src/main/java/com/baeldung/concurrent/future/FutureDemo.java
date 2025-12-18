package com.baeldung.concurrent.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureDemo {

    public String invoke(boolean toCancel, boolean mayInterruptIfRunning) {

        System.out.println("FutureDemo.invoke(), toCancel=" + toCancel);

        String str = null;

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<String> future = executorService.submit(() -> {
            // Task
            Thread.sleep(4000l);
            return "Hello world";
        });

        if (toCancel) {
            future.cancel(mayInterruptIfRunning);
        }

        try {
            future.get(2, TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e1) {
            System.out.println("Exception in future.get()");
            e1.printStackTrace();
        }

        if (future.isDone() && !future.isCancelled()) {
            try {
                str = future.get();
            }
            catch (InterruptedException | ExecutionException e) {
                System.out.println("isDone, Exception in future.get()");
                e.printStackTrace();
            }
        }

        return str;

    }

}
