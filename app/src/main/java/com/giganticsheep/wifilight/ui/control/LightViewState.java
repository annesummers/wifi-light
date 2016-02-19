package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.nofragmentbase.ui.base.ViewState;

/**
 * Created by anne on 19/02/16.
 */
public interface LightViewState extends ViewState {
    void setStateDisconnected();
    void setStateConnecting();
}
