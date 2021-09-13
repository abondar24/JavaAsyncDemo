package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExplicitLockCommand implements Command {
    private static final Random random = new Random();


    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    private static final List<Integer> list1 = new ArrayList<>();
    private static final List<Integer> list2 = new ArrayList<>();

    private static void stageOne() {

        lock1.lock();
        try {
            list1.add(random.nextInt());
        }finally {
            lock1.unlock();
        }
    }

    private static  void stageTwo() {
        lock2.lock();
        try {
            list2.add(random.nextInt());
        }finally {
            lock2.unlock();
        }
    }

    public static void process() {
        for (int i = 0; i < 10000; i++) {
            stageOne();
            stageTwo();
        }
    }


    @Override
    public void execute() {
        System.out.println("Starting ...");
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(LockCommand::process);
        t1.start();

        Thread t2 = new Thread(LockCommand::process);
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

        System.out.printf("List1: %d, List2: %d\n", list1.size(), list2.size());
    }
}
