package com.giganticsheep.wifilight.util;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.base.EventBus;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class AndroidEventBus implements EventBus {

    private final de.greenrobot.event.EventBus bus = new de.greenrobot.event.EventBus();
    private final de.greenrobot.event.EventBus uiBus = new de.greenrobot.event.EventBus();

    /**
     * Posts a message to the global event bus.  Classes must register to receive messages
     * and much subscribe to  a specific message to receive it
     *
     * @param messageObject the object to post to the bus
     */
    @NonNull
    public void postMessage(@NonNull final Object messageObject) {
        bus.post(messageObject);
    }

    /**
     * Registers the class myClass to receive events from the global event bus.
     *
     * @param myClass the class to register
     */
    public void registerForEvents(@NonNull final Object myClass) {
        bus.register(myClass);
    }

    /**
     * Unregisters the class myClass from the global event bus.
     *
     * @param myClass the class to unregister
     */
    public void unregisterForEvents(@NonNull final Object myClass) {
        bus.unregister(myClass);
    }

    /**
     * Posts a message to the global event bus.  Classes must register to receive messages
     * and much subscribe to  a specific message to receive it
     *
     * @param messageObject the object to post to the bus
     */
    @NonNull
    public void postUIMessage(@NonNull final Object messageObject) {
        uiBus.post(messageObject);
    }

    /**
     * Registers the class myClass to receive events from the global event bus.
     *
     * @param myClass the class to register
     */
    public void registerForUIEvents(@NonNull final Object myClass) {
        uiBus.registerSticky(myClass);
    }

    /**
     * Unregisters the class myClass from the global event bus.
     *
     * @param myClass the class to unregister
     */
    public void unregisterForUIEvents(@NonNull final Object myClass) {
        uiBus.unregister(myClass);
    }

    @Override
    public void postMessageSticky(Object messageObject) {
        bus.postSticky(messageObject);
    }
}
