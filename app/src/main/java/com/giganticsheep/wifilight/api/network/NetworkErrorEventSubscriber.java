package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.network.error.WifiLightAPIException;
import com.giganticsheep.wifilight.api.network.error.WifiLightNetworkException;
import com.giganticsheep.wifilight.api.network.error.WifiLightServerException;
import com.giganticsheep.wifilight.base.ErrorEvent;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.util.ErrorSubscriber;

import rx.Subscriber;
import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 26/07/15. <p>
 * (*_*)
 */
public class NetworkErrorEventSubscriber<T> extends Subscriber<T> {

    private final EventBus eventBus;

    public NetworkErrorEventSubscriber(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable error) {
        Timber.e(error.getMessage());
        eventBus.postMessage(new ErrorEvent(transformError(error)))
                .subscribe(new ErrorSubscriber<>());
    }

    @Override
    public void onNext(T object) { }

    private Throwable transformError(Throwable error) {
        if(error instanceof WifiLightAPIException) {
            return new Exception("A bug needs fixing; " + error.getMessage());
        }

        if(error instanceof WifiLightServerException) {
            String errorString = "A server error has occurred.  Pleas try again later.";

            if(BuildConfig.DEBUG) {
                errorString += " " + error.getMessage();
            }

            return new Exception(errorString);
        }

        if(error instanceof WifiLightNetworkException) {
            String errorString = "A network error has occurred.  Please check your network connection and try again.";

            if(BuildConfig.DEBUG) {
                errorString += " " + error.getMessage();
            }

            return new Exception(errorString);
        }

        return new Exception("An error has occurred; " + error.getMessage());
    }
}
