package com.giganticsheep.wifilight.ui.status.group;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.base.ViewStateBase;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

/**
 * Handles the different states the LightView can be in. <p>
 *
 * Created by anne on 29/06/15. <p>
 *     
 * (*_*)
 */
public class GroupStatusViewState extends ViewStateBase<GroupStatusView> {

    private static final String KEY_GROUP = "key_light";

    public static final int STATE_SHOW_GROUP = STATE_MAX + 1;

    private Group group;

    /**
     * Sets the state to STATE_SHOW_GROUP_CONNECTED.
     */
    public void setShowGroup(@NonNull final Group group) {
        state = STATE_SHOW_GROUP;

        this.group = group;
    }

    @Override
    public void apply(@NonNull final GroupStatusView lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_GROUP:
                lightView.showGroup(group);
                break;

            default:
                super.apply(lightView, retained);
                break;
        }
    }

    @Override
    public void saveInstanceState(@NonNull final Bundle bundle) {
        super.saveInstanceState(bundle);

        bundle.putParcelable(KEY_GROUP, group);
    }

    @Nullable
    @Override
    public RestoreableViewState<GroupStatusView> restoreInstanceState(@Nullable final Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        super.restoreInstanceState(bundle);

        group = bundle.getParcelable(KEY_GROUP);

        return this;
    }

    public int state() {
        return state;
    }
}
