package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.processor.LatchProcessor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchCommand implements Command {

    @Override
    public void execute() {
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
