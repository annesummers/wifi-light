package com.giganticsheep.wifilight.ui.navigation.group;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.navigation.NavigationViewStateActivity;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class GroupAdapter extends LightContainerAdapter<GroupAdapter.ClickableLightViewHolder>  {

    private Group group;

    public GroupAdapter(@NonNull final Injector injector) {
        injector.inject(this);

        this.placeholderViewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.list_light_item, null);
        this.placeholderLightContainerLayout = (RelativeLayout) placeholderViewGroup.findViewById(R.id.light_layout);
    }

    @Override
    public ClickableLightViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup,
                                                      final int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_light_item, null);

        return new ClickableLightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClickableLightViewHolder holder,
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

    public class ClickableLightViewHolder extends LightContainerAdapter.LightViewHolder
            implements View.OnClickListener {

        private String lightId;

        public ClickableLightViewHolder(@NonNull final View view) {
            super(view);

            lightContainerLayout.setOnClickListener(this);
            lightNameTextView.setOnClickListener(this);
            lightImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(@NonNull final View v) {
            Rect layoutRect = calculateRectOnScreen();
            detachLayoutAndAttachPaddedPlaceholder();

            eventBus.postUIMessage(
                    new NavigationViewStateActivity.ShowLightControlActivityEvent(
                            layoutRect,
                            lightContainerLayout,
                            lightId));
        }
    }
}
