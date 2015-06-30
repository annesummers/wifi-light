package com.giganticsheep.wifilight.ui.presenter;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightDetailsPresenter extends LightPresenter {
    public LightDetailsPresenter(LightNetwork lightNetwork, EventBus eventBus) {
        super(lightNetwork, eventBus);

        eventBus.registerForEvents(this);
    }

    @Override
    public void fragmentDestroyed() {
        eventBus.unregisterForEvents(this);
    }

    @Subscribe
    public void lightLoaded(LightNetwork.LightDetailsEvent event) {
        getView().lightChanged(event.light());
    }
}
