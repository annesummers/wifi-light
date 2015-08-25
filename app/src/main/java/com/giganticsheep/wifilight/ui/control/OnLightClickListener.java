package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.base.EventBus;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/08/15. <p>
 * (*_*)
 */
class OnLightClickListener extends OnListItemClickListener
        implements ExpandableListView.OnChildClickListener {

    private final int locationPosition;

    public OnLightClickListener(@NonNull final EventBus eventBus,
                                @NonNull final LightNetworkPresenter presenter,
                                @NonNull final LightControlActivity activity,
                                final int locationPosition) {
        super(eventBus, presenter, activity);

        this.locationPosition = locationPosition;
    }

    @Override
    public boolean onChildClick(ExpandableListView listView,
                                View v,
                                int groupPosition,
                                int childPosition,
                                long id) {
        String lightId = ((LightGroupAdapter) listView.getAdapter()).getChild(groupPosition, childPosition);

        selectorChanged(LightSelector.SelectorType.LIGHT, lightId);

        presenter.setPosition(locationPosition, groupPosition, childPosition);
        presenter.fetchLight(lightId);

        listView.expandGroup(groupPosition);
        listView.setSelectedChild(groupPosition, childPosition, true);

        closeDrawer();

        return true;
    }
}
