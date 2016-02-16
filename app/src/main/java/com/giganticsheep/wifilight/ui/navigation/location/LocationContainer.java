package com.giganticsheep.wifilight.ui.navigation.location;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.nofragmentbase.ui.base.FlowActivity;
import com.giganticsheep.nofragmentbase.ui.base.GridRecyclerViewRelativeLayoutContainer;
import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.ui.navigation.LightContainerAdapter;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by anne on 24/11/15.
 */
public class LocationContainer extends GridRecyclerViewRelativeLayoutContainer
                                implements LocationScreen.LocationViewAction {

    private Location location;

    @InjectView(R.id.groups_recycler_view)
    RecyclerView recyclerView;

    public LocationContainer(Context context) {
        super(context);
    }

    public LocationContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected Screen.ViewActionBase getViewAction() {
        return this;
    }

    @Override
    protected void showLoading() {
        EventBus.getDefault().post(new FlowActivity.FullScreenLoadingEvent(true));
    }

    @Override
    protected void onCreated() {

    }

    @Override
    protected void setupViews() {
        super.setupViews();

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new LocationAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void showLocation(Location location) {
        this.location = location;
    }

    public class LocationAdapter extends LightContainerAdapter<LocationAdapter.GroupViewHolder> {
        public LocationAdapter() {//@NonNull final Injector injector) {
          //  injector.inject(this);

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

        /**
         * The Injector interface is implemented by a Component that provides the injected
         * class members, enabling the LocationAdapter to inject itself
         * into the Component.
         */
       // public interface Injector {
            /**
             * Injects the LocationAdapter class into the Component implementing this interface.
             *
             * @param adapter the class to inject.
             */
        //    void inject(final LocationAdapter adapter);
      //  }

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
    }
}
