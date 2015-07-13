package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.LightControl;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 *
 * Used by the LightControlActivity.
 */
public class LightControlPresenter extends LightPresenterBase {

    // TODO current Light stuff may not be the right way to handle this

    private String currentLight;
    private ArrayList<String> newLightIds;

    public LightControlPresenter(Injector injector) {
        super(injector);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this);
    }

    /**
     * Fetches all the available Lights.
     */
    public void fetchLights() {
        if (isViewAttached()) {
            getView().showLoading();
        }

        compositeSubscription.add(lightNetwork.fetchLights(true).subscribe(fetchLightSubscriber));
    }

    /**
     * @return the id of the current displayed Light.
     */
    public String getCurrentLightId() {
        return currentLight;
    }

    /**
     * Called when all the available Lights have been fetched from the network.
     *
     * @param event a FetchLightsSuccessEvent
     */
    @Subscribe
    public synchronized void handleFetchLightsSuccess(LightControl.FetchLightsSuccessEvent event) {
        logger.debug("handleFetchLightsSuccess()");

        List<String> lightIds = newLightIds;

        if(lightIds != null && lightIds.size() > 0) {
            currentLight = lightIds.get(0);
        }

        newLightIds = null;

        if (isViewAttached()) {
            getView().showMainView();
        }
    }

    /**
     * Called every time a Light has been fetched from the network.
     *
     * @param event a LightDetailsEvent; contains a Light
     */
    @Subscribe
    public synchronized void handleLightDetails(LightControl.LightDetailsEvent event) {
        logger.debug("handleLightDetails() " + event.light().toString());

        if(newLightIds == null) {
            newLightIds = new ArrayList<>();
        }

        newLightIds.add(event.light().id());

        if (isViewAttached()) {
            getView().lightChanged(event.light());
        }
    }

    @Override
    public String toString() {
        return "MainActivityPresenter{" +
                "currentLight='" + currentLight + '\'' +
                ", newLightIds=" + newLightIds +
                '}';
    }
}
