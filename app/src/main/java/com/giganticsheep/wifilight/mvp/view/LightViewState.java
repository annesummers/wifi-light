package com.giganticsheep.wifilight.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.model.Light;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

import org.parceler.Parcels;

/**
 * Handles the different states the LightView can be in. <p>
 *
 * Created by anne on 29/06/15. <p>
 *     
 * (*_*)
 */
public class LightViewState implements RestoreableViewState<LightView> {

    private static final String KEY_STATE = "key_state";
    private static final String KEY_LIGHT = "key_light";

    private final int STATE_SHOW_LOADING = 0;
    private final int STATE_SHOW_LIGHT_CONNECTED = 1;
    private final int STATE_SHOW_LIGHT_CONNECTING = 2;
    private final int STATE_SHOW_LIGHT_DISCONNECTED = 3;
    private final int STATE_SHOW_ERROR = 4;

    private int state = STATE_SHOW_LOADING;
    private Light light;

    /**
     * Sets the state to STATE_SHOW_LOADING.
     */
    public void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    /**
     * Sets the state to STATE_SHOW_ERROR.
     */
    public void setShowError() {
        state = STATE_SHOW_ERROR;
    }

    /**
     * Sets the state to STATE_SHOW_LIGHT_CONNECTED.
     */
    public void setShowConnected() {
        state = STATE_SHOW_LIGHT_CONNECTED;
    }

    /**
     * Sets the state to STATE_SHOW_LIGHT_CONNECTING.
     */
    public void setShowConnecting() {
        state = STATE_SHOW_LIGHT_CONNECTING;
    }

    /**
     * Sets the state to STATE_SHOW_LIGHT_DISCONNECTED.
     */
    public void setShowDisconnected() {
        state = STATE_SHOW_LIGHT_DISCONNECTED;
    }

    @Override
    public void apply(@NonNull final LightView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LOADING:
                lightView.showLoading();
                break;

            case STATE_SHOW_ERROR:
                lightView.showError();
                break;

            case STATE_SHOW_LIGHT_CONNECTED:
                lightView.showConnected();
                break;

            case STATE_SHOW_LIGHT_CONNECTING:
                lightView.showConnecting();
                break;

            case STATE_SHOW_LIGHT_DISCONNECTED:
                lightView.showDisconnected();
                break;

            default:
                break;
        }
    }

    @Override
    public void saveInstanceState(@NonNull Bundle bundle) {
        bundle.putInt(KEY_STATE, state);
        bundle.putParcelable(KEY_LIGHT, Parcels.wrap(light));
    }

    @Nullable
    @Override
    public RestoreableViewState<LightView> restoreInstanceState(@Nullable Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        state = bundle.getInt(KEY_STATE);
        light = Parcels.unwrap(bundle.getParcelable(KEY_LIGHT));

        return this;
    }

    public void setData(Light light) {
        this.light = light;
    }

    @NonNull
    @Override
    public String toString() {
        return "LightViewState{" +
                "state=" + state +
                '}';
    }
}
