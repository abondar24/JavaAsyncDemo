package org.abondar.experimental.javaasyncdemo.multithread;


public class TrylockDemo {
    public static void main(String[] args) throws Exception {
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
    }
}
