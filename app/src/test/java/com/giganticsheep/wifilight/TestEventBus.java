package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.base.EventBus;

import rx.Observable;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class TestEventBus implements EventBus {

    @Override
    public Observable postMessage(Object messageObject) {
        return Observable.just(messageObject);
    }

    @Override
    public void registerForEvents(Object myClass) {

    }

    @Override
    public void unregisterForEvents(Object myClass) {

    }
}
