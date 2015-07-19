package com.giganticsheep.wifilight.base;

import rx.Observable;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public interface EventBus {
    <T> Observable<T> postMessage(T messageObject);

    <T> Observable<T> registerForEvents(T myClass);
    <T> Observable<T> unregisterForEvents(T myClass);
}
