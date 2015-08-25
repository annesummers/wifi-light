package com.giganticsheep.wifilight.ui.control;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.Location;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/08/15. <p>
 * (*_*)
 */
public abstract class LightNetworkAdapterBase extends BaseExpandableListAdapter {

    // TODO test adapters
    protected final Injector injector;

    @Inject
    Activity activity;

    protected LightNetwork lightNetwork = new LightNetwork();

    @DebugLog
    public LightNetworkAdapterBase(@NonNull final Injector injector) {
        this.injector = injector;

        injector.inject(this);
    }

    public void setLightNetwork(@NonNull final LightNetwork lightNetwork) {
        this.lightNetwork = lightNetwork;
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling the DrawerAdapter to inject itself
     * into the Component.
     */
    public interface Injector {
        /**
         * Injects the DrawerAdapter class into the Component implementing this interface.
         *
         * @param adapter the class to inject.
         */
        void inject(final LightNetworkAdapterBase adapter);
    }
    /**
     * Holds the views for an entry in the list.  Once an instance of this class has been created
     * for an entry and the views inflated it is stored in the tag for the View for that list groupPosition.
     * This enables the views to be recycled quickly when the elements change or the list is scrolled
     * off the screen. <p>
     *
     * The data for the entry is set via setViewData(ViewData viewData).
     */
    protected class LightViewHolder {
        private Light viewData;

        private final TextView lightNameTextView;
        private final ImageView lightStatusImageView;

        public LightViewHolder(@NonNull View view) {
            lightNameTextView = (TextView) view.findViewById(R.id.light_name);
            lightStatusImageView = (ImageView) view.findViewById(R.id.light_status);
        }

        public void setViewData(@NonNull final Light viewData) {
            this.viewData = viewData;

            lightNameTextView.setText(viewData.getLabel());
            lightStatusImageView.setImageResource(viewData.isConnected() ?
                    R.drawable.ic_status_tick :
                    R.drawable.ic_status_warning);
        }
    }

    protected class GroupListViewHolder {
        private LightGroupAdapter viewData;

        private final ExpandableListView groupListView;

        public GroupListViewHolder(View view) {
            groupListView = (ExpandableListView) view.findViewById(R.id.group_list);
        }

        public void setViewData(@NonNull final LightGroupAdapter viewData) {
            this.viewData = viewData;

            groupListView.setAdapter(viewData);
        }
    }

    protected class GroupViewHolder {
        private Group viewData;

        private final TextView groupNameTextView;

        public GroupViewHolder(View view) {
            groupNameTextView = (TextView) view.findViewById(R.id.group_name);
        }

        public void setViewData(@NonNull final Group viewData) {
            this.viewData = viewData;

            groupNameTextView.setText(viewData.getName());
        }
    }

    protected class LocationViewHolder {
        private Location viewData;

        private final TextView locationNameTextView;

        public LocationViewHolder(View view) {
            locationNameTextView = (TextView) view.findViewById(R.id.location_name);
        }

        public void setViewData(@NonNull final Location viewData) {
            this.viewData = viewData;

            locationNameTextView.setText(viewData.getName());
        }
    }
}
