package com.giganticsheep.wifilight.util;

import com.giganticsheep.wifilight.base.Logger;

import rx.Subscriber;

/**
 * Created by anne on 14/07/15.
 * (*_*)
 */
public class ErrorSubscriber extends Subscriber {

    private final Logger logger;

    public ErrorSubscriber(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        logger.error(e.getMessage());
    }

    @Override
    public void onNext(Object o) {
    }
}
