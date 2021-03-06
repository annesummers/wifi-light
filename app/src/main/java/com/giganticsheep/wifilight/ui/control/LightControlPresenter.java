package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;

import hugo.weaving.DebugLog;

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

        subscribe(lightControl.fetchLightNetwork());
    }
}
