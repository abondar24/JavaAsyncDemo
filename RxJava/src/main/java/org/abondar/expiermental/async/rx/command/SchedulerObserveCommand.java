package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

public class SchedulerObserveCommand extends SchedulerAbstractCommand implements Command {
    @Override
    public void execute() {
        log("Starting");
        final Observable<String> observable = simple();
        log("created");
        observable
                .doOnNext(x -> log("Found 1: " +x))
                .observeOn(schedulerA)
                .doOnNext(x -> log("Found 2: " +x))
                .subscribe(
                        x->log("Got 1: "+x),
                        Throwable::printStackTrace,
                        ()->log("Completed"));
        log("Exiting");
    }
}
