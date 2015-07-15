package com.giganticsheep.wifilight.util;

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
    public Observable postMessage(final Object messageObject) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber subscriber) {
                messages.add(messageObject);
            }
        });
    }

    @Override
    public void registerForEvents(Object myClass) {
        listeners.add(myClass);
    }

    @Override
    public void unregisterForEvents(Object myClass) {
        listeners.remove(myClass);
    }

    public Object popLastMessage() {
        Object lastMessage = messages.get(messages.size() - 1);
        messages.remove(lastMessage);

        return lastMessage;
    }
}
