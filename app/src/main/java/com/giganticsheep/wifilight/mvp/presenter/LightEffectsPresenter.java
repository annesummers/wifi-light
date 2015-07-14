package com.giganticsheep.wifilight.mvp.presenter;

import com.squareup.otto.Subscribe;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightEffectsPresenter extends LightFragmentPresenter {

    public LightEffectsPresenter(Injector injector) {
        super(injector);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregisterForEvents(this);
    }

    @Subscribe
    public void handleLightChanged(LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
