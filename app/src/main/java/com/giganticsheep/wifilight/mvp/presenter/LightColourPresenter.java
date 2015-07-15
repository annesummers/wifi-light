package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.squareup.otto.Subscribe;

import org.jetbrains.annotations.NotNull;

/**
 * Presenter to handle the setting of the selected Lights' colours and brightness.<p>
 *
 * Created by anne on 29/06/15.<p>
 *
 * (*_*)
 */
public class LightColourPresenter extends LightFragmentPresenter {

    public LightColourPresenter(@NonNull final Injector injector,
                                @NotNull final LightControlPresenter lightControlPresenter) {
        super(injector, lightControlPresenter);

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
        subscribe(lightControl.setHue(hue, duration), new SetLightSubscriber());
    }

    /**
     * Sets the saturation of the selected lights.
     *
     * @param saturation the saturation to set; an int between 0 and 100.
     * @param duration the duration to set the saturation for.
     */
    public void setSaturation(final int saturation, float duration) {
        subscribe(lightControl.setSaturation(saturation, duration), new SetLightSubscriber());
    }

    /**
     * Sets the brightness of the selected lights.
     *
     * @param brightness the brightness to set; an int between 0 and 100.
     * @param duration the duration to set the brightness for.
     */
    public void setBrightness(final int brightness, float duration) {
        subscribe(lightControl.setBrightness(brightness, duration), new SetLightSubscriber());
    }

    /**
     * Sets the kelvin (warmth) of the selected lights.
     *
     * @param kelvin the kelvin to set; an int between 2500 and 9000.
     * @param duration the duration to set the kelvin for.
     */
    public void setKelvin(final int kelvin, float duration) {
        subscribe(lightControl.setKelvin(kelvin, duration), new SetLightSubscriber());
    }

    /**
     * Sets the power of the selected lights.
     *
     * @param power ON or OFF.
     * @param duration how long to set the power change for.
     */
    public void setPower(final LightControl.Power power, final float duration) {
        subscribe(lightControl.setPower(power, duration), new SetLightSubscriber());
    }

    /**
     * Called with the details of a Light to display.
     *
     * @param event a LightChangedEvent; contains a Light
     */
    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
