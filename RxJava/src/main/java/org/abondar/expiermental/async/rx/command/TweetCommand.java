package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.expiermental.async.rx.client.TwitterClient;
import rx.Observable;
import rx.Subscription;
import twitter4j.Status;


/**
 * Created by abondar on 2/2/17.
 */
public class TweetCommand implements Command {

    @Override
    public void execute() {
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

        client.processTweets();
        client.mapTweets();
    }
}
