package com.giganticsheep.wifilight.ui.control.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.LightNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hugo.weaving.DebugLog;

/**
 * Created by anne on 13/07/15.
 *
 */
public class LightLocationAdapter extends LightNetworkAdapterBase {

    private final Map<Integer, List<LightGroupAdapter>> lightGroupAdapters = new ArrayMap<>();
    private final OnLightGroupClickListener onLightGroupClickListener;

    @DebugLog
    public LightLocationAdapter(@NonNull final Injector injector,
                                @NonNull final OnLightGroupClickListener onLightGroupClickListener) {
        super(injector);

        this.onLightGroupClickListener = onLightGroupClickListener;
    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        return lightNetwork.lightGroupCount(groupPosition);
    }

    @Nullable
    @Override
    public String getChild(final int groupPosition,
                           final int childPosition) {
        return lightNetwork.getLightGroup(groupPosition, groupPosition).getId();
    }

    @Override
    public long getChildId(final int locationPosition,
                           final int groupPosition) {
        return groupPosition;
    }

    //@DebugLog
    @NonNull
    @Override
    public View getChildView(final int groupPosition,
                             final int childPosition,
                             boolean isLastChild,
                            @Nullable View convertView,
                            final ViewGroup parent) {
        GroupListViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(fragment.getActivity()).inflate(R.layout.drawer_group_list, null);
            holder = new GroupListViewHolder(convertView,
                                            onLightGroupClickListener,
                                            new OnLightClickListener(fragment.getComponent(), groupPosition));
            convertView.setTag(holder);
        } else {
            holder = (GroupListViewHolder) convertView.getTag();
        }

        holder.setViewData(lightGroupAdapters.get(groupPosition).get(childPosition));

        return convertView;
    }

    @Override
    public String getGroup(final int groupPosition) {
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

        for(int i = 0; i < lightNetwork.lightLocationCount(); i++) {
            lightGroupAdapters.put(i, new ArrayList<>(lightNetwork.lightGroupCount(i)));
            for(int j = 0; j < lightNetwork.lightGroupCount(i); j++) {
                LightGroupAdapter lightGroupAdapter = new LightGroupAdapter(injector, i);
                lightGroupAdapter.setLightNetwork(lightNetwork);
                lightGroupAdapters.get(i).add(lightGroupAdapter);
            }
        }
    }

    public void clickDrawerItem(final int lightGroupPosition,
                                final int lightPosition) {

    }
}
