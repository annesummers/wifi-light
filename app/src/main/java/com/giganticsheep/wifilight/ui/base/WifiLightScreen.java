package com.giganticsheep.wifilight.ui.base;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.error.ErrorStrings;

import javax.inject.Inject;

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
