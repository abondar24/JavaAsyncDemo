package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.processor.MultiProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolCommand implements Command {


    @Override
    public void execute() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i=0;i<5;i++){

            executor.submit(new MultiProcessor(i));
        }

        executor.shutdown();
        System.out.println("All tasks submited");

        try {
            executor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }
    }
}


