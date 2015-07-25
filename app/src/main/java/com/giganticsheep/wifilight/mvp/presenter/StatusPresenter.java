package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
public class StatusPresenter extends LightPresenterBase {

    /**
     * Constructs the StatusPresenter object.  Injects itself into the supplied Injector.
     *
     * @param injector              an Injector used to inject this object into a Component that will
     *                              provide the injected class members.
     */
    public StatusPresenter(@NonNull final Injector injector) {
        super(injector);
    }

    @Override
    public void attachView(LightView view) {
        super.attachView(view);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<>());
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

    public void setPower(boolean isOn) {
        if(isOn /*&& light != null && light.getPower() != LightControl.Power.ON*/) {
            subscribe(lightControl.setPower(LightControl.Power.ON, LightControlActivity.DEFAULT_DURATION),
                    new SetLightSubscriber());
        } else if(!isOn /*&& light != null && light.getPower() != LightControl.Power.OFF*/){
            subscribe(lightControl.setPower(LightControl.Power.OFF, LightControlActivity.DEFAULT_DURATION),
                    new SetLightSubscriber());
        }
    }
}
