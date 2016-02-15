package com.giganticsheep.wifilight.ui.locations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetworkViewState extends ViewStateBase<LightNetworkView> {

    private static final String KEY_LIGHT_NETWORK = "key_light_network";
    private static final String KEY_LOCATION_POSITION = "key_location_position";

    final public static int STATE_SHOW_LIGHT_NETWORK = STATE_MAX + 1;

    private LightNetwork lightNetwork;
    private int locationPosition;

    /**
     * Sets the state to STATE_SHOW_GROUP.
     */
    public void setShowLightNetwork(@NonNull final LightNetwork lightNetwork,
                                    final int locationPosition) {
        state = STATE_SHOW_LIGHT_NETWORK;

        this.lightNetwork = lightNetwork;
        this.locationPosition = locationPosition;
    }

    @DebugLog
    @Override
    public void apply(@NonNull final LightNetworkView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LIGHT_NETWORK:
                lightView.showLightNetwork(lightNetwork, locationPosition);
                break;

            default:
                super.apply(lightView, retained);
                break;
        }
    }

    @DebugLog
    @Override
    public void saveInstanceState(final Bundle bundle) {
        super.saveInstanceState(bundle);

        bundle.putParcelable(KEY_LIGHT_NETWORK, lightNetwork);
        bundle.putInt(KEY_LOCATION_POSITION, locationPosition);
    }

    @DebugLog
    @Nullable
    @Override
    public RestoreableViewState<LightNetworkView> restoreInstanceState(final Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        super.restoreInstanceState(bundle);

        lightNetwork = bundle.getParcelable(KEY_LIGHT_NETWORK);
        locationPosition = bundle.getInt(KEY_LOCATION_POSITION);

        return this;
    }

    int state() {
        return state;
    }
}
