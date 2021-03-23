package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.thread.VolatileThread;

public class VolatileCommand  implements Command {
    @Override
    public void execute() {
        VolatileThread vt = new VolatileThread();

        vt.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }


        System.out.println(vt.getCounter());

        vt.stopThread();

    }
}
