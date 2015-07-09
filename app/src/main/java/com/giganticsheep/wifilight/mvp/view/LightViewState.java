package com.giganticsheep.wifilight.mvp.view;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightViewState implements ViewState<LightView> {

    final int STATE_SHOW_LOADING = 0;
    final int STATE_SHOW_LIGHT_DETAILS = 1;
    final int STATE_SHOW_ERROR = 2;

    int state = STATE_SHOW_LOADING;

    public void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    public void setShowError() {
        state = STATE_SHOW_ERROR;
    }

    public void setShowLightDetails() {
        state = STATE_SHOW_LIGHT_DETAILS;
    }

    @Override
    public void apply(LightView lightView, boolean retained) {
        switch (state) {
            case STATE_SHOW_LOADING:
                lightView.showLoading();
                break;

            case STATE_SHOW_ERROR:
                lightView.showError();
                break;

            case STATE_SHOW_LIGHT_DETAILS:
                lightView.showLightDetails();
                break;
        }
    }
}
