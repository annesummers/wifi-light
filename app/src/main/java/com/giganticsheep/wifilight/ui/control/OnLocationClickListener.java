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
class OnLocationClickListener extends OnListItemClickListener
        implements ExpandableListView.OnGroupClickListener {

    public OnLocationClickListener(@NonNull final EventBus eventBus,
                                   @NonNull final LightNetworkPresenter presenter,
                                   @NonNull final LightControlActivity activity) {
        super(eventBus, presenter, activity);
    }

    @Override
    public boolean onGroupClick(ExpandableListView listView,
                                View v,
                                int groupPosition,
                                long id) {
        String locationId = ((LightLocationAdapter) listView.getAdapter()).getGroup(groupPosition);

        selectorChanged(LightSelector.SelectorType.LOCATION, locationId);

        //presenter.setLocationPosition(groupPosition);
        presenter.setPosition(groupPosition, Constants.INVALID, Constants.INVALID);
        presenter.fetchLocation(locationId);

        listView.setSelectedGroup(groupPosition);

        closeDrawer();

        return true;
    }
}
