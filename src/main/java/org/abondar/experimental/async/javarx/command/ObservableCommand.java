package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.javarx.Basics;
import rx.Observable;

public class ObservableCommand implements Command {
    @Override
    public void execute() {
        log("Before");
        Observable
                .range(5, 3)
                .subscribe(ObservableCommand::log);
        log("After");


        Observable<Integer> ints = Observable.create(subscriber -> {
            log("Create");
            subscriber.onNext(5);
            subscriber.onNext(6);
            subscriber.onNext(7);
            log("Completed");
        });

        log("Starting");
        ints.subscribe(i -> log("Element: " + i));
        log("Exit");
    }

    private static void log(Object msg) {
        System.out.println("OC: " + msg);
    }
}
