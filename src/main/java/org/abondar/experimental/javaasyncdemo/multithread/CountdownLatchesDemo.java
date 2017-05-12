package org.abondar.experimental.javaasyncdemo.multithread;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchesDemo {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(7);

        ExecutorService service = Executors.newFixedThreadPool(3);

        for (int i=0;i<3;i++){
            service.submit(new LatchProcessor(latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
