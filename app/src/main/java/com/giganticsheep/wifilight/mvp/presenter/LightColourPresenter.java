package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.api.model.StatusResponse;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.squareup.otto.Subscribe;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 *
 * Presenter to handle the setting of the selected Lights' colours and brightness.
 */
public class LightColourPresenter extends LightPresenterBase {

    public LightColourPresenter(Injector injector) {
        super(injector);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        eventBus.unregisterForEvents(this);
    }

    /**
     * Sets the hue of the selected lights.
     *
     * @param hue the hue to set; an int between 0 and 360.
     * @param duration the duration to set the hue for.
     */
    public void setHue(final int hue, float duration) {
        setColour(lightNetwork.setHue(hue, duration));
    }

    /**
     * Sets the saturation of the selected lights.
     *
     * @param saturation the saturation to set; an int between 0 and 100.
     * @param duration the duration to set the saturation for.
     */
    public void setSaturation(final int saturation, float duration) {
        setColour(lightNetwork.setSaturation(saturation, duration));
    }

    /**
     * Sets the brightness of the selected lights.
     *
     * @param brightness the brightness to set; an int between 0 and 100.
     * @param duration the duration to set the brightness for.
     */
    public void setBrightness(final int brightness, float duration) {
        setColour(lightNetwork.setBrightness(brightness, duration));
    }

    /**
     * Sets the kelvin (warmth) of the selected lights.
     *
     * @param kelvin the kelvin to set; an int between 2500 and 9000.
     * @param duration the duration to set the kelvin for.
     */
    public void setKelvin(final int kelvin, float duration) {
        setColour(lightNetwork.setKelvin(kelvin, duration));
    }

    /**
     * Sets the power of the selected lights.
     *
     * @param power ON or OFF.
     * @param duration how long to set the power change for.
     */
    public void setPower(final ModelConstants.Power power, final float duration) {
        compositeSubscription.add(lightNetwork.setPower(power, duration)
                .subscribe(setLightSubscriber));
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

    private void setColour(Observable<StatusResponse> observable) {
        compositeSubscription.add(observable
                .subscribe(setLightSubscriber));
    }
}
