package org.abondar.experimental.javaasyncdemo.multithread;


public class SynchronizedDemo {

    private int count = 0;

    private synchronized void increment(){
        count++;
    }
    public static void main(String[] args) {
        SynchronizedDemo demo = new SynchronizedDemo();
        demo.doWork();
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
            e.printStackTrace();
        }


        System.out.printf("Count is: %d\n",count);
    }
}
