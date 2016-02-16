package com.giganticsheep.wifilight.ui.locations;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.util.Constants;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/08/15. <p>
 * (*_*)
 */
public class LightGroupAdapter extends LightNetworkAdapterBase {

    private int locationPosition = Constants.INVALID;

    public LightGroupAdapter(@NonNull final Injector injector,
                             final int locationPosition) {
        super(injector);

        this.locationPosition = locationPosition;
    }

    public LightGroupAdapter(@NonNull final Injector injector) {
        super(injector);
    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        int lightCount = lightNetwork.lightCount(locationPosition, groupPosition);
        return lightCount;
    }

    @Nullable
    @Override
    public String getChild(final int groupPosition,
                           final int childPosition) {
        return lightNetwork.getLight(locationPosition, groupPosition, childPosition).getId();
    }

    @Override
    public long getChildId(final int groupPosition,
                           final int lightPosition) {
        return lightPosition;
    }

    @DebugLog
    @NonNull
    @Override
    public View getChildView(final int groupPosition,
                             final int childPosition,
                             boolean isLastChild,
                             @Nullable View convertView,
                             final ViewGroup parent) {
        LightViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.drawer_list_item, null);
            holder = new LightViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LightViewHolder) convertView.getTag();
        }

        holder.setViewData(lightNetwork.getLight(locationPosition, groupPosition, childPosition));

        return convertView;
    }

    @Override
    public String getGroup(final int groupPosition) {
        return lightNetwork.getLightGroup(locationPosition, groupPosition).getId();
    }

    @Override
    public int getGroupCount() {
        return lightNetwork.lightGroupCount(locationPosition);
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
        GroupViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.drawer_group_item, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        holder.setViewData(lightNetwork.getLightGroup(locationPosition, groupPosition));

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

    void setLocationPosition(final int locationPosition) {
        this.locationPosition = locationPosition;
    }

    /**
     * Holds the views for an entry in the list.  Once an instance of this class has been created
     * for an entry and the views inflated it is stored in the tag for the View for that list groupPosition.
     * This enables the views to be recycled quickly when the elements change or the list is scrolled
     * off the screen. <p>
     *
     * The data for the entry is set via setViewData(ViewData viewData).
     */
    public class LightViewHolder {
        private Light viewData;

        public final TextView lightNameTextView;
        final ImageView lightStatusImageView;

        private LightViewHolder(@NonNull View view) {
            lightNameTextView = (TextView) view.findViewById(R.id.light_name);
            lightStatusImageView = (ImageView) view.findViewById(R.id.light_status);
        }

        private void setViewData(@NonNull final Light viewData) {
            this.viewData = viewData;

            lightNameTextView.setText(viewData.getLabel());
            lightStatusImageView.setImageResource(viewData.isConnected() ?
                    R.drawable.ic_status_tick :
                    R.drawable.ic_status_warning);
        }
    }

    public class GroupViewHolder {
        private Group viewData;

        public final TextView groupNameTextView;

        private GroupViewHolder(View view) {
            groupNameTextView = (TextView) view.findViewById(R.id.group_name);
        }

        private void setViewData(@NonNull final Group viewData) {
            this.viewData = viewData;

            groupNameTextView.setText(viewData.getName());
        }
    }
}
