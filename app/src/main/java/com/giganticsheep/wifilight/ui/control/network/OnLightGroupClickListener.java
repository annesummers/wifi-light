package com.giganticsheep.wifilight.ui.control.network;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.util.Constants;

import javax.inject.Inject;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/08/15. <p>
 * (*_*)
 */
class OnLightGroupClickListener extends OnNetworkItemClickListener
                                implements ExpandableListView.OnChildClickListener,
                                            ExpandableListView.OnGroupClickListener {

    private int locationPosition;

    @Inject
    public OnLightGroupClickListener(@NonNull final Injector injector) {
        super(injector);
    }

    @Override
    public boolean onChildClick(@NonNull final ExpandableListView listView,
                                @NonNull final View v,
                                final int groupPosition,
                                final int childPosition,
                                final long id) {
        locationPosition = groupPosition;

        listView.expandGroup(groupPosition);
        listView.setSelectedChild(groupPosition, childPosition, true);

        return false;
    }

    @Override
    public boolean onGroupClick(@NonNull final ExpandableListView listView,
                                @NonNull final View v,
                                final int groupPosition,
                                final long id) {

        String groupId = ((LightGroupAdapter) listView.getExpandableListAdapter()).getGroup(groupPosition);

        selectorChanged(LightSelector.SelectorType.GROUP, groupId);

        presenter.setPosition(locationPosition, groupPosition, Constants.INVALID);
        presenter.fetchGroup(groupId);

        listView.setSelectedGroup(groupPosition);

        return false;
    }
}
