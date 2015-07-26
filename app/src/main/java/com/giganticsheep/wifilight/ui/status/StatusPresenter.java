package com.giganticsheep.wifilight.ui.status;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.ui.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.base.light.LightView;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
class StatusPresenter extends LightPresenterBase {

    /**
     * Constructs the StatusPresenter object.  Injects itself into the supplied Injector.
     *
     * @param injector              an Injector used to inject this object into a Component that will
     *                              provide the injected class members.
     */
    public StatusPresenter(@NonNull final Injector injector) {
        super(injector);
    }

    @DebugLog
    @Override
    public void attachView(LightView view) {
        super.attachView(view);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @DebugLog
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
    @DebugLog
    public void setPower(final LightControl.Power power, final float duration) {
        subscribe(lightControl.setPower(power, duration), new SetLightSubscriber());
    }

    @DebugLog
    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }

    @DebugLog
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
