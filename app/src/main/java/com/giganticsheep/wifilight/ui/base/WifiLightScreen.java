package com.giganticsheep.wifilight.ui.base;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by anne on 15/02/16.
 */
public abstract class WifiLightScreen<T extends Screen.ViewActionBase> extends Screen<T> {

    @Inject protected EventBus eventBus;
    @Inject protected ErrorStrings errorStrings;
    @Inject public LightControl lightControl;

    protected WifiLightScreen(ScreenGroup screenGroup) {
        super(screenGroup);
    }

    protected WifiLightScreen(Parcel in) {
        super(in);
    }

    /**
     * Subscribes to observable with ErrorSubscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param <T> the type the Observable is observing
     */
    public <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber(eventBus, errorStrings));
    }

    protected abstract class ScreenSubscriber<O> extends Subscriber<O> {

        @Override
        public void onCompleted() {
            getViewState().setStateShowing();
        }

        @Override
        public void onError(@NonNull final Throwable e) {
            getViewState().setStateError(e);
        }
    }
}
