package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.ErrorEvent;
import com.giganticsheep.wifilight.util.ErrorSubscriber;

import retrofit.RetrofitError;
import rx.Subscriber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 26/07/15. <p>
 * (*_*)
 */
public class ServerErrorEventSubscriber<T> extends Subscriber<T> {

    private final EventBus eventBus;

    public ServerErrorEventSubscriber(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable error) {
        eventBus.postMessage(new ErrorEvent(transformServerError(error))).subscribe(new ErrorSubscriber<>());
    }

    @Override
    public void onNext(T object) { }

    private Throwable transformServerError(Throwable error) {
        if(error instanceof RetrofitError) {
            return new Exception("A network error has occurred.  Please check your network connection and try again.");
        }

        return new Exception("A network error has occurred.  Please check your network connection and try again.");
    }
}
