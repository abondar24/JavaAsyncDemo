package org.abondar.expiermental.async.rx.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.expiermental.async.rx.data.Tweet;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class NotificationsCommand  implements Command {

    @Override
    public void execute() {
        Observable<Tweet> tweets = Observable.empty();
        subscribeToNotifications(tweets);
        captureNotifications(tweets);
        disableNotifications(tweets);
    }

    private void subscribeToNotifications(Observable<Tweet> tweets) {
        System.out.println("Subscribing to notifications.");
        tweets.subscribe(System.out::println,
                Throwable::printStackTrace,
                NotificationsCommand::noMore);
    }

    private void captureNotifications(Observable<Tweet> tweets) {
        System.out.println("Capturing notifications.");
        Observer<Tweet> observer = new Observer<Tweet>() {

            @Override
            public void onCompleted() {
                noMore();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(Tweet tweet) {
                System.out.println(tweet);
            }
        };

        tweets.subscribe(observer);
    }

    public static void disableNotifications(Observable<Tweet> tweets) {

        System.out.println("Disabling from subscription");
        Subscriber<Tweet> subscriber = new Subscriber<Tweet>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(Tweet tweet) {
                if (tweet.getText().contains("java")) {
                    unsubscribe();
                }
            }
        };

        subscriber.add(tweets.subscribe());


    }


    private static void noMore() {
        System.out.println("No more notifications");
    }
}
