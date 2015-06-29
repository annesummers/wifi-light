package com.giganticsheep.wifilight.ui.fragment;

import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.ui.base.BaseApplication;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightEffectsPresenter extends LightPresenter {
    public LightEffectsPresenter(LightNetwork lightNetwork, BaseApplication.EventBus eventBus) {
        super(lightNetwork, eventBus);

        eventBus.registerForEvents(this);
    }

    @Override
    public void fragmentDestroyed() {
        eventBus.unregisterForEvents(this);
    }
}
