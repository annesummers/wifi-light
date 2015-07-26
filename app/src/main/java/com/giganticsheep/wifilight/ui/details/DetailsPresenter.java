package com.giganticsheep.wifilight.ui.details;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ui.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.base.light.LightView;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

import hugo.weaving.DebugLog;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
class DetailsPresenter extends LightPresenterBase {

    public DetailsPresenter(@NonNull final Injector injector) {
        super(injector);
    }

    @DebugLog
    @Override
    public void attachView(LightView view) {
        super.attachView(view);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @DebugLog
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @DebugLog
    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
