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
    public boolean onChildClick(ExpandableListView listView,
                                View v,
                                int groupPosition,
                                int childPosition,
                                long id) {
        locationPosition = groupPosition;

        listView.expandGroup(groupPosition);
        listView.setSelectedChild(groupPosition, childPosition, true);

        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView listView,
                                View v,
                                int groupPosition,
                                long id) {

        String groupId = ((LightGroupAdapter) listView.getExpandableListAdapter()).getGroup(groupPosition);

        selectorChanged(LightSelector.SelectorType.GROUP, groupId);

        presenter.setPosition(locationPosition, groupPosition, Constants.INVALID);
        presenter.fetchGroup(groupId);

        listView.setSelectedGroup(groupPosition);

        if(listView.isGroupExpanded(groupPosition)) {
            listView.collapseGroup(groupPosition);
        } else {
            listView.expandGroup(groupPosition);
        }

        closeDrawer();

        return true;
    }
}
