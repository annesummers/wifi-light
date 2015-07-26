package com.giganticsheep.wifilight.util;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.base.EventBus;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class AndroidEventBus implements EventBus {

    private final de.greenrobot.event.EventBus bus = new de.greenrobot.event.EventBus();

    /**
     * Posts a message to the global event bus.  Classes must register to receive messages
     * and much subscribe to  a specific message to receive it
     *
     * @param messageObject the object to post to the bus
     */
    @NonNull
    public <T> Observable<T> postMessage(@NonNull final T messageObject) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(@NonNull Subscriber<? super T> subscriber) {
                try {
                    bus.post(messageObject);
                } catch (Exception e) {
                    subscriber.onError(e);
                }

                subscriber.onNext(messageObject);
                subscriber.onCompleted();
            }
        }).subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Registers the class myClass to receive events from the global event bus.
     *
     * @param myClass the class to register
     */
    public <T> Observable<T> registerForEvents(@NonNull final T myClass) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(@NonNull Subscriber<? super T> subscriber) {
                try {
                    bus.register(myClass);
                } catch (Exception e) {
                    subscriber.onError(e);
                }

                subscriber.onNext(myClass);
                subscriber.onCompleted();
            }
        }).subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Unregisters the class myClass from the global event bus.
     *
     * @param myClass the class to unregister
     */
    public <T> Observable<T> unregisterForEvents(@NonNull final T myClass) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(@NonNull Subscriber<? super T> subscriber) {
                try {
                    bus.unregister(myClass);
                } catch (Exception e) {
                    subscriber.onError(e);
                }

                subscriber.onNext(myClass);
                subscriber.onCompleted();
            }
        }).subscribeOn(AndroidSchedulers.mainThread());
    }
}
