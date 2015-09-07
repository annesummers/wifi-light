package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;

import hugo.weaving.DebugLog;
import rx.Subscriber;

/**
 * Used by {@link LightControlActivity} as an interface between the UI and the model. <p>
 *
 * Created by anne on 09/07/15.<p>
 *
 * (*_*)
 */
public class LightControlPresenter extends LightPresenterBase {

    public LightControlPresenter(@NonNull final Injector injector) {
        super(injector);
    }

    @DebugLog
    final public void fetchLightNetwork() {
        if (isViewAttached()) {
            getView().showLoading();
        }

        subscribe(lightControl.fetchLightNetwork(), new Subscriber<LightNetwork>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                eventBus.postMessage(new ErrorEvent(e));
            }

            @Override
            public void onNext(LightNetwork lightNetwork) {
                eventBus.postMessage(new LightControl.FetchLightNetworkEvent(lightNetwork));
            }
        });
    }
}
