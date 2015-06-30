package com.giganticsheep.wifilight.base;

import rx.Observable;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public interface EventBus {
    Observable postMessage(final Object messageObject);

    void registerForEvents(Object myClass);
    void unregisterForEvents(Object myClass);
}
