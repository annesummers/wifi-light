package com.giganticsheep.wifilight.base.error;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.network.error.WifiLightAPIException;
import com.giganticsheep.wifilight.api.network.error.WifiLightNetworkException;
import com.giganticsheep.wifilight.api.network.error.WifiLightServerException;
import com.giganticsheep.wifilight.base.EventBus;

import javax.inject.Inject;

import rx.Subscriber;
import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 26/07/15. <p>
 * (*_*)
 */
public class ErrorSubscriber<T> extends Subscriber<T> {

    private final EventBus eventBus;
    private final ErrorStrings errorStrings;

    @Inject
    public ErrorSubscriber(@NonNull final EventBus eventBus,
                           @NonNull final ErrorStrings errorStrings) {
        this.eventBus = eventBus;
        this.errorStrings = errorStrings;
    }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable error) {
        Timber.e(error.getMessage());
        eventBus.postMessage(new ErrorEvent(transformError(error)))
                .subscribe(new SilentErrorSubscriber());
    }

    @Override
    public void onNext(T object) { }

    private Throwable transformError(Throwable error) {
        StringBuilder errorString = new StringBuilder();

        if(error instanceof WifiLightAPIException) {
            errorString.append(errorStrings.apiErrorString());
            errorString.append('\n');
            errorString.append(error.getMessage());
        } else if(error instanceof WifiLightServerException) {
            errorString.append(errorStrings.serverErrorString());

            if(BuildConfig.DEBUG) {
                errorString.append('\n');
                errorString.append(error.getMessage());
            }
        } else if(error instanceof WifiLightNetworkException) {
            errorString.append(errorStrings.networkErrorString());

            if(BuildConfig.DEBUG) {
                errorString.append('\n');
                errorString.append(error.getMessage());
            }
        } else {
            errorString.append(errorStrings.generalErrorString());
            errorString.append('\n');
            errorString.append(error.getMessage());
        }

        return new Exception(errorString.toString());
    }
}
