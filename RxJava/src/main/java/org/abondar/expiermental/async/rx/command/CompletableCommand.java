package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import rx.Completable;
import rx.Observable;

import java.util.function.Consumer;

public class CompletableCommand implements Command {
    @Override
    public void execute() {

        String data = "Some data";
        Completable completable = writeData(data);
        completable.andThen(Observable.create(subscriber -> {
            subscriber.onNext("Data is witten");
            subscriber.onCompleted();
        }))
                .asObservable()
                .subscribe(System.out::println);

    }

    private Completable writeData(String data) {
        return Completable.create(s -> doAsyncWrite(data,
                s::onCompleted,
                s::onError));
    }

    private void doAsyncWrite(String data, Runnable onSuccess, Consumer<Exception> onError) {
        System.out.println("Writing: "+  data);
        onSuccess.run();
    }
}
