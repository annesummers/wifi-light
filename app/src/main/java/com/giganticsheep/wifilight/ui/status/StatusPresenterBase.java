package com.giganticsheep.wifilight.ui.status;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;
import com.giganticsheep.wifilight.ui.base.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.PresenterBase;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
public abstract class StatusPresenterBase<V extends ViewBase> extends PresenterBase<V> {

    /**
     * Sets the power of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param isOn
     */
    @DebugLog
    public void setPower(final boolean isOn, final LightSelector selector) {
        if(isOn) {
            subscribe(lightControl.setPower(LightControl.Power.ON, LightControlActivity.DEFAULT_DURATION),
                    new SetLightSubscriber());
        } else if(!isOn){
            subscribe(lightControl.setPower(LightControl.Power.OFF, LightControlActivity.DEFAULT_DURATION),
                    new SetLightSubscriber());
        }
    }

    @DebugLog
    public void lightChanged(@NonNull final String lightId) {
        eventBus.postMessage(new LightChangedEvent(lightId));
    }

    public class SetLightSubscriber extends ErrorSubscriber<LightStatus> {

        public SetLightSubscriber() {
            super(eventBus, errorStrings);
        }

        @Override
        public void onNext(@NonNull final LightStatus light) {
            lightChanged(light.getId());
        }
    }
}
