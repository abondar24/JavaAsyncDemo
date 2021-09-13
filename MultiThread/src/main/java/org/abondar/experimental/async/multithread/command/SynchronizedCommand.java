package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.command.Command;

public class SynchronizedCommand implements Command {

    private int count = 0;

    private synchronized void increment(){
        count++;
    }

    private void doWork() {
        Thread t1 = new Thread(()->{
            for (int i=0;i<10000;i++){
                increment();
            }
        });

        Thread t2 = new Thread(()->{
            for (int i=0;i<10000;i++){
                increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }


        System.out.printf("Count is: %d\n",count);
    }

    @Override
    public void execute() {
        doWork();
    }
}
