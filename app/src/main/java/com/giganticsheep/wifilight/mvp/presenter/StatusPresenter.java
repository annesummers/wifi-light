package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
public class StatusPresenter extends FragmentPresenterBase {
    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector              an Injector used to inject this object into a Component that will
     *                              provide the injected class members.
     * @param controlPresenter the Presenter that handles the details of the current Light.
     */
    public StatusPresenter(@NonNull final Injector injector,
                           @NonNull final ControlPresenter controlPresenter) {
        super(injector, controlPresenter);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<StatusPresenter>(logger));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<StatusPresenter>(logger));
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

    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
