package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
public class LightStatusPresenter extends LightFragmentPresenterBase {
    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector              an Injector used to inject this object into a Component that will
     *                              provide the injected class members.
     * @param lightControlPresenter the Presenter that handles the details of the current Light.
     */
    public LightStatusPresenter(@NonNull final Injector injector,
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
     * Sets the power of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param power ON or OFF.
     * @param duration how long to set the power change.
     */
    public void setPower(final LightControl.Power power, final float duration) {
        subscribe(lightControl.setPower(power, duration), new SetLightSubscriber());
    }
}
