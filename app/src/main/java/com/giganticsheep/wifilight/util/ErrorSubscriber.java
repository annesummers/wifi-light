package com.giganticsheep.wifilight.util;

import android.support.annotation.NonNull;

import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by anne on 14/07/15.
 * (*_*)
 */
public class ErrorSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() { }

    @Override
    public void onError(@NonNull Throwable e) {
        Timber.e(e, "ErrorSubscriber");
    }

    @Override
    public void onNext(T object) { }
}
