package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.model.Light;
import com.squareup.otto.Subscribe;

import rx.Subscriber;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 *
 * Used by the LightControlActivity.
 */
public class LightControlPresenter extends LightPresenterBase {

    private String currentLightId;

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
    public void fetchLights(boolean fetchFromServer) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        subscribe(lightControl.fetchLights(fetchFromServer), new Subscriber<Light>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e);
                }
            }

            @Override
            public void onNext(Light light) {
                subscribe(eventBus.postMessage(new LightChangedEvent(light)));
            }
        });
    }

    /**
     * @return the id of the current displayed Light.
     */
    public String getCurrentLightId() {
        return currentLightId;
    }

    @Subscribe
    public void handleLightChanged(LightChangedEvent event) {
        currentLightId = event.getLight().id();

        handleLightChanged(event.getLight());
    }

    @Override
    public String toString() {
        return "MainActivityPresenter{" +
                "currentLightId='" + currentLightId +
                '}';
    }
}
