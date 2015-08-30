package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightSelector;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/08/15. <p>
 * (*_*)
 */
public abstract class LightControlEventCatcher {

    public void onEvent(final LightControl.LightSelectorChangedEvent event) {
        setCurrentSelector(event.selector());
    }

    abstract void setCurrentSelector(final LightSelector selector);
}
