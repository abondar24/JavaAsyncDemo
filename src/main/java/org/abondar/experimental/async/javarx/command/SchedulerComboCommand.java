package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

public class SchedulerComboCommand extends SchedulerAbstractCommand implements Command {
    @Override
    public void execute() {
        log("Starting");
        Observable<String> observable = Observable.create(subscriber -> {
            log("Subscribed");
            subscriber.onNext("A");
            subscriber.onNext("B");
            subscriber.onNext("C");
            subscriber.onNext("D");
            subscriber.onCompleted();
        });
        log("Created");
        observable.subscribeOn(schedulerA)
                .flatMap(record -> store(record).subscribeOn(schedulerB))
                .observeOn(schedulerC)
                .subscribe(x -> log("Got: "+x),
                        Throwable::printStackTrace,
                        ()->log("Completed"));
        log("Exiting");
    }
}
