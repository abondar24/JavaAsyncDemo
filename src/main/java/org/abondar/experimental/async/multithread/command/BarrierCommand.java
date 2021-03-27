package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierCommand implements Command {
    @Override
    public void execute() {
        Runnable action = ()-> System.out.println("Ready for action");
        CyclicBarrier barrier = new CyclicBarrier(3,action);

        for (int i=1;i<=3;i++){
            final int inc=i;
            Thread t = new Thread(()->{
                System.out.printf("T%d started\n",inc);
                try {
                    Thread.sleep(1000);
                    System.out.printf("T%d Waiting\n",inc);
                    barrier.await();
                    System.out.println("Barrier passed");
                }catch (InterruptedException | BrokenBarrierException ex){
                    System.err.println(ex.getMessage());
                    System.exit(2);
                }

            });
            t.start();
        }


    }
}
