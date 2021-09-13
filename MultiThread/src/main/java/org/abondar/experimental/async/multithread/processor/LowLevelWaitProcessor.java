package org.abondar.experimental.async.multithread.processor;


import java.util.LinkedList;
import java.util.Random;

public class LowLevelWaitProcessor {

    private final LinkedList<Integer> linkedList = new LinkedList<>();

    private final Object lock = new Object();

    public void produce() throws InterruptedException {

        int val = 0;
        while (true) {
            synchronized (lock) {
                int LIMIT = 10;
                while (linkedList.size() == LIMIT){
                    lock.wait();
                }
                linkedList.add(val++);
                lock.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        Random random = new Random();
        while (true) {

            synchronized (lock) {

                while (linkedList.size()==0){
                    lock.wait();
                }
                System.out.printf("List size %d ", linkedList.size());
                int val = linkedList.removeFirst();
                System.out.printf("; value is: %d\n", val);
                lock.notify();
            }

            Thread.sleep(random.nextInt(1000));
        }
    }
}
