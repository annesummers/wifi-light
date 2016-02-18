package com.giganticsheep.nofragmentbase.ui.base;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by anne on 18/02/16.
 */
public class SubscriptionDelegate implements SubscriptionHandler {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    public <T> Subscription subscribe(Observable<T> observable, Subscriber<T> subscriber) {
        Subscription subscription = observable.subscribe(subscriber);
        compositeSubscription.add(subscription);

        return subscription;
    }

    public void clearSubscriptions() {
        compositeSubscription.clear();
    }

    public void shutdown() {
        compositeSubscription.unsubscribe();
    }
}
