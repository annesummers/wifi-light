package com.giganticsheep.wifilight.ui.control.network;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.util.Constants;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/08/15. <p>
 * (*_*)
 */
class OnLocationClickListener extends OnNetworkItemClickListener
                                implements ExpandableListView.OnGroupClickListener {


    public OnLocationClickListener(@NonNull final Injector injector) {
        super(injector);
    }

    @Override
    public boolean onGroupClick(ExpandableListView listView,
                                View v,
                                int groupPosition,
                                long id) {
        String locationId = ((LightLocationAdapter) listView.getExpandableListAdapter()).getGroup(groupPosition);

        selectorChanged(LightSelector.SelectorType.LOCATION, locationId);

        presenter.setPosition(groupPosition, Constants.INVALID, Constants.INVALID);
        presenter.fetchLocation(locationId);

        listView.setSelectedGroup(groupPosition);

        /*if(listView.isGroupExpanded(groupPosition)) {
            listView.collapseGroup(groupPosition);
        } else {
            listView.expandGroup(groupPosition);
        }*/

        closeDrawer();

        return false;
    }
}
