package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

public class SchedulerSubscribeCommand extends SchedulerAbstractCommand implements Command {
    @Override
    public void execute() {
        log("Starting");
        Observable<String> observable = simple();
        log("Created");
        observable.subscribeOn(schedulerA)
                .subscribeOn(schedulerB)
                .subscribe(x -> log("Got " + x),
                        Throwable::printStackTrace,
                        () -> log("Completed"));
        log("Exiting");
    }
}
