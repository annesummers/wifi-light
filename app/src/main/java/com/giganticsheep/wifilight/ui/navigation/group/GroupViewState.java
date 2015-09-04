package com.giganticsheep.wifilight.ui.navigation.group;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.base.ViewStateBase;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class GroupViewState extends ViewStateBase<GroupView> {

    final private static String KEY_GROUP = "key_group";

    final public static int STATE_SHOW_GROUP = STATE_MAX + 1;

    private Group group;

    public void setShowGroup(@NonNull final Group group) {
        state = STATE_SHOW_GROUP;

        this.group = group;
    }

    @Override
    public void apply(@NonNull final GroupView groupView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_GROUP:
                groupView.showGroup(group);
                break;

            default:
                super.apply(groupView, retained);
                break;
        }
    }

    @Override
    public void saveInstanceState(Bundle bundle) {
        bundle.putParcelable(KEY_GROUP, group);
    }

    @Override
    public RestoreableViewState restoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        super.restoreInstanceState(bundle);

        group = bundle.getParcelable(KEY_GROUP);

        return this;
    }
}
