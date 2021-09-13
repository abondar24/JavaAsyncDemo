package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

public class MergeSinglesCommand implements Command {
    @Override
    public void execute() {
      mergedObserver();
      mergeSingles();
    }

    private void mergedObserver(){
        Observable<String> aMergeB = getDataA().mergeWith(getDataB());
        aMergeB.subscribe(System.out::println);
    }

    private void mergeSingles(){
        Single<String> s1 = getDataAsSingle(1);
        Single<String> s2 = getDataAsSingle(2);

        Observable<String> observable = Single.merge(s1, s2);
        observable.subscribe(System.out::println);
    }

    private Single<String> getDataA() {
        return Single.<String>create(observer -> observer.onSuccess("DataA"))
                .subscribeOn(Schedulers.io());
    }

    private Single<String> getDataB() {
        return Single.<String>create(observer -> observer.onSuccess("DataB"))
                .subscribeOn(Schedulers.io());
    }

    private Single<String> getDataAsSingle(int i) {
        return Single.just("Done: " + i);
    }
}
