package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public abstract class BrightnessPresenterBase extends FragmentPresenterBase {

    public BrightnessPresenterBase(@NonNull final Injector injector,
                                   @NonNull final ControlPresenter controlPresenter) {
        super(injector, controlPresenter);
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
}
