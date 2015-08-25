package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giganticsheep.wifilight.R;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/08/15. <p>
 * (*_*)
 */
public class LightGroupAdapter extends LightNetworkAdapterBase {

    private final int locationPosition;


    @DebugLog
    public LightGroupAdapter(@NonNull final Injector injector,
                             final int locationPosition) {
        super(injector);

        this.locationPosition = locationPosition;
    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        return lightNetwork.lightCount(locationPosition, groupPosition);
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

    //@DebugLog
    @NonNull
    @Override
    public View getChildView(final int groupPosition,
                             final int childPosition,
                             boolean isLastChild,
                             @Nullable View convertView,
                             final ViewGroup parent) {
        LightViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.drawer_list_item, null);
            holder = new LightViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LightViewHolder) convertView.getTag();
        }

        holder.setViewData(lightNetwork.getLight(locationPosition, groupPosition, childPosition));

        return convertView;
    }

    @Override
    public String getGroup(int groupPosition) {
        return lightNetwork.getLightLocation(groupPosition).getId();
    }

    @Override
    public int getGroupCount() {
        return lightNetwork.lightLocationCount();
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.drawer_group_item, null);
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
}
