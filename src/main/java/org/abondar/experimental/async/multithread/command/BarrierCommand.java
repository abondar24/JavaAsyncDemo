package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierCommand implements Command {
    @Override
    public void execute() {
        Runnable action = ()-> System.out.println("Ready for action");
        CyclicBarrier barrier = new CyclicBarrier(3,action);

        Thread t1 = new Thread(()->{
            System.out.println("T1 started");
            try {
                Thread.sleep(1000);
                System.out.println("T1 Waiting");
                barrier.await();
                System.out.println("Barrier passed");
            }catch (InterruptedException | BrokenBarrierException ex){
                System.err.println(ex.getMessage());
                System.exit(2);
            }

        });
        t1.start();

        Thread t2 = new Thread(()->{
            System.out.println("T2 started");
            try {
                Thread.sleep(2000);
                System.out.println("T2 Waiting");
                barrier.await();
                System.out.println("Barrier passed");
            }catch (InterruptedException | BrokenBarrierException ex){
                System.err.println(ex.getMessage());
                System.exit(2);
            }

        });
        t2.start();

        Thread t3 = new Thread(()->{
            System.out.println("T3 started");
            try {
                Thread.sleep(3000);
                System.out.println("T3 Waiting");
                barrier.await();
                System.out.println("Barrier passed");
            }catch (InterruptedException | BrokenBarrierException ex){
                System.err.println(ex.getMessage());
                System.exit(2);
            }

        });
        t3.start();
    }
}
