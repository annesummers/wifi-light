package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

import rx.Subscriber;

/**
 * Created by anne on 09/07/15.<p>
 *
 * (*_*)<p>
 *
 * Used by LightControlActivity as an interface between the UI and the model.
 */
public class LightControlPresenter extends LightPresenterBase {

    private String currentLightId;

    public LightControlPresenter(@NonNull Injector injector) {
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
     *
     * @param fetchFromServer whether to fetch the information from the server or to use the
     *                        cached information if we have it.
     */
    public final void fetchLights(boolean fetchFromServer) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        subscribe(lightControl.fetchLights(fetchFromServer), new ErrorSubscriber<Light>(logger));
    }

    /**
     * Fetches the Light with the given id.
     *
     * @param id the id of the Light to fetch.
     */
    public final void fetchLight(final String id) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        fetchLight(id, new Subscriber<Light>() {

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
    public final String getCurrentLightId() {
        return currentLightId;
    }

    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        currentLightId = event.getLight().id();

        handleLightChanged(event.getLight());
    }

    @NonNull
    @Override
    public String toString() {
        return "LightControlPresenter{" +
                "currentLightId='" + currentLightId +
                '}';
    }
}
