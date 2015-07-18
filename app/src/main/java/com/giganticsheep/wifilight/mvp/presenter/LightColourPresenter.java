package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.squareup.otto.Subscribe;

/**
 * Presenter to handle the setting of the selected Lights' colours and brightness.<p>
 *
 * Created by anne on 29/06/15.<p>
 *
 * (*_*)
 */
public class LightColourPresenter extends LightFragmentPresenterBase {

    public LightColourPresenter(@NonNull final Injector injector,
                                @NonNull final LightControlPresenter lightControlPresenter) {
        super(injector, lightControlPresenter);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        eventBus.unregisterForEvents(this);
    }

    /**
     * Sets the hue of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param hue the hue to set; an int between 0 and 360.
     * @param duration the duration to fade into the new hue.
     */
    public void setHue(final int hue, float duration) {
        subscribe(lightControl.setHue(hue, duration), new SetLightSubscriber());
    }

    /**
     * Sets the saturation of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param saturation the saturation to set; an int between 0 and 100.
     * @param duration the duration to fade into the new saturation.
     */
    public void setSaturation(final int saturation, float duration) {
        subscribe(lightControl.setSaturation(saturation, duration), new SetLightSubscriber());
    }

    /**
     * Sets the brightness of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param brightness the brightness to set; an int between 0 and 100.
     * @param duration the duration to fade into the new brightness.
     */
    public void setBrightness(final int brightness, float duration) {
        subscribe(lightControl.setBrightness(brightness, duration), new SetLightSubscriber());
    }

    /**
     * Sets the kelvin (warmth) of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param kelvin the kelvin to set; an int between 2500 and 9000.
     * @param duration the duration to fade into the new kelvin.
     */
    public void setKelvin(final int kelvin, float duration) {
        subscribe(lightControl.setKelvin(kelvin, duration), new SetLightSubscriber());
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Light} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Light}.
     */
    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
