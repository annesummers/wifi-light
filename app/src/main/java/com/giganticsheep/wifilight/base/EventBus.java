package com.giganticsheep.wifilight.base;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public interface EventBus {
    void postMessage(Object messageObject);

    void registerForEvents(Object myClass);
    void unregisterForEvents(Object myClass);

    void postUIMessage(Object messageObject);

    void registerForUIEvents(Object myClass);
    void unregisterForUIEvents(Object myClass);

    void postMessageSticky(Object event);
}
