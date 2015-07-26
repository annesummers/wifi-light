package com.giganticsheep.wifilight.ui.base.light;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.base.ViewStateBase;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

import org.parceler.Parcels;

/**
 * Handles the different states the LightView can be in. <p>
 *
 * Created by anne on 29/06/15. <p>
 *     
 * (*_*)
 */
public class LightViewState extends ViewStateBase<LightView> {

    private static final String KEY_LIGHT = "key_light";

    private static final int STATE_SHOW_LIGHT_CONNECTED = STATE_MAX + 1;
    private static final int STATE_SHOW_LIGHT_CONNECTING = STATE_MAX + 2;
    private static final int STATE_SHOW_LIGHT_DISCONNECTED = STATE_MAX + 3;

    private Light light;

    /**
     * Sets the state to STATE_SHOW_LIGHT_CONNECTED.
     */
    public void setShowConnected(@NonNull final Light light) {
        state = STATE_SHOW_LIGHT_CONNECTED;

        this.light = light;
    }

    /**
     * Sets the state to STATE_SHOW_LIGHT_CONNECTING.
     */
    public void setShowConnecting(@NonNull final Light light) {
        state = STATE_SHOW_LIGHT_CONNECTING;

        this.light = light;
    }

    /**
     * Sets the state to STATE_SHOW_LIGHT_DISCONNECTED.
     */
    public void setShowDisconnected(@NonNull final Light light) {
        state = STATE_SHOW_LIGHT_DISCONNECTED;

        this.light = light;
    }

    @Override
    public void apply(@NonNull final LightView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LIGHT_CONNECTED:
                lightView.showConnected(light);
                break;

            case STATE_SHOW_LIGHT_CONNECTING:
                lightView.showConnecting(light);
                break;

            case STATE_SHOW_LIGHT_DISCONNECTED:
                lightView.showDisconnected(light);
                break;

            default:
                super.apply(lightView, retained);
                break;
        }
    }

    @Override
    public void saveInstanceState(@NonNull final Bundle bundle) {
        super.saveInstanceState(bundle);

        bundle.putParcelable(KEY_LIGHT, Parcels.wrap(light));
    }

    @Nullable
    @Override
    public RestoreableViewState<LightView> restoreInstanceState(@Nullable final Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        super.restoreInstanceState(bundle);

        light = Parcels.unwrap(bundle.getParcelable(KEY_LIGHT));

        return this;
    }
}
