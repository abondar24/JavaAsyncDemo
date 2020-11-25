package org.abondar.experimental.async.multithread.command;


import org.abondar.experimental.async.command.Command;

import java.util.Random;

public class ThreadInterruptionCommand implements Command {

    @Override
    public void execute() {

        try {
            System.out.println("Starting");
            Thread t1 = new Thread(() -> {
                Random random = new Random();

                for (int i = 0; i < 1E8; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Interrupted!");
                        break;
                    }
                    Math.sin(random.nextDouble());
                }
            });
            t1.start();

            Thread.sleep(500);
            t1.interrupt();

            t1.join();
            System.out.println("Finished");
        } catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }


    }
}
