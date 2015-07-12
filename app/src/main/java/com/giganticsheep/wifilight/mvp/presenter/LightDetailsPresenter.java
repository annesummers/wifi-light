package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightDetailsPresenter extends LightPresenterBase {

    public LightDetailsPresenter(Injector injector) {
        super(injector);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregisterForEvents(this);
    }

    @Subscribe
    public void handleLightDetails(LightNetwork.LightDetailsEvent event) {
        getView().lightChanged(event.light());
    }
}
