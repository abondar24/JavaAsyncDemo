package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.multithread.runner.Runner;

public class ReentrantLockDemo {

    public static void main(String[] args) throws Exception {
        final Runner runner = new Runner();

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
    }
}
