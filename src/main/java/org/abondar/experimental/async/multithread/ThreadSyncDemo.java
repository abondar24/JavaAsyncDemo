package org.abondar.experimental.async.multithread;


import java.util.Scanner;

public class ThreadSyncDemo {
    public static void main(String[] args) {
        Processor processor = new Processor();
        processor.start();

        System.out.println("Press return to stop...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        processor.shutdown();
    }
}


