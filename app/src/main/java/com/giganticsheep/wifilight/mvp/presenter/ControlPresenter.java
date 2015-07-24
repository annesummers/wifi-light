package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

import rx.Subscriber;

/**
 * Used by {@link com.giganticsheep.wifilight.ui.LightControlActivity} as an interface between the UI and the model. <p>
 *
 * Created by anne on 09/07/15.<p>
 *
 * (*_*)
 */
public class ControlPresenter extends LightPresenterBase {

    private Light light;

    public ControlPresenter(@NonNull final Injector injector) {
        super(injector);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<ControlPresenter>());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<ControlPresenter>());
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

        subscribe(lightControl.fetchLights(fetchFromServer), new Subscriber<Light>(){

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
                if(getLight() != null && light.id().equals(getLight().id())) {
                    ControlPresenter.this.light = light;

                    subscribe(eventBus.postMessage(new LightChangedEvent(light)));
                }
            }
        });
    }

    @Override
    public final void fetchLight(final String id) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        fetchLight(id, new Subscriber<Light>() {

            @Override
            public void onCompleted() {
                subscribe(eventBus.postMessage(new LightChangedEvent(light)));
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e);
                }
            }

            @Override
            public void onNext(Light light) {
                ControlPresenter.this.light = light;
            }
        });
    }

    @Override
    public Light getLight() {
        return light;
    }

    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }

    @NonNull
    @Override
    public String toString() {
        return "LightControlPresenter{" +
                "Light=" + ((light != null) ? light.id() : "null") +
                '}';
    }
}
