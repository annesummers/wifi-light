package com.giganticsheep.wifilight.ui.colour;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.ui.base.light.BrightnessPresenterBase;

import hugo.weaving.DebugLog;

/**
 * Presenter to handle the setting of the selected Lights' colours and brightness.<p>
 *
 * Created by anne on 29/06/15.<p>
 *
 * (*_*)
 */
class ColourPresenter extends BrightnessPresenterBase {

    public ColourPresenter(@NonNull final Injector injector) {
        super(injector);
    }

    /**
     * Sets the hue of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param hue the hue to set; an int between 0 and 360.
     * @param duration the duration to fade into the new hue.
     */
    @DebugLog
    public void setHue(final int hue, float duration) {
        subscribe(lightControl.setHue(hue, duration), new SetLightSubscriber());
    }

    /**
     * Sets the saturation of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param saturation the saturation to set; an int between 0 and 100.
     * @param duration the duration to fade into the new saturation.
     */
    @DebugLog
    public void setSaturation(final int saturation, float duration) {
        subscribe(lightControl.setSaturation(saturation, duration), new SetLightSubscriber());
    }
}
