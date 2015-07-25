package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class DetailsPresenter extends LightPresenterBase {

    public DetailsPresenter(@NonNull final Injector injector) {
        super(injector);
    }

    @Override
    public void attachView(LightView view) {
        super.attachView(view);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
