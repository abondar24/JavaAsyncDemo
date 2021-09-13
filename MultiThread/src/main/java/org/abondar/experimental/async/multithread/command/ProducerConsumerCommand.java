package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.command.Command;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerCommand implements Command {

    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    private static void producer() throws InterruptedException {
        Random random = new Random();
        while (true){
            queue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException{
        Random random = new Random();
        while (true){
            Thread.sleep(100);
            if (random.nextInt(10)==0){
                Integer val = queue.take();
                System.out.printf("Taken value %d; Queue size is %d\n",val,queue.size());
            }
        }
    }

    @Override
    public void execute() {
        try {
            Thread t1 = new Thread(()->{
                try {
                    producer();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                    System.exit(2);
                }
            });

            Thread t2  = new Thread(()->{
                try {
                    consumer();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                    System.exit(2);
                }
            });


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
