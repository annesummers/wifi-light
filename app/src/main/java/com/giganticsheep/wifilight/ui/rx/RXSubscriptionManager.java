package com.giganticsheep.wifilight.ui.rx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.observables.AndroidObservable;
import rx.functions.Action0;

/**
 * Created by anne on 22/06/15.
 */
public class RXSubscriptionManager {
    private final Map<Integer, Subscription> subscriptions = new HashMap<Integer, Subscription>();

    private int nextNumber = 1;
    private final List<Integer> freeList = new ArrayList<Integer>();

    private final RXActivity activity;
    private final RXFragment fragment;

    /**
     * Instantiates a new Subscription manager.
     *
     * @param activity the activity
     */
    public RXSubscriptionManager(RXActivity activity) {
        this.activity = activity;
        fragment = null;
    }

    /**
     * Instantiates a new Subscription manager.
     *
     * @param fragment the fragment
     */
    public RXSubscriptionManager(RXFragment fragment) {
        activity = null;
        this.fragment = fragment;
    }

    /**
     * Remove void.
     *
     * @param which the which
     */
    void remove(int which) {
        synchronized (subscriptions) {
            subscriptions.remove(which);

            freeList.add(which);
        }
    }

    /**
     * Add int.
     *
     * @param subscription the subscription
     * @return the int
     */
    int add(Subscription subscription) {
        synchronized (subscriptions) {
            int nextNumber = this.nextNumber;
            if(freeList.size() > 0) {
                nextNumber = freeList.get(0);
            }

            subscriptions.put(nextNumber, subscription);

            if(nextNumber == this.nextNumber) {
                this.nextNumber++;
            }

            return nextNumber;
        }
    }

    /**
     * Unsubscribe void.
     */
    public void unsubscribe() {
        synchronized (subscriptions) {
            for (Subscription subscription : subscriptions.values()) {
                if (subscription != null) {
                    subscription.unsubscribe();
                }
            }

            subscriptions.clear();
        }
    }

    /**
     * Helper method for calling Observables through AndroidObservable and handling
     * the resulting subscriptions.  All subscriptions are unsubscribed from in the
     * onDestroy()
     *
     * @param <T> the type of objects received from the observable
     * @param observable the observable
     */
    public <T> Observable<? extends T> call(final Observable<? extends T> observable) {
        final int[] subscriptionNumber = new int[1];

        if(activity != null) {
            return AndroidObservable.bindActivity(activity, observable);
        } else {
            return AndroidObservable.bindFragment(activity, observable);
        }
    }
}
