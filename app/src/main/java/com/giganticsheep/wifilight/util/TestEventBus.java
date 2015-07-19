package com.giganticsheep.wifilight.util;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.base.EventBus;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class TestEventBus implements EventBus {

    List<Object> listeners = new ArrayList();

    private final Bus eventBus = new Bus();

    private final List<Object> messages = new ArrayList<>();

    @Override
    public <T> Observable<T> postMessage(@NonNull final T messageObject) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(@NonNull Subscriber<? super T> subscriber) {
                messages.add(messageObject);

                subscriber.onNext(messageObject);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public <T> Observable<T> registerForEvents(@NonNull final T myClass) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(@NonNull Subscriber<? super T> subscriber) {
                listeners.add(myClass);

                subscriber.onNext(myClass);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public <T> Observable<T> unregisterForEvents(@NonNull final T myClass) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(@NonNull Subscriber<? super T> subscriber) {
                listeners.remove(myClass);

                subscriber.onNext(myClass);
                subscriber.onCompleted();
            }
        });
    }

    public Object popLastMessage() {
        Object lastMessage = messages.get(messages.size() - 1);
        messages.remove(lastMessage);

        return lastMessage;
    }
}
