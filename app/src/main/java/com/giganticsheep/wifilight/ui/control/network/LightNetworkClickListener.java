package com.giganticsheep.wifilight.ui.control.network;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.giganticsheep.wifilight.util.Constants;
import com.giganticsheep.wifilight.util.ErrorSubscriber;

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

    private int checkedLight = Constants.INVALID;
    private int checkedLightGroup = Constants.INVALID;
    private int checkedLightLocation = Constants.INVALID;

    private int locationPosition;

    private final ExpandableListView lightLocationListView;

    public LightNetworkClickListener(@NonNull final Injector injector,
                                     @NonNull final ExpandableListView lightLocationListView) {
        injector.inject(this);

        this.lightLocationListView = lightLocationListView;

        //OnLightLocationClickListener lightLocationClickListener = new OnLightLocationClickListener();
        OnLightGroupClickListener groupClickListener = new OnLightGroupClickListener();

        lightLocationListView.setOnGroupClickListener(groupClickListener);
        lightLocationListView.setOnGroupExpandListener(groupClickListener);

        lightLocationListView.setOnChildClickListener(new OnLightClickListener(locationPosition));
    }

    ExpandableListView.OnGroupClickListener createLightGroupClickListener() {
        return new OnLightGroupClickListener();
    }

    ExpandableListView.OnChildClickListener createLightClickListener(final int lightLocationPosition) {
        return new OnLightClickListener(lightLocationPosition);
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
        eventBus.postMessage(new LightControlActivity.CloseDrawerEvent())
                .subscribe(new ErrorSubscriber<>());
    }

    private void selectorChanged(@NonNull final LightSelector.SelectorType type,
                                   @Nullable final String id) {
        eventBus.postMessage(new LightControl.LightSelectorChangedEvent(new LightSelector(type, id)))
                .subscribe(new ErrorSubscriber<>());
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightNetworkClickListener lightNetworkClickListener);
    }

    private class OnLightLocationClickListener implements ExpandableListView.OnGroupClickListener,
            ExpandableListView.OnGroupExpandListener {

        @Override
        public boolean onGroupClick(ExpandableListView listView,
                                    View v,
                                    int groupPosition,
                                    long id) {
            String locationId = ((LightLocationAdapter) listView.getExpandableListAdapter()).getGroup(groupPosition);

            selectorChanged(LightSelector.SelectorType.LOCATION, locationId);

            presenter.setPosition(groupPosition, Constants.INVALID, Constants.INVALID);
            presenter.fetchLocation(locationId);

            checkedLightLocation = selectGroupAndReturnFlatPosition(listView, groupPosition);
            checkedLight = Constants.INVALID;
            checkedLightGroup = Constants.INVALID;

            return false;
        }

        @Override
        public void onGroupExpand(final int groupPosition) {
            if(checkedLight != Constants.INVALID) {
                lightLocationListView.setItemChecked(checkedLight, true);
            }
        }
    }

    private class OnLightGroupClickListener implements ExpandableListView.OnGroupClickListener,
                                                ExpandableListView.OnChildClickListener,
                                                ExpandableListView.OnGroupExpandListener {

        @Override
        public boolean onChildClick(@NonNull final ExpandableListView listView,
                                    @NonNull final View v,
                                    final int groupPosition,
                                    final int childPosition,
                                    final long id) {
            locationPosition = groupPosition;

            listView.expandGroup(groupPosition);
            listView.setSelectedChild(groupPosition, childPosition, true);

            return true;
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

            checkedLightLocation = Constants.INVALID;
            checkedLight = Constants.INVALID;
            checkedLightGroup = selectGroupAndReturnFlatPosition(listView, groupPosition);

            return false;
        }

        @Override
        public void onGroupExpand(final int groupPosition) {
            if(checkedLightGroup != Constants.INVALID) {
                lightLocationListView.setItemChecked(checkedLightGroup, true);
            }
        }
    }

    private class OnLightClickListener implements ExpandableListView.OnChildClickListener {

        private final int lightLocationPosition;

        public OnLightClickListener(final int lightLocationPosition) {
            this.lightLocationPosition = lightLocationPosition;
        }

        @Override
        public boolean onChildClick(@NonNull final ExpandableListView listView,
                                    @NonNull final View v,
                                    final int groupPosition,
                                    final int childPosition,
                                    final long id) {
            String lightId = ((LightGroupAdapter) listView.getExpandableListAdapter()).getChild(groupPosition, childPosition);

            selectorChanged(LightSelector.SelectorType.LIGHT, lightId);

            presenter.setPosition(locationPosition, groupPosition, childPosition);
            presenter.fetchLight(lightId);

            listView.expandGroup(groupPosition);
            listView.setSelectedChild(groupPosition, childPosition, true);

            long packedPos = ExpandableListView.getPackedPositionForChild(groupPosition, childPosition);
            int flatPos = listView.getFlatListPosition(packedPos);
            int adjustedPos = flatPos - listView.getFirstVisiblePosition();

            checkedLightLocation = Constants.INVALID;
            checkedLightGroup = Constants.INVALID;
            checkedLight = adjustedPos;

            listView.setItemChecked(adjustedPos, true);

            closeDrawer();

            return true;
        }
    }
}
