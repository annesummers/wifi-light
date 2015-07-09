package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.api.network.LightNetwork;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightEffectsPresenter extends LightPresenter {
    public LightEffectsPresenter(LightNetwork lightNetwork, EventBus eventBus) {
        super(lightNetwork, eventBus);

        eventBus.registerForEvents(this);
    }

    @Override
    public void fragmentDestroyed() {
        eventBus.unregisterForEvents(this);
    }
}
