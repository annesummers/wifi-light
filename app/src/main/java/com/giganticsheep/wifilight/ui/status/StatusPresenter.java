package com.giganticsheep.wifilight.ui.status;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;

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

    /**
     * Sets the power of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param isOn
     */
    @DebugLog
    public void setPower(boolean isOn) {
        if(isOn) {
            subscribe(lightControl.setPower(LightControl.Power.ON, LightControlActivity.DEFAULT_DURATION),
                    new SetLightSubscriber());
        } else if(!isOn){
            subscribe(lightControl.setPower(LightControl.Power.OFF, LightControlActivity.DEFAULT_DURATION),
                    new SetLightSubscriber());
        }
    }
}
