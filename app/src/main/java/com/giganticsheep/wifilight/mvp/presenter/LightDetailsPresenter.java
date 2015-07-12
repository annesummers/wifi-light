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
        super.onDestroy();

        eventBus.unregisterForEvents(this);
    }

    /**
     * Called every time a Light is fetched from the network.
     *
     * @param event a LightDetailsEvent; contains a Light
     */
    @Subscribe
    public void handleLightDetails(LightNetwork.LightDetailsEvent event) {
        getView().lightChanged(event.light());
    }
}
