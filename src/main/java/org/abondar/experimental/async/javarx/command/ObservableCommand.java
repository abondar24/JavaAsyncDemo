package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;

import rx.Observable;

import static org.abondar.experimental.async.javarx.LogUtil.log;

public class ObservableCommand implements Command {
    @Override
    public void execute() {
        String logKey = "OC";
        log(logKey,"Before");

        Observable
                .range(5, 3)
                .subscribe(msg-> log(logKey,msg));
        log(logKey,"After");



        Observable<Integer> ints = Observable.create(subscriber -> {
            log(logKey,"Create");
            subscriber.onNext(5);
            subscriber.onNext(6);
            subscriber.onNext(7);
            log(logKey,"Completed");
        });


        log(logKey,"Starting");
        ints.subscribe(i -> log(logKey,"Element: " + i));
        log(logKey,"Exit");
    }


}
