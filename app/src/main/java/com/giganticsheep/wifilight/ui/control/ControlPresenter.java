package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ui.ErrorEvent;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;

import hugo.weaving.DebugLog;
import rx.Subscriber;
import timber.log.Timber;

/**
 * Used by {@link LightControlActivity} as an interface between the UI and the model. <p>
 *
 * Created by anne on 09/07/15.<p>
 *
 * (*_*)
 */
public class ControlPresenter extends LightPresenterBase {

    @DebugLog
    public ControlPresenter(@NonNull final Injector injector) {
        super(injector);
    }

    @DebugLog
    final public void fetchLightNetwork() {
        if (isViewAttached()) {
            getView().showLoading();
        }

        subscribe(lightControl.fetchLightNetwork(), new FetchLightsErrorSubscriber<>());
    }

    private class FetchLightsErrorSubscriber<T> extends Subscriber<T> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Timber.e(e, "ErrorSubscriber");
            eventBus.postMessage(new ErrorEvent(e));
        }

        @Override
        public void onNext(T object) {
        }
    }
}
