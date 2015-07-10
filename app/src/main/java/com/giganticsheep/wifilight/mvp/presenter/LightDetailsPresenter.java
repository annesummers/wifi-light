package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.ui.dagger.MainActivityComponent;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightDetailsPresenter extends LightPresenter {

    public LightDetailsPresenter(MainActivityComponent component) {
        super(component);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregisterForEvents(this);
    }

    @Subscribe
    public void lightLoaded(LightNetwork.LightDetailsEvent event) {
        getView().lightChanged(event.light());
    }
}
