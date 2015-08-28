package com.giganticsheep.wifilight.ui.control.network;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.ui.base.ViewStateBase;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetworkViewState extends ViewStateBase<LightNetworkView> {

    private static final String KEY_LIGHT_NETWORK = "key_light_network";
    private static final String KEY_GROUP_POSITION = "key_group_position";
    private static final String KEY_LIGHT_POSITION = "key_child_position";
    private static final String KEY_LOCATION_POSITION = "key_location_position";

    final public static int STATE_SHOW_LIGHT_NETWORK = STATE_MAX + 1;

    private LightNetwork lightNetwork;
    private int locationPosition;
    private int groupPosition;
    private int lightPosition;

    /**
     * Sets the state to STATE_SHOW_LIGHT_NETWORK.
     */
    public void setShowLightNetwork(@NonNull final LightNetwork lightNetwork,
                                    final int locationPosition,
                                    final int groupPosition,
                                    final int lightPosition) {
        state = STATE_SHOW_LIGHT_NETWORK;

        this.lightNetwork = lightNetwork;
        this.locationPosition = locationPosition;
        this.groupPosition = groupPosition;
        this.lightPosition = lightPosition;
    }

    @DebugLog
    @Override
    public void apply(@NonNull final LightNetworkView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LIGHT_NETWORK:
                lightView.showLightNetwork(lightNetwork, locationPosition, groupPosition, lightPosition);
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
        bundle.putInt(KEY_GROUP_POSITION, groupPosition);
        bundle.putInt(KEY_LIGHT_POSITION, lightPosition);
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
        groupPosition = bundle.getInt(KEY_GROUP_POSITION);
        lightPosition = bundle.getInt(KEY_LIGHT_POSITION);

        return this;
    }

    int state() {
        return state;
    }
}
