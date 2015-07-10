package com.giganticsheep.wifilight.mvp.view;

import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightViewState implements RestoreableViewState<LightView> {

    private static final String KEY_STATE = "key_state";

    private final int STATE_SHOW_LOADING = 0;
    private final int STATE_SHOW_LIGHT_DETAILS = 1;
    private final int STATE_SHOW_ERROR = 2;

    private int state = STATE_SHOW_LOADING;

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

            default:
                break;
        }
    }

    @Override
    public void saveInstanceState(Bundle bundle) {
        bundle.putInt(KEY_STATE, state);
    }

    @Override
    public RestoreableViewState<LightView> restoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        state = bundle.getInt(KEY_STATE);

        return this;
    }

    @Override
    public String toString() {
        return "LightViewState{" +
                "state=" + state +
                '}';
    }
}
