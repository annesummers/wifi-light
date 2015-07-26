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
    private static final String KEY_GROUP_POSITION = "key_group_position";
    private static final String KEY_CHILD_POSITION = "key_child_position";

    private final int STATE_SHOW_LIGHT_NETWORK = STATE_MAX + 1;

    private LightNetwork lightNetwork;
    private int groupPosition;
    private int childPosition;

    /**
     * Sets the state to STATE_SHOW_LIGHT_NETWORK.
     */
    public void setShowLightNetwork(@NonNull final LightNetwork lightNetwork,
                                    final int groupPosition,
                                    final int childPosition) {
        state = STATE_SHOW_LIGHT_NETWORK;

        this.lightNetwork = lightNetwork;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }

    @DebugLog
    @Override
    public void apply(@NonNull final LightNetworkView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LIGHT_NETWORK:
                lightView.showLightNetwork(lightNetwork, groupPosition, childPosition);
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
        bundle.putInt(KEY_GROUP_POSITION, groupPosition);
        bundle.putInt(KEY_CHILD_POSITION, childPosition);
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
        groupPosition = bundle.getInt(KEY_GROUP_POSITION);
        childPosition = bundle.getInt(KEY_CHILD_POSITION);

        return this;
    }
}
