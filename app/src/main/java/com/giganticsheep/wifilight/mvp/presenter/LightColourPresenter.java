package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.api.model.StatusResponse;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.ui.dagger.MainActivityComponent;
import com.squareup.otto.Subscribe;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightColourPresenter extends LightPresenter {

    public LightColourPresenter(Injector injector) {
        super(injector);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregisterForEvents(this);
    }

    /**
     * @param hue the hue to set the enabled lights
     */
    public void setHue(final int hue, float duration) {
        setColour(lightNetwork.setHue(hue, duration));
    }

    /**
     * @param saturation the saturation to set the enabled lights
     */
    public void setSaturation(final int saturation, float duration) {
        setColour(lightNetwork.setSaturation(saturation, duration));
    }

    /**
     * @param brightness the brightness to set the enabled lights
     */
    public void setBrightness(final int brightness, float duration) {
        setColour(lightNetwork.setBrightness(brightness, duration));
    }

    /**
     * @param kelvin the kelvin (warmth to set the enabled lights
     */
    public void setKelvin(final int kelvin, float duration) {
        setColour(lightNetwork.setKelvin(kelvin, duration));
    }

    private void setColour(Observable<StatusResponse> observable) {
        observable
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().showError(e);
                        }
                    }

                    @Override
                    public void onNext(StatusResponse response) {
                    }
                });
    }

    /**
     * @param power ON or OFF
     * @param duration how long to set the power change for
     */
    public void setPower(final ModelConstants.Power power, final float duration) {
        lightNetwork.setPower(power, duration)
                .subscribe(new Subscriber<StatusResponse>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e);
                }
            }

            @Override
            public void onNext(StatusResponse response) { }
        });
    }

    @Subscribe
    public void handleLightDetails(LightNetwork.LightDetailsEvent event) {
        getView().lightChanged(event.light());
    }
}
