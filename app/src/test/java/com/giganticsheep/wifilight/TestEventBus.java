package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.base.EventBus;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class TestEventBus implements EventBus {

    List<Object> listeners = new ArrayList();

    private Bus eventBus = new Bus();

    @Override
    public Observable postMessage(Object messageObject) {
        return Observable.just(messageObject);
    }

    @Override
    public void registerForEvents(Object myClass) {
        listeners.add(myClass);
    }

    @Override
    public void unregisterForEvents(Object myClass) {
        listeners.remove(myClass);
    }
}
