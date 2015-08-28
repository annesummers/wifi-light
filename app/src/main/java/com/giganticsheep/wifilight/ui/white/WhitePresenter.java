package com.giganticsheep.wifilight.ui.white;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ui.base.light.BrightnessPresenter;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
class WhitePresenter extends BrightnessPresenter {

    public WhitePresenter(@NonNull final Injector injector) {
        super(injector);
    }

    /**
     * Sets the kelvin (warmth) of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param kelvin the kelvin to set; an int between 2500 and 9000.
     * @param duration the duration to fade into the new kelvin.
     */
    @DebugLog
    public void setKelvin(final int kelvin, float duration) {
        subscribe(lightControl.setKelvin(kelvin, duration), new SetLightSubscriber());
    }
}
