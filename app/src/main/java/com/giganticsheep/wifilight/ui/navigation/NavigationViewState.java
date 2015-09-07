package com.giganticsheep.wifilight.ui.navigation;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ui.base.ViewStateBase;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class NavigationViewState extends ViewStateBase<NavigationView> {

    final private static String KEY_GROUP = "key_group";
    final private static String KEY_LOCATION = "key_location";

    final public static int STATE_SHOW_GROUP = STATE_MAX + 1;
    final public static int STATE_SHOW_LOCATION = STATE_MAX + 2;

    String groupId;
    String locationId;

    public void showGroup(final String groupId) {
        state = STATE_SHOW_GROUP;

        this.groupId = groupId;
    }

    public void showLocation(final String locationId) {
        state = STATE_SHOW_LOCATION;

        this.locationId = locationId;
    }

    @Override
    public void apply(@NonNull final NavigationView navigationView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_GROUP:
                navigationView.showGroup(groupId);
                break;

            case STATE_SHOW_LOCATION:
                navigationView.showLocation(locationId);
                break;

            default:
                super.apply(navigationView, retained);
                break;
        }
    }
}
