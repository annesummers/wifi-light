package com.giganticsheep.wifilight.ui.rx;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.observables.AndroidObservable;
import rx.functions.Action0;

/**
 * Created by anne on 22/06/15.
 */
public class RXSubscriptionManager {
    private final Map<Integer, Subscription> mSubscriptions = new HashMap<Integer, Subscription>();

    private int mNextNumber = 1;
    private final List<Integer> mFreeList = new ArrayList<Integer>();

    private final RXActivity mActivity;
    private final RXFragment mFragment;

    /**
     * Instantiates a new Subscription manager.
     *
     * @param activity the activity
     */
    public RXSubscriptionManager(RXActivity activity) {
        mActivity = activity;
        mFragment = null;
    }

    /**
     * Instantiates a new Subscription manager.
     *
     * @param fragment the fragment
     */
    public RXSubscriptionManager(RXFragment fragment) {
        mActivity = null;
        mFragment = fragment;
    }

    /**
     * Remove void.
     *
     * @param which the which
     */
    void remove(int which) {
        synchronized (mSubscriptions) {
            mSubscriptions.remove(which);

            mFreeList.add(which);
        }
    }

    /**
     * Add int.
     *
     * @param subscription the subscription
     * @return the int
     */
    int add(Subscription subscription) {
        synchronized (mSubscriptions) {
            int nextNumber = mNextNumber;
            if(mFreeList.size() > 0) {
                nextNumber = mFreeList.get(0);
            }

            mSubscriptions.put(nextNumber, subscription);

            if(nextNumber == mNextNumber) {
                mNextNumber++;
            }

            return nextNumber;
        }
    }

    /**
     * Unsubscribe void.
     */
    public void unsubscribe() {
        synchronized (mSubscriptions) {
            for (Subscription subscription : mSubscriptions.values()) {
                if (subscription != null) {
                    subscription.unsubscribe();
                }
            }

            mSubscriptions.clear();
        }
    }

    /**
     * Helper method for calling Observables through AndroidObservable and handling
     * the resulting subscriptions.  All subscriptions are unsubscribed from in the
     * onDestroy()
     *
     * @param <T> the type of objects received from the observable
     * @param observable the observable
     * @param subscriber the subscriber
     */
   /* public <T> void call(final Observable<? extends T> observable, final Subscriber<T> subscriber) {
        final int[] subscriptionNumber = new int[1];

        if(mActivity != null) {
            subscriptionNumber[0] = add(AndroidObservable.bindActivity(mActivity, observable)
                    .doOnTerminate(new Action0() {
                        @Override
                        public void call() {
                            remove(subscriptionNumber[0]);
                        }
                    }).subscribe(subscriber));
        } else {
            subscriptionNumber[0] = add(AndroidObservable.bindFragment(mFragment, observable)
                    .doOnTerminate(new Action0() {
                        @Override
                        public void call() {
                            remove(subscriptionNumber[0]);
                        }
                    })
                    .subscribe(subscriber));
        }
    }*/
}
