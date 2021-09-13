package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.task.RandomIntSum;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinCommand implements Command {
    @Override
    public void execute() {
        ForkJoinPool pool = new ForkJoinPool();

        RandomIntSum task = new RandomIntSum(7);
        int sum = pool.invoke(task);

        System.out.printf("Random sum %d\n",sum);
    }
}
