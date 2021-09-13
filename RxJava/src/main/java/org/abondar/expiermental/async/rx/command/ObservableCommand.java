package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;

import org.abondar.expiermental.async.rx.util.LogUtil;
import rx.Observable;


public class ObservableCommand implements Command {
    @Override
    public void execute() {
        String logKey = "OC";
        LogUtil.log(logKey,"Before");

        Observable
                .range(5, 3)
                .subscribe(msg-> LogUtil.log(logKey,msg));
        LogUtil.log(logKey,"After");



        Observable<Integer> ints = Observable.create(subscriber -> {
            LogUtil.log(logKey,"Create");
            subscriber.onNext(5);
            subscriber.onNext(6);
            subscriber.onNext(7);
            LogUtil.log(logKey,"Completed");
        });


        LogUtil.log(logKey,"Starting");
        ints.subscribe(i -> LogUtil.log(logKey,"Element: " + i));
        LogUtil.log(logKey,"Exit");
    }


}
