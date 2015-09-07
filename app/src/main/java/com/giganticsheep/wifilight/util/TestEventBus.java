package com.giganticsheep.wifilight.util;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.base.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class TestEventBus implements EventBus {

    private final List<Object> uiListeners = new ArrayList<>();

    private final List<Object> uiMessages = new ArrayList<>();

    private final List<Object> listeners = new ArrayList<>();

    private final List<Object> messages = new ArrayList<>();

    @Override
    public void postMessage(@NonNull final Object messageObject) {
        messages.add(messageObject);
    }

    @Override
    public void registerForEvents(@NonNull final Object myClass) {
        listeners.add(myClass);
    }

    @Override
    public void unregisterForEvents(@NonNull final Object myClass) {
        listeners.remove(myClass);
    }

    @Override
    public void postUIMessage(Object messageObject) {
        uiMessages.add(messageObject);
    }

    @Override
    public void registerForUIEvents(Object myClass) {
        uiListeners.add(myClass);
    }

    @Override
    public void unregisterForUIEvents(Object myClass) {
        uiListeners.remove(myClass);
    }

    @Override
    public void postMessageSticky(Object messageObject) {
        messages.add(messageObject);
    }

    public Object popLastMessage() {
        Object lastMessage = messages.get(messages.size() - 1);
        messages.remove(lastMessage);

        return lastMessage;
    }

    public Object popLastUIMessage() {
        Object lastMessage = uiMessages.get(uiMessages.size() - 1);
        uiMessages.remove(lastMessage);

        return lastMessage;
    }
}
