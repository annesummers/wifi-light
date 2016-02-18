package com.giganticsheep.nofragmentbase.ui.base;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by anne on 18/02/16.
 */
public interface SubscriptionHandler {
    /**
     * Subscribes to observable with subscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param subscriber the Subscriber to subscribe with
     * @param <T> the type the Observable is observing
     *
     * @return the Subscription that was returned when subscribing to observable
     */
    <T> Subscription subscribe(@NonNull final Observable<T> observable,
                               @NonNull final Subscriber<T> subscriber);

    /**
     * Unsubscribes from all existing Subscriptions.
     * All subsequent Subscriptions will be immediately unsubscribed from as well.
     */
    void shutdown();

    /**
     * Unsubscribes from all existing Subscriptions.
     */
    void clearSubscriptions();
}
