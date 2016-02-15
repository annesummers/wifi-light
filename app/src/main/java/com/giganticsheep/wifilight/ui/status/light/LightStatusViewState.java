package com.giganticsheep.wifilight.ui.status.light;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.model.Light;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

/**
 * Handles the different states the LightView can be in. <p>
 *
 * Created by anne on 29/06/15. <p>
 *     
 * (*_*)
 */
public class LightStatusViewState extends ViewStateBase<LightStatusView> {

    private static final String KEY_LIGHT = "key_light";

    public static final int STATE_SHOW_LIGHT = STATE_MAX + 1;

    private Light light;

    /**
     * Sets the state to STATE_SHOW_LIGHT_CONNECTED.
     */
    public void setShowLight(@NonNull final Light light) {
        state = STATE_SHOW_LIGHT;

        this.light = light;
    }

    @Override
    public void apply(@NonNull final LightStatusView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LIGHT:
                lightView.showLight(light);
                break;

            default:
                super.apply(lightView, retained);
                break;
        }
    }

    @Override
    public void saveInstanceState(@NonNull final Bundle bundle) {
        super.saveInstanceState(bundle);

        bundle.putParcelable(KEY_LIGHT, light);
    }

    @Nullable
    @Override
    public RestoreableViewState<LightStatusView> restoreInstanceState(@Nullable final Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        super.restoreInstanceState(bundle);

        light = bundle.getParcelable(KEY_LIGHT);

        return this;
    }

    public int state() {
        return state;
    }
}
