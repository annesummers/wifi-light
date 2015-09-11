package com.giganticsheep.wifilight.ui.locations;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.api.model.Location;

import hugo.weaving.DebugLog;

/**
 * Created by anne on 13/07/15.
 *
 */
public class LightLocationAdapter extends LightNetworkAdapterBase {

    @DebugLog
    public LightLocationAdapter(@NonNull final Injector injector) {
        super(injector);
    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        return lightNetwork.lightLocationCount();
    }

    @Nullable
    @Override
    public String getChild(final int groupPosition,
                           final int childPosition) {
        return lightNetwork.getLightLocation(childPosition).getId();
    }

    @Override
    public long getChildId(final int groupPosition,
                           final int childPosition) {
        return childPosition;
    }

    //@DebugLog
    @NonNull
    @Override
    public View getChildView(final int groupPosition,
                             final int childPosition,
                             boolean isLastChild,
                            @Nullable View convertView,
                            final ViewGroup parent) {
        LocationViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.drawer_location_item, null);
            holder = new LocationViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LocationViewHolder) convertView.getTag();
        }

        holder.setViewData(lightNetwork.getLightLocation(groupPosition));

        return convertView;
    }

    @Override
    public String getGroup(final int groupPosition) {
        return "Locations";
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    //@DebugLog
    @NonNull
    @Override
    public View getGroupView(final int groupPosition,
                             boolean isExpanded,
                             @Nullable View convertView,
                             final ViewGroup parent) {
        LocationsViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.drawer_locations_item, null);
            holder = new LocationsViewHolder(convertView);
            convertView.setTag(holder);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(final int groupPosition,
                                     final int childPosition) {
        return true;
    }

    @Override
    public void setLightNetwork(@NonNull final LightNetwork lightNetwork) {
        super.setLightNetwork(lightNetwork);
    }

    class LocationViewHolder {
        private Location viewData;

        final TextView locationNameTextView;

        public LocationViewHolder(View view) {
            locationNameTextView = (TextView) view.findViewById(R.id.location_name_textview);
        }

        public void setViewData(@NonNull final Location viewData) {
            this.viewData = viewData;

            locationNameTextView.setText(viewData.getName());
        }
    }

    class LocationsViewHolder {
        final TextView locationsTextView;

        public LocationsViewHolder(View view) {
            locationsTextView = (TextView) view.findViewById(R.id.locations_textview);
        }
    }
}
