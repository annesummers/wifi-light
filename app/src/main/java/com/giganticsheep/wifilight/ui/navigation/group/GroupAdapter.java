package com.giganticsheep.wifilight.ui.navigation.group;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;

import javax.inject.Inject;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.LightViewHolder> {

    private Group group;

    @Inject Context context;
    @Inject EventBus eventBus;

    private final RelativeLayout placeholderLightLayout;
    private final ViewGroup placeholderViewGroup;

    public GroupAdapter(@NonNull final Injector injector) {
        injector.inject(this);

        this.placeholderViewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_list_light, null);
        this.placeholderLightLayout = (RelativeLayout) placeholderViewGroup.findViewById(R.id.light_layout);
    }

    @Override
    public LightViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup,
                                                      final int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list_light, null);

        return new LightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LightViewHolder holder,
                                 final int position) {
        if(group != null) {
            holder.lightId = group.getLight(position).getId();
            holder.lightNameTextView.setText(group.getLight(position).getLabel());
        }
    }

    @Override
    public int getItemCount() {
        if(group == null) {
            return 0;
        }

        return group.lightCount();
    }

    void setGroup(@NonNull final Group group) {
        this.group = group;
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
        void inject(final GroupAdapter adapter);
    }

    class LightViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final ViewGroup viewGroup;
        private final RelativeLayout lightLayout;

        private final TextView lightNameTextView;
        private final ImageView lightImageView;
        //private View lightView;

        private String lightId;

        public LightViewHolder(@NonNull final View view) {
            super(view);

            this.viewGroup = (ViewGroup) view;

            this.lightLayout = (RelativeLayout) view.findViewById(R.id.light_layout);
            this.lightNameTextView = (TextView) view.findViewById(R.id.light_name_textview);
            this.lightImageView = (ImageView) view.findViewById(R.id.light_small_imageview);

            lightImageView.setVisibility(View.VISIBLE);

            lightLayout.setOnClickListener(this);
            lightNameTextView.setOnClickListener(this);
            lightImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            placeholderLightLayout.setPadding(
                    lightLayout.getWidth()/2 - lightLayout.getPaddingLeft(),
                    lightLayout.getHeight()/2 - lightLayout.getPaddingTop(),
                    lightLayout.getWidth()/2 - lightLayout.getPaddingRight(),
                    lightLayout.getHeight()/2 - lightLayout.getPaddingBottom() - 30);

            // get XY coordingates for the group layout
            int[] location = new int[2];
            lightLayout.getLocationOnScreen(location);

            int width = lightLayout.getWidth();
            int height = lightLayout.getHeight();

            placeholderViewGroup.removeView(placeholderLightLayout);
            viewGroup.removeView(lightLayout);
            viewGroup.addView(placeholderLightLayout);

            Rect groupRect = new Rect(location[0],
                    location[1],
                    location[0] + width,
                    location[1] + height);

            eventBus.postUIMessage(
                    new NavigationActivity.ShowLightControlActivityEvent(groupRect,
                            lightLayout,
                            lightId));
        }
    }
}
