package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.expiermental.async.rx.util.LogUtil;
import rx.Observable;


public class MultipleSubscribersCommand implements Command {
    @Override
    public void execute() {
        String logKey = "MSC";
        Observable<Integer> ints = Observable.<Integer>create(subscriber -> {
                            LogUtil.log(logKey,"Create");
                            subscriber.onNext(42);
                            subscriber.onCompleted();
                        }
                ).cache();

        LogUtil.log(logKey,"Starting");
        ints.subscribe(i -> LogUtil.log(logKey,"Element A: " + i));
        ints.subscribe(i -> LogUtil.log(logKey,"Element B: " + i));
        LogUtil.log(logKey,"Exit");
    }


}
