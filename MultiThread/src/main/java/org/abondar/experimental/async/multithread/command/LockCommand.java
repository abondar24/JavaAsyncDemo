package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LockCommand implements Command {
    private static final Random random = new Random();


    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    private static final List<Integer> list1 = new ArrayList<>();
    private static final List<Integer> list2 = new ArrayList<>();

    private static void stageOne() {

        synchronized (lock1){
            try {
                Thread.sleep(1);
                list1.add(random.nextInt());
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
                System.exit(2);
            }
        }


    }

    private static  void stageTwo() {
        synchronized (lock2){
            try {
                Thread.sleep(1);
                list2.add(random.nextInt(10));
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
                System.exit(2);
            }

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
            System.err.println(ex.getMessage());
            System.exit(2);
        }

        long end = System.currentTimeMillis();

        process();
        System.out.printf("Time taken %d\n", end - start);

        System.out.printf("List1: %d, List2: %d\n", list1.size(), list2.size());
    }
}

