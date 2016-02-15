package com.giganticsheep.wifilight.ui.status.location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.model.Location;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

/**
 * Handles the different states the LightView can be in. <p>
 *
 * Created by anne on 29/06/15. <p>
 *     
 * (*_*)
 */
public class LocationStatusViewState extends ViewStateBase<LocationStatusView> {

    private static final String KEY_LOCATION = "key_light";

    public static final int STATE_SHOW_LOCATION = STATE_MAX + 1;

    private Location location;

    /**
     * Sets the state to STATE_SHOW_LOCATION_CONNECTED.
     */
    public void setShowLocation(@NonNull final Location location) {
        state = STATE_SHOW_LOCATION;

        this.location = location;
    }

    @Override
    public void apply(@NonNull final LocationStatusView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LOCATION:
                lightView.showLocation(location);
                break;

            default:
                super.apply(lightView, retained);
                break;
        }
    }

    @Override
    public void saveInstanceState(@NonNull final Bundle bundle) {
        super.saveInstanceState(bundle);

        bundle.putParcelable(KEY_LOCATION, location);
    }

    @Nullable
    @Override
    public RestoreableViewState<LocationStatusView> restoreInstanceState(@Nullable final Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        super.restoreInstanceState(bundle);

        location = bundle.getParcelable(KEY_LOCATION);

        return this;
    }

    public int state() {
        return state;
    }
}
