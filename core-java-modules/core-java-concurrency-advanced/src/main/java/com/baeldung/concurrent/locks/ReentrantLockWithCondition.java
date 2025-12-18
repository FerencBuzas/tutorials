package com.baeldung.concurrent.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWithCondition {

    private static final Logger LOG = LoggerFactory.getLogger(ReentrantLockWithCondition.class);

    private final Stack<String> stack = new Stack<>();
    private final int stackCapacity;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition stackEmptyCondition = lock.newCondition();
    private final Condition stackFullCondition = lock.newCondition();

    public ReentrantLockWithCondition(int stackCapacity) {
        this.stackCapacity = stackCapacity;
    }

    private void pushToStack(String item) throws InterruptedException {
        try {
            lock.lock();
            if (stack.size() == stackCapacity) {
                LOG.info("  await, stack size={}", stack.size());
                stackFullCondition.await();
            }
            LOG.info("Pushing {}", item);
            stack.push(item);
            stackEmptyCondition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    private String popFromStack() throws InterruptedException {
        try {
            lock.lock();
            if (stack.size() == 0) {
                LOG.info("  await, stack is empty");
                stackEmptyCondition.await();
            }
            return stack.pop();
        } finally {
            stackFullCondition.signalAll();
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        final int THREAD_COUNT = 2;
        final int N_ITEMS = 6;
        final int STACK_CAPACITY = 3;

        ReentrantLockWithCondition object = new ReentrantLockWithCondition(STACK_CAPACITY);

        final ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        service.execute(() -> {
            for (int i = 0; i < N_ITEMS; i++) {
                try {
                    object.pushToStack("Item_" + i);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        service.execute(() -> {
            for (int i = 0; i < N_ITEMS; i++) {
                try {
                    LOG.info("Popped " + object.popFromStack());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        service.shutdown();
    }
}
