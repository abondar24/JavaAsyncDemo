package org.abondar.experimental.javarxdemo;

import rx.Completable;
import rx.Observable;
import rx.Subscription;
import twitter4j.Status;

import java.time.DayOfWeek;

import static rx.Observable.just;

/**
 * Created by abondar on 2/2/17.
 */
public class Main {

    public static void main(String[] args) {
        Basics.helloRx();
        Basics.inMemoryData();
        Basics.syncComputation();
        Basics.syncAsyncComputation();
        Basics.twoThreads();
        Basics.singles();
        Basics.singles1();

        Basics.masteringObservable();
        Basics.masteringObservableDeeper();
        Basics.multipleSubscribers();
        Basics.loopsAndSubscribers();
        Basics.observableByTimer();
        Basics.interval();


        TwitterClient client = new TwitterClient();
        Observable<Status> observable = client.consumeTweets();
        Subscription sub1 = observable.subscribe();
        System.out.println("Subscribed 1");
        Subscription sub2 = observable.subscribe();
        System.out.println("Subscribed 2");
        sub1.unsubscribe();
        System.out.println("Unsubscribed 1");
        sub2.unsubscribe();
        System.out.println("Unsubscribed 2");
        Basics.simpleFilterWithMap();
        Basics.numbersFlatMap();
        showMorse();
        Basics.shakespeare();
        Basics.trueFalse();


                //these methods are just skeletons to understand. there is no actual subscribtion made
        Completable completable = Basics.writeToDb("salo");
        Basics.subscribeToNotfications();
        Basics.captureAllNotifications();
        Basics.listenerControl();
        Basics.listenerControl1();
        Basics.delayed("sss");
        Basics.rxLoad(24);
        Basics.rxLoad1(1);
        client.processTweets();
        Basics.simpleFilter();
        client.mapTweets();
        showForDay();
    }

    public static void showMorse(){
        just('S','p','a','r','t','a')
                .map(Character::toLowerCase)
                .flatMap(Basics::toMorseCode)
        .subscribe(System.out::println);
    }

    public static void showForDay(){
        Observable
                .just(DayOfWeek.SUNDAY,DayOfWeek.MONDAY)
                .concatMap(Basics::loadRecordsFor);
    }
}
