package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.time.Duration;

import static org.abondar.experimental.async.javarx.util.SleeperUtil.sleep;

public class SchedulerWorkerCommand implements Command {
    @Override
    public void execute() {
        Scheduler scheduler = Schedulers.immediate();
        Scheduler.Worker worker = scheduler.createWorker();
        System.out.println("Main start");
        worker.schedule(() -> {
            System.out.println("Outer start");
            sleep(Duration.ofSeconds(1));
            worker.schedule(() -> {
                System.out.println("Middle start");
                sleep(Duration.ofSeconds(1));
                worker.schedule(() -> {
                    System.out.println("Inner start");
                    sleep(Duration.ofSeconds(1));
                    System.out.println("Inner end");
                });
                System.out.println("Middle end");
            });

            System.out.println("Outer End");

        });
        System.out.println("Main end");
        worker.unsubscribe();
    }
}
