package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.ErrorEvent;
import com.giganticsheep.wifilight.util.ErrorSubscriber;

import rx.Subscriber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 26/07/15. <p>
 * (*_*)
 */
public class ErrorEventSubscriber<T> extends Subscriber<T> {

    private final EventBus eventBus;

    public ErrorEventSubscriber(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        eventBus.postMessage(new ErrorEvent(e)).subscribe(new ErrorSubscriber<>());
    }

    @Override
    public void onNext(T object) {
    }
}
