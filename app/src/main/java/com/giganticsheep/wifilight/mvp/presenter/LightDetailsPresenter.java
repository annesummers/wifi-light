package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.squareup.otto.Subscribe;

import org.jetbrains.annotations.NotNull;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightDetailsPresenter extends LightFragmentPresenter {

    public LightDetailsPresenter(@NotNull final Injector injector,
                                 @NotNull final LightControlPresenter lightControlPresenter) {
        super(injector, lightControlPresenter);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this);
    }

    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
