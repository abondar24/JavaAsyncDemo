package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DelayCommand implements Command {
    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();
        Observable
                .interval(700, TimeUnit.MILLISECONDS)
                .timestamp()
                .sample(10, SECONDS)
                .map(ts -> ts.getTimestampMillis() - startTime + "ms: " + ts.getValue())
                .take(5)
                .subscribe(System.out::println);
    }
}
