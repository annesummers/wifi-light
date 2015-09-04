package com.giganticsheep.wifilight.ui.navigation.location;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.ui.base.ViewStateBase;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class LocationViewState extends ViewStateBase<LocationView> {

    final private static String KEY_LOCATION = "key_location";

    final public static int STATE_SHOW_LOCATION = STATE_MAX + 1;

    private Location location;

    public void setShowLocation(@NonNull final Location location) {
        state = STATE_SHOW_LOCATION;

        this.location = location;
    }

    @Override
    public void apply(@NonNull final LocationView locationView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LOCATION:
                locationView.showLocation(location);
                break;

            default:
                super.apply(locationView, retained);
                break;
        }
    }

    @Override
    public void saveInstanceState(Bundle bundle) {
        bundle.putParcelable(KEY_LOCATION, location);
    }

    @Override
    public RestoreableViewState restoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        super.restoreInstanceState(bundle);

        location = bundle.getParcelable(KEY_LOCATION);

        return this;
    }
}
