package org.abondar.experimental.async.multithread.processor;


import java.util.Scanner;

public class WaitProcessor {

    public void produce() throws InterruptedException {

        synchronized (this) {
            System.out.println("Producer thread running ....");
            wait();
            System.out.println("Resumed.");
        }

    }

    public void consume() throws InterruptedException {
        Scanner scanner  = new Scanner(System.in);
        Thread.sleep(10000);

        synchronized (this) {
            System.out.println("Waiting for return");
            scanner.nextLine();
            System.out.println("Return pressed.");
            notify();
            Thread.sleep(5000);
        }
    }
}
