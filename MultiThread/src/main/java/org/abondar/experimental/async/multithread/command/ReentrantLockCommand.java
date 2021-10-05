package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.runner.ReentrantLockRunner;

public class ReentrantLockCommand implements Command {


    @Override
    public void execute() {
        try {
            final ReentrantLockRunner reentrantLockRunner = new ReentrantLockRunner();

            Thread t1 = new Thread(()->{
                try {
                    reentrantLockRunner.firstThread();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            });

            Thread t2 = new Thread(()->{
                try {
                    reentrantLockRunner.secondThread();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            reentrantLockRunner.finished();
        } catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }


    }
}
