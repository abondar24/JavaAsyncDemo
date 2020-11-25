package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.runner.TryLockRunner;

public class TryLockCommand implements Command {

    @Override
    public void execute() {
        try {
            final TryLockRunner runner = new TryLockRunner();

            Thread t1 = new Thread(()->{
                try {
                    runner.firstThread();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            });

            Thread t2 = new Thread(()->{
                try {
                    runner.secondThread();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            runner.finished();
        } catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }
    }
}
