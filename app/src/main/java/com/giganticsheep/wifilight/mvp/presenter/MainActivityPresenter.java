package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.base.EventBus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */
public class MainActivityPresenter extends LightPresenter {

    private String currentLight;
    private ArrayList<String> newLightIds;

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

    public String getCurrentLight() {
        return currentLight;
    }

    @Subscribe
    public synchronized void handleFetchLightsSuccess(LightNetwork.FetchLightsSuccessEvent event) {
        //logger.debug("handleFetchLightsSuccess()");

        List<String> lightIds = newLightIds;

        if(lightIds.size() > 0) {
            currentLight = lightIds.get(0);
        }

        newLightIds = null;

        getView().showLightDetails();
    }

    @Subscribe
    public synchronized void handleLightDetails(LightNetwork.LightDetailsEvent event) {
        //logger.debug("handleLightDetails() " + event.light().toString());

        if(newLightIds == null) {
            newLightIds = new ArrayList<>();
        }

        newLightIds.add(event.light().id());

        getView().lightChanged(event.light());
    }
}
