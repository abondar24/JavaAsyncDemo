package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.misc.CallTracker;

import java.util.Random;

public class ThreadLocalCommand implements Command {
    @Override
    public void execute() {
        new Thread(this::run).start();
        new Thread(this::run).start();
        new Thread(this::run).start();

    }


    private void run() {
        Random rand = new Random();
        int counter = rand.nextInt(5) + 1;

        System.out.printf("%s generated counter %d\n", Thread.currentThread().getName(), counter);
        for (int i = 0; i < counter; i++) {
            CallTracker.call();
        }
    }
}
