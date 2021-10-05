package org.abondar.experimental.async.multithread;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.runner.ReadWriteLockRunner;

public class ReadWriteLockCommand implements Command {
    @Override
    public void execute() {

        try {
            final ReadWriteLockRunner runner = new ReadWriteLockRunner();
            Thread t1 = new Thread(runner::writeThread);
            Thread.sleep(10000);
            Thread t2 = new Thread(runner::readThread);


            t1.start();
            t2.start();

            t1.join();
            t2.join();

        } catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }
    }
}
