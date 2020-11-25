package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.multithread.processor.WaitProcessor;

public class WaitNotifyDemo {



    public static void main(String[] args) throws InterruptedException {
        final WaitProcessor processor = new WaitProcessor();

        Thread t1 = new Thread(()->{
            try {
               processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2  = new Thread(()->{
            try {
               processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }


}
