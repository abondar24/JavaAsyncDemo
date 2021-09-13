package org.abondar.experimental.async.multithread.processor;

import java.util.concurrent.CountDownLatch;

public class LatchProcessor implements Runnable {
    private final CountDownLatch latch;

    public LatchProcessor(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Started.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}
