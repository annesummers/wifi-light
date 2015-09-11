package com.giganticsheep.wifilight.ui.locations;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;
import com.giganticsheep.wifilight.util.Constants;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/08/15. <p>
 * (*_*)
 */
class LightNetworkClickListener {

    @Inject EventBus eventBus;
    @Inject LightNetworkPresenter presenter;

    private int checkedLightLocation = Constants.INVALID;

    private int locationGroupPosition = 0;

    private final ExpandableListView lightLocationListView;

    public LightNetworkClickListener(@NonNull final Injector injector,
                                     @NonNull final ExpandableListView lightLocationListView) {
        injector.inject(this);

        this.lightLocationListView = lightLocationListView;

        lightLocationListView.setOnGroupExpandListener(new OnLocationsClickListener());
        lightLocationListView.setOnChildClickListener(new OnLocationClickListener());
    }

    private int selectGroupAndReturnFlatPosition(@NonNull final ExpandableListView listView,
                                                 final int groupPosition) {
        listView.setSelectedGroup(groupPosition);

        long packedPos = ExpandableListView.getPackedPositionForGroup(groupPosition);
        int flatPos = listView.getFlatListPosition(packedPos);
        int adjustedPos = flatPos - listView.getFirstVisiblePosition();

        listView.setItemChecked(adjustedPos, true);

        return adjustedPos;
    }

    private void closeDrawer() {
        eventBus.postUIMessage(new NavigationActivity.CloseDrawerEvent());
    }

    private void selectorChanged(@NonNull final LightSelector.SelectorType type,
                                   @Nullable final String id) {
        eventBus.postMessage(new LightControl.LightSelectorChangedEvent(new LightSelector(type, id)));
    }

    public void checkDrawerItem(int lightLocationPosition) {
        lightLocationListView.expandGroup(0);

        int adjustedPos = getAdjustedPositionFromPackedPosition(
                ExpandableListView.getPackedPositionForChild(
                        locationGroupPosition,
                        lightLocationPosition));

        lightLocationListView.setSelectedChild(locationGroupPosition, lightLocationPosition, true);
        lightLocationListView.setItemChecked(adjustedPos, true);
    }

    private int getAdjustedPositionFromPackedPosition(final long packedPos) {
        int flatPost = lightLocationListView.getFlatListPosition(packedPos);
        return flatPost - lightLocationListView.getFirstVisiblePosition();
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightNetworkClickListener lightNetworkClickListener);
    }

    private class OnLocationsClickListener implements ExpandableListView.OnGroupExpandListener {

        @Override
        public void onGroupExpand(final int groupPosition) {
            if(checkedLightLocation != Constants.INVALID) {
                lightLocationListView.setItemChecked(checkedLightLocation, true);
            }
        }
    }

    private class OnLocationClickListener implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(@NonNull final ExpandableListView listView,
                                    @NonNull final View v,
                                    final int groupPosition,
                                    final int childPosition,
                                    final long id) {
            String locationId = ((LightLocationAdapter)listView.getExpandableListAdapter()).getChild(groupPosition, childPosition);

            presenter.setPosition(childPosition);
            presenter.locationChanged(locationId);

            listView.expandGroup(groupPosition);
            checkDrawerItem(childPosition);
            listView.setSelectedChild(groupPosition, childPosition, true);

            long packedPos = ExpandableListView.getPackedPositionForChild(groupPosition, childPosition);
            int flatPos = listView.getFlatListPosition(packedPos);
            int adjustedPos = flatPos - listView.getFirstVisiblePosition();

            listView.setItemChecked(adjustedPos, true);

            checkedLightLocation = adjustedPos;

            closeDrawer();

            return true;
        }
    }
}
