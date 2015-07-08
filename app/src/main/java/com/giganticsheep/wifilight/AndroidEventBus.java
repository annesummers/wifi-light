package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.base.EventBus;
import com.squareup.otto.Bus;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class AndroidEventBus implements EventBus {

    private final Bus bus = new Bus();

    /**
     * Posts a message to the global message bus.  Classes must register to receive messages
     * and much subscribe to  a specific message to receive it
     *
     * @param messageObject the object to post to the bus
     */
    public Observable postMessage(final Object messageObject) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
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

    public void registerForEvents(Object myClass) {
        bus.register(myClass);
    }

    public void unregisterForEvents(Object myClass) {
        bus.unregister(myClass);
    }
}