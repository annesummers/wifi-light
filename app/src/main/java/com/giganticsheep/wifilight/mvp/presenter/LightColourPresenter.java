package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.LightControl;
import com.squareup.otto.Subscribe;

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
        subscribe(lightControl.setHue(hue, duration), setLightSubscriber);
    }

    /**
     * Sets the saturation of the selected lights.
     *
     * @param saturation the saturation to set; an int between 0 and 100.
     * @param duration the duration to set the saturation for.
     */
    public void setSaturation(final int saturation, float duration) {
        subscribe(lightControl.setSaturation(saturation, duration), setLightSubscriber);
    }

    /**
     * Sets the brightness of the selected lights.
     *
     * @param brightness the brightness to set; an int between 0 and 100.
     * @param duration the duration to set the brightness for.
     */
    public void setBrightness(final int brightness, float duration) {
        subscribe(lightControl.setBrightness(brightness, duration), setLightSubscriber);
    }

    /**
     * Sets the kelvin (warmth) of the selected lights.
     *
     * @param kelvin the kelvin to set; an int between 2500 and 9000.
     * @param duration the duration to set the kelvin for.
     */
    public void setKelvin(final int kelvin, float duration) {
        subscribe(lightControl.setKelvin(kelvin, duration), setLightSubscriber);
    }

    /**
     * Sets the power of the selected lights.
     *
     * @param power ON or OFF.
     * @param duration how long to set the power change for.
     */
    public void setPower(final LightControl.Power power, final float duration) {
        subscribe(lightControl.setPower(power, duration), setLightSubscriber);
    }

    /**
     * Called with the details of a Light to display.
     *
     * @param event a LightChangedEvent; contains a Light
     */
    @Subscribe
    public void handleLightChanged(LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
