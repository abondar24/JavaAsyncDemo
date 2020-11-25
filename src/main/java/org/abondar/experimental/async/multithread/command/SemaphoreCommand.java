package org.abondar.experimental.async.multithread.command;



import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.Connection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SemaphoreCommand implements Command {

    @Override
    public void execute() {
        try {
            ExecutorService executor = Executors.newCachedThreadPool();

            for (int i=0;i<200;i++){
                executor.submit(()->{
                    Connection.getInstance().connect();
                });
            }

            executor.shutdown();

            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

    }
}
