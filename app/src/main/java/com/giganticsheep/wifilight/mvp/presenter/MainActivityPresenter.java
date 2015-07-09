package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.base.EventBus;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */
public class MainActivityPresenter extends LightPresenter {
    public MainActivityPresenter(LightNetwork lightNetwork, EventBus eventBus) {
        super(lightNetwork, eventBus);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregisterForEvents(this);
    }

    public void fetchLights() {
        if (isViewAttached()) {
            getView().showLoading();
        }

        lightNetwork.fetchLights(true)
                .subscribe(lightSubscriber);
    }
}
