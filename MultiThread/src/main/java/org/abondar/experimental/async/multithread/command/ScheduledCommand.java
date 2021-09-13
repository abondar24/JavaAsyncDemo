package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.task.ScheduledTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledCommand implements Command {
    @Override
    public void execute() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);

        ScheduledTask t1 = new ScheduledTask(1);
        ScheduledTask t2 = new ScheduledTask(2);


        ses.schedule(t1,2, TimeUnit.SECONDS);
        ses.scheduleAtFixedRate(t2,5,10,TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(30);
        }catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

        ses.shutdown();
    }
}
