package org.abondar.experimental.async.multithread.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LockDemo {
    private static Random random = new Random();


    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    private static List<Integer> list1 = new ArrayList<>();
    private static List<Integer> list2 = new ArrayList<>();

    private static void stageOne() {

        synchronized (lock1){
            try {
                Thread.sleep(1);
                list1.add(random.nextInt());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }


    }

    private static  void stageTwo() {
        synchronized (lock2){
            try {
                Thread.sleep(1);
                list2.add(random.nextInt(10));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }

    }

    public static void process() {
        for (int i = 0; i < 10000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting ...");
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            process();
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            process();
        });
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        long end = System.currentTimeMillis();

        process();
        System.out.printf("Time taken %d\n", end - start);

        System.out.printf("List1: %d, List2: %d", list1.size(), list2.size());
    }
}

