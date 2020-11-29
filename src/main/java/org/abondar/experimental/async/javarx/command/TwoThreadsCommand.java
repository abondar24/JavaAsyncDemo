package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

public class TwoThreadsCommand implements Command {
    @Override
    public void execute() {
        Observable<String> a = Observable.create(subscriber -> new Thread(() -> {
            subscriber.onNext("one");
            subscriber.onNext("two");
            subscriber.onCompleted();
        }).start());

        Observable<String> b = Observable.create(subscriber -> new Thread(() -> {
            subscriber.onNext("three");
            subscriber.onNext("four");
            subscriber.onCompleted();
        }).start());

        Observable<String> c = Observable.merge(a, b);
        c.subscribe(System.out::println);
    }
}
