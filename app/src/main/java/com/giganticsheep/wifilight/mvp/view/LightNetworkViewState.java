package com.giganticsheep.wifilight.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.mvp.presenter.LightNetworkPresenter;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

import org.parceler.Parcels;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetworkViewState extends ViewStateBase<LightNetworkView> {

    private static final String KEY_LIGHT_NETWORK = "key_light_network";

    private final int STATE_SHOW_LIGHT_NETWORK = STATE_MAX + 1;

    private LightNetworkPresenter.LightNetwork lightNetwork;

    /**
     * Sets the state to STATE_SHOW_LIGHT_NETWORK.
     */
    public void setShowLightNetwork() {
        state = STATE_SHOW_LIGHT_NETWORK;
    }

    @Override
    public void apply(@NonNull final LightNetworkView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LIGHT_NETWORK:
                lightView.showLightNetwork(lightNetwork);
                break;

            default:
                super.apply(lightView, retained);
                break;
        }
    }

    @Override
    public void saveInstanceState(Bundle bundle) {
        super.saveInstanceState(bundle);

        bundle.putParcelable(KEY_LIGHT_NETWORK, Parcels.wrap(lightNetwork));
    }

    @Nullable
    @Override
    public RestoreableViewState<LightNetworkView> restoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        super.restoreInstanceState(bundle);

        lightNetwork = Parcels.unwrap(bundle.getParcelable(KEY_LIGHT_NETWORK));

        return this;
    }

    public void setLightNetwork(LightNetworkPresenter.LightNetwork lightNetwork) {
        this.lightNetwork = lightNetwork;
    }
}
