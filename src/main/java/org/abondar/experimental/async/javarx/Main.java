package org.abondar.experimental.async.javarx;

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

        showMorse();


        client.processTweets();
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
