package org.abondar.experimental.async.javarx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;
import rx.Subscription;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static org.abondar.experimental.async.javarx.util.LogUtil.log;

public class NaturalNumbersCommand implements Command {
    @Override
    public void execute() {
        Subscription subscription = naturalNumbers().subscribe(msg->log("NCC",msg));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex){
            System.err.println(ex.getMessage());
            System.exit(2);
        }

        subscription.unsubscribe();
    }

    private Observable<BigInteger> naturalNumbers() {
        return Observable.create(
                subscriber -> {
                    Runnable r = () -> {
                        BigInteger i = ZERO;
                        while (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(i);
                            i = i.add(ONE);
                        }
                    };
                    new Thread(r).start();
                });
    }



}
