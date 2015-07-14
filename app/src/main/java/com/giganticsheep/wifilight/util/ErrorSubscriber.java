package com.giganticsheep.wifilight.util;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.base.Logger;

import rx.Subscriber;

/**
 * Created by anne on 14/07/15.
 * (*_*)
 */
public class ErrorSubscriber<T> extends Subscriber<T> {

    private final Logger logger;

    public ErrorSubscriber(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(@NonNull Throwable e) {
        logger.error(e.getMessage());
    }

    @Override
    public void onNext(T object) { }
}
