package com.giganticsheep.wifilight.ui.navigation.location;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;
import com.giganticsheep.wifilight.ui.navigation.group.LightContainerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class LocationAdapter extends LightContainerAdapter<LocationAdapter.GroupViewHolder> {

    private Location location = null;
    private boolean locationChanged = true;

    public LocationAdapter(@NonNull final Injector injector) {
        injector.inject(this);

        this.placeholderViewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.list_group_item, null);
        this.placeholderLightContainerLayout = (RelativeLayout) placeholderViewGroup.findViewById(R.id.group_layout);
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
                    lightViewHolder.lightContainerLayout.setLayoutParams(layoutParams);
                }

                lightViewHolder.lightNameTextView.setText(location.getGroup(position).getLight(i).getLabel());

                holder.lightLayout.addView(lightViewHolder.lightContainerLayout);
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

    class GroupViewHolder extends LightContainerAdapter.LightContainerViewHolder
                                implements View.OnClickListener {

        private final TextView groupNameTextView;

        private String groupId;

        private final LinearLayout lightLayout;
        private List<LightViewHolder> lightViewHolders = new ArrayList();

        public GroupViewHolder(@NonNull final View view) {
            super(view);

            this.lightContainerLayout = (RelativeLayout) view.findViewById(R.id.group_layout);

            this.groupNameTextView = (TextView) view.findViewById(R.id.group_name_textview);
            this.lightLayout = (LinearLayout) view.findViewById(R.id.light_group_layout);

            lightLayout.setOnClickListener(this);
            groupNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(@NonNull final View clickedOnView) {
            Rect layoutRect = calculateRectOnScreen();
            detachLayoutAndAttachPaddedPlaceholder();
            // TODO remove all but first two then zoom

            for(LightViewHolder holder : lightViewHolders) {
                lightLayout.removeView(holder.lightContainerLayout);
            }

            eventBus.postUIMessage(
                    new NavigationActivity.ShowGroupFragmentEvent(
                            layoutRect,
                            lightContainerLayout,
                            groupId));
        }
    }

   /* static class LightViewHolder extends LightViewHolder {
        private final View lightView;
        private final TextView lightNameTextView;
        private final ImageView lightImageView;
        private final RelativeLayout lightLayout;

        public LightViewHolder(@NonNull final View view) {
            super(view);

            lightView = view;
            lightLayout = (RelativeLayout) view.findViewById(R.id.light_layout);
            lightNameTextView = (TextView) view.findViewById(R.id.light_name_textview);
            this.lightImageView = (ImageView) view.findViewById(R.id.light_small_imageview);

            lightImageView.setVisibility(View.VISIBLE);
        }

        public void setVisibility(int visibility) {
            lightView.setVisibility(visibility);
        }
    }*/
}
