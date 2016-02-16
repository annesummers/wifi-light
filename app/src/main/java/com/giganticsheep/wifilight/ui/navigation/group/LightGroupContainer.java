package com.giganticsheep.wifilight.ui.navigation.group;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.giganticsheep.nofragmentbase.ui.base.FlowActivity;
import com.giganticsheep.nofragmentbase.ui.base.GridRecyclerViewRelativeLayoutContainer;
import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.navigation.LightContainerAdapter;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by anne on 24/11/15.
 */
public class LightGroupContainer extends GridRecyclerViewRelativeLayoutContainer
        implements LightGroupScreen.ViewAction {

    private Group group;

    @InjectView(R.id.lights_recycler_view)
    RecyclerView recyclerView;

    public LightGroupContainer(Context context) {
        super(context);
    }

    public LightGroupContainer(Context context, AttributeSet attrs) {
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
        recyclerView.setAdapter(new GroupAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void showGroup(Group group) {
        this.group = group;
    }

    public class GroupAdapter extends LightContainerAdapter<GroupAdapter.ClickableLightViewHolder> {

        public GroupAdapter() {//@NonNull final Injector injector) {
            // injector.inject(this);

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
            if (group != null) {
                holder.lightId = group.getLight(position).getId();
                holder.lightNameTextView.setText(group.getLight(position).getLabel());
            }
        }

        @Override
        public int getItemCount() {
            if (group == null) {
                return 0;
            }

            return group.lightCount();
        }
        /**
         * The Injector interface is implemented by a Component that provides the injected
         * class members, enabling the LocationAdapter to inject itself
         * into the Component.
         */
        //public interface Injector {

        /**
         * Injects the LocationAdapter class into the Component implementing this interface.
         *
         * @param adapter the class to inject.
         */
        //     void inject(final GroupAdapter adapter);
        //  }

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
                        new NavigationActivity.ShowLightControlActivityEvent(
                                layoutRect,
                                lightContainerLayout,
                                lightId));
            }
        }
    }
}
