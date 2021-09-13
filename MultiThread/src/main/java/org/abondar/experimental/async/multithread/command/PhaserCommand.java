package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;

import java.util.concurrent.Phaser;

public class PhaserCommand implements Command {
    @Override
    public void execute() {

        Phaser phaser = new Phaser(1);

        int taskNumber=3;
        phaser.bulkRegister(taskNumber);
        for (int i = 1; i <= taskNumber; i++) {

            final int inc = i;
            Thread t = new Thread(() -> {

                System.out.printf("T%d Initializing\n", inc);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                    System.exit(2);
                }
                phaser.arriveAndAwaitAdvance();
                System.out.printf("T%d Started\n", inc);

            });
            t.start();
        }

        phaser.arriveAndDeregister();


    }
}
