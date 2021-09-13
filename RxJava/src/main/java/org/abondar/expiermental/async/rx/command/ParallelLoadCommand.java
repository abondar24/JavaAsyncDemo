package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.expiermental.async.rx.data.Data;
import rx.Observable;

public class ParallelLoadCommand implements Command {
    @Override
    public void execute() {
       Observable<Data> obsData = loadObservable();
       obsData.subscribe(d-> System.out.println(d.toString()));

       Observable<Data> callData = loadCallable();
       callData.subscribe(d-> System.out.println(d.toString()));
    }

    private Observable<Data> loadObservable() {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(createData());
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    private Observable<Data> loadCallable() {
        return Observable.fromCallable(ParallelLoadCommand::createData);
    }


    private static Data createData() {
        return new Data();
    }
}
