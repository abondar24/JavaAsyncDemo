package org.abondar.experimental.javaasyncdemo.multithread;


public class LowLevelWaitDemo {


    public static void main(String[] args) throws InterruptedException {
        final LowLevelWaitProcessor processor = new LowLevelWaitProcessor();

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
