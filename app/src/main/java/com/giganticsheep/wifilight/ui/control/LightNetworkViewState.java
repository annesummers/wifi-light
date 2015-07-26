package com.giganticsheep.wifilight.ui.control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.ui.base.ViewStateBase;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

import org.parceler.Parcels;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetworkViewState extends ViewStateBase<LightNetworkView> {

    private static final String KEY_LIGHT_NETWORK = "key_light_network";
    private static final String KEY_SELECTED_POSITION = "key_selected_position";

    private final int STATE_SHOW_LIGHT_NETWORK = STATE_MAX + 1;

    private LightNetwork lightNetwork;
    private int selectedPosition;

    /**
     * Sets the state to STATE_SHOW_LIGHT_NETWORK.
     */
    public void setShowLightNetwork(@NonNull final LightNetwork lightNetwork,
                                    final int position) {
        state = STATE_SHOW_LIGHT_NETWORK;

        this.lightNetwork = lightNetwork;
        this.selectedPosition = position;
    }

    @DebugLog
    @Override
    public void apply(@NonNull final LightNetworkView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LIGHT_NETWORK:
                lightView.showLightNetwork(lightNetwork, selectedPosition);
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

        bundle.putParcelable(KEY_LIGHT_NETWORK, Parcels.wrap(lightNetwork));
        bundle.putInt(KEY_SELECTED_POSITION, selectedPosition);
    }

    @DebugLog
    @Nullable
    @Override
    public RestoreableViewState<LightNetworkView> restoreInstanceState(final Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        super.restoreInstanceState(bundle);

        lightNetwork = Parcels.unwrap(bundle.getParcelable(KEY_LIGHT_NETWORK));
        selectedPosition = bundle.getInt(KEY_SELECTED_POSITION);

        return this;
    }
}
