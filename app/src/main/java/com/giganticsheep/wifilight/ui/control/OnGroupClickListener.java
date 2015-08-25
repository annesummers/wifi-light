package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.util.Constants;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/08/15. <p>
 * (*_*)
 */
class OnGroupClickListener extends OnListItemClickListener
        implements ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener {
    int locationPosition;

    public OnGroupClickListener(@NonNull final EventBus eventBus,
                                @NonNull final LightNetworkPresenter presenter,
                                @NonNull final LightControlActivity activity) {
        super(eventBus, presenter, activity);
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

        String groupId = ((LightGroupAdapter) listView.getAdapter()).getGroup(groupPosition);

        selectorChanged(LightSelector.SelectorType.GROUP, groupId);

        //presenter.setGroupPosition(locationPosition, groupPosition);

        presenter.setPosition(locationPosition, groupPosition, Constants.INVALID);
        presenter.fetchGroup(groupId);

        listView.setSelectedGroup(groupPosition);

        closeDrawer();

        return true;
    }
}
