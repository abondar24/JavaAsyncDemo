package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

public class HelloCommand implements Command {
    @Override
    public void execute() {
        Observable.create(subscriber -> {
            subscriber.onNext("Hello World!");
            subscriber.onCompleted();
        }).subscribe(System.out::println);
    }
}
