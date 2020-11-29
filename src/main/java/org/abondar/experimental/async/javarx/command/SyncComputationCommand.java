package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

public class SyncComputationCommand implements Command {
    @Override
    public void execute() {
        Observable<Integer> observable = Observable.create(subscriber -> {
            subscriber.onNext(1);
            subscriber.onNext(2);
            subscriber.onNext(3);
        });

        observable.map(i -> "Number " + i).subscribe(System.out::println);
    }
}
