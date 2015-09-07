package com.giganticsheep.wifilight.ui.navigation.location;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.GroupViewHolder> {

    private Location location = null;
    private boolean locationChanged = true;

    @Inject Context context;
    @Inject EventBus eventBus;

    private final RelativeLayout placeholderGroupLayout;
    private final ViewGroup placeholderViewGroup;

    public LocationAdapter(@NonNull final Injector injector) {
        injector.inject(this);

        this.placeholderViewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.list_group_item, null);
        this.placeholderGroupLayout = (RelativeLayout) placeholderViewGroup.findViewById(R.id.group_layout);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup,
                                                      final int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_group_item, null);

        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupViewHolder holder,
                                 final int position) {
        if(location != null) {
            Group group = location.getGroup(position);
            holder.groupNameTextView.setText(group.getName());
            holder.groupId = group.getId();

            for (int i = 0; i < holder.lightViewHolders.size(); i++) {
                holder.lightViewHolders.get(i).setVisibility(View.GONE);
            }

            holder.lightViewHolders.clear();

            boolean firstSet = false;

            for (int i = 0; i < location.getGroup(position).lightCount(); i++) {
                View lightView = LayoutInflater.from(context).inflate(R.layout.layout_list_light, null);
                LightViewHolder lightViewHolder = new LightViewHolder(lightView);
                holder.lightViewHolders.add(lightViewHolder);

                if(!firstSet) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    //layoutParams.setMarginEnd(5);
                    layoutParams.setMargins(0, 0, 5, 0);
                    lightViewHolder.lightLayout.setLayoutParams(layoutParams);
                }

                lightViewHolder.lightNameTextView.setText(location.getGroup(position).getLight(i).getLabel());

                holder.lightLayout.addView(lightViewHolder.lightLayout);
                firstSet = true;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(location == null) {
            return 0;
        }

        return location.groupCount();
    }

    synchronized void setLocation(@NonNull final Location location) {
        locationChanged = true;
        this.location = location;
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling the LocationAdapter to inject itself
     * into the Component.
     */
    public interface Injector {
        /**
         * Injects the LocationAdapter class into the Component implementing this interface.
         *
         * @param adapter the class to inject.
         */
        void inject(final LocationAdapter adapter);
    }

    class GroupViewHolder extends RecyclerView.ViewHolder
                                implements View.OnClickListener {
        private final ViewGroup viewGroup;
        private final RelativeLayout groupLayout;
        private final LinearLayout lightLayout;

        private final TextView groupNameTextView;

        private String groupId;

        private List<LightViewHolder> lightViewHolders = new ArrayList();

        public GroupViewHolder(final View view) {
            super(view);

            this.viewGroup = (ViewGroup) view;

            this.groupLayout = (RelativeLayout) view.findViewById(R.id.group_layout);

            this.lightLayout = (LinearLayout) view.findViewById(R.id.light_group_layout);
            this.groupNameTextView = (TextView) view.findViewById(R.id.group_name_textview);

            lightLayout.setOnClickListener(this);
            groupNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View clickedOnView) {
            placeholderGroupLayout.setPadding(
                    groupLayout.getWidth()/2 - groupLayout.getPaddingLeft(),
                    groupLayout.getHeight()/2 - groupLayout.getPaddingTop(),
                    groupLayout.getWidth()/2 - groupLayout.getPaddingRight(),
                    groupLayout.getHeight()/2 - groupLayout.getPaddingBottom() - 30);

            // get XY coordingates for the group layout
            int[] location = new int[2];
            groupLayout.getLocationOnScreen(location);

            int width = groupLayout.getWidth();
            int height = groupLayout.getHeight();

            placeholderViewGroup.removeView(placeholderGroupLayout);
            viewGroup.removeView(groupLayout);
            viewGroup.addView(placeholderGroupLayout);

            // TODO remove all but first two then zoom

            for(LightViewHolder holder : lightViewHolders) {
                lightLayout.removeView(holder.lightLayout);
            }

            Rect groupRect = new Rect(location[0],
                    location[1],
                    location[0] + width,
                    location[1] + height);

            eventBus.postUIMessage(
                    new NavigationActivity.ShowGroupFragmentEvent(groupRect,
                            groupLayout,
                            groupId));
        }
    }

    static class LightViewHolder {
        private final View lightView;
        private final TextView lightNameTextView;
        private final ImageView lightImageView;
        private final RelativeLayout lightLayout;

        public LightViewHolder(@NonNull final View view) {
            lightView = view;
            lightLayout = (RelativeLayout) view.findViewById(R.id.light_layout);
            lightNameTextView = (TextView) view.findViewById(R.id.light_name_textview);
            this.lightImageView = (ImageView) view.findViewById(R.id.light_small_imageview);

            lightImageView.setVisibility(View.VISIBLE);
        }

        public void setVisibility(int visibility) {
            lightView.setVisibility(visibility);
        }
    }
}
