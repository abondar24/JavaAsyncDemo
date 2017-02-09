package org.abondar.experimental.javarxdemo;

/**
 * Created by abondar on 2/6/17.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;
import twitter4j.*;

import java.time.Instant;
import java.util.Date;


public class TwitterClient {
    private static final Logger log = LoggerFactory.getLogger(TwitterClient.class);

    public void processTweets() {
        consumeTweets().subscribe(
                status -> log.info("Status: {}", status),
                ex -> log.error("Error callback", ex)
        );
    }

    public Observable<Status> consumeTweets() {

        return Observable.create(subscriber -> {
            System.out.println("Establishing connection");
            TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
            twitterStream.addListener(new twitter4j.StatusListener() {

                @Override
                public void onException(Exception e) {
                    if (subscriber.isUnsubscribed()) {
                        twitterStream.shutdown();
                    } else {
                        subscriber.onError(e);
                    }
                }

                @Override
                public void onStatus(Status status) {
                    if (subscriber.isUnsubscribed()) {
                        twitterStream.shutdown();
                    } else {
                        subscriber.onNext(status);
                    }
                }

                @Override
                public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

                }

                @Override
                public void onTrackLimitationNotice(int i) {

                }

                @Override
                public void onScrubGeo(long l, long l1) {

                }

                @Override
                public void onStallWarning(StallWarning stallWarning) {

                }
            });
            subscriber.add(Subscriptions.create(twitterStream::shutdown));
        });


    }

    public void mapTweets(){
        Observable<Status> tweets = consumeTweets();
        Observable<Date> dates = tweets.map(Status::getCreatedAt);
        dates.subscribe(System.out::println);

        Observable<Instant> instants = tweets
                .map(Status::getCreatedAt)
                .map(Date::toInstant);
        instants.subscribe(System.out::println);
    }
}
