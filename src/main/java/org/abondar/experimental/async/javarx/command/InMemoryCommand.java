package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

public class InMemoryCommand implements Command {
    @Override
    public void execute() {
        String someKey = "Key";
        Observable.create(subscriber -> {
            String cache = getFromCache(someKey);
            subscriber.onNext(cache);
            subscriber.onCompleted();
        }).subscribe(System.out::println);
    }


    private String getFromCache(String key) {
        return key + ":644";
    }
}
