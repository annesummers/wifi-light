package com.giganticsheep.wifilight.mvp.presenter;

import com.squareup.otto.Subscribe;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightDetailsPresenter extends LightFragmentPresenter {

    public LightDetailsPresenter(Injector injector) {
        super(injector);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this);
    }

    @Subscribe
    public void handleLightChanged(LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
