package com.giganticsheep.wifilight.ui.fragment;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.api.model.StatusResponse;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.squareup.otto.Subscribe;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightColourPresenter extends LightPresenter {
    public LightColourPresenter(LightNetwork lightNetwork,
                                EventBus eventBus) {
        super(lightNetwork, eventBus);

        eventBus.registerForEvents(this);
    }

    @Override
    public void fragmentDestroyed() {
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() {
                    }

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
    public void lightLoaded(LightNetwork.LightDetailsEvent event) {
        getView().lightChanged(event.light());
    }
}
