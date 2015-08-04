package com.giganticsheep.wifilight.ui.control;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

/**
 * Created by anne on 13/07/15.
 *
 */
public class DrawerAdapter extends BaseExpandableListAdapter {

    @Inject Activity activity;

   // private int groupPosition;
   // private int childPosition;
    private LightNetwork lightNetwork = new LightNetwork();

    @DebugLog
    public DrawerAdapter(@NonNull final Injector injector) {
        injector.inject(this);
    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        return lightNetwork.lightCount(groupPosition);
    }

    @Nullable
    @Override
    public String getChild(final int groupPosition,
                           final int childPosition) {
        return lightNetwork.get(groupPosition, childPosition).getId();
    }

    @Override
    public long getChildId(final int groupPosition,
                           final int childPosition) {
        return childPosition;
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
            convertView = LayoutInflater.from(activity).inflate(R.layout.drawer_list_item, null);
            holder = new LightViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LightViewHolder) convertView.getTag();
        }

        holder.setViewData(lightNetwork.get(groupPosition, childPosition));

        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lightNetwork.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return lightNetwork.groupCount();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @DebugLog
    @NonNull
    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {
        GroupViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.drawer_group_item, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        holder.setViewData(lightNetwork.get(groupPosition));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setLightNetwork(@NonNull final LightNetwork lightNetwork) {
        this.lightNetwork = lightNetwork;
       // this.groupPosition = groupPosition;
       // this.childPosition = childPosition;
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
        void inject(final DrawerAdapter adapter);
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

    private class GroupViewHolder {
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
}
