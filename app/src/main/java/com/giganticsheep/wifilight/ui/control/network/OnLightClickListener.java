package com.giganticsheep.wifilight.ui.control.network;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import com.giganticsheep.wifilight.api.model.LightSelector;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/08/15. <p>
 * (*_*)
 */
class OnLightClickListener extends OnNetworkItemClickListener
                            implements ExpandableListView.OnChildClickListener {

    private final int locationPosition;

    public OnLightClickListener(@NonNull final Injector injector,
                                final int locationPosition) {
        super(injector);

        this.locationPosition = locationPosition;
    }

    @Override
    public boolean onChildClick(ExpandableListView listView,
                                View v,
                                int groupPosition,
                                int childPosition,
                                long id) {
        String lightId = ((LightGroupAdapter) listView.getExpandableListAdapter()).getChild(groupPosition, childPosition);

        selectorChanged(LightSelector.SelectorType.LIGHT, lightId);

        presenter.setPosition(locationPosition, groupPosition, childPosition);
        presenter.fetchLight(lightId);

        listView.expandGroup(groupPosition);
        listView.setSelectedChild(groupPosition, childPosition, true);

        closeDrawer();

        return true;
    }
}
