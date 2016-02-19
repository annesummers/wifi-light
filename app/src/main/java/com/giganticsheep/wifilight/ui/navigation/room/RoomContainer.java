package com.giganticsheep.wifilight.ui.navigation.room;

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
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.giganticsheep.nofragmentbase.ui.base.FlowActivity;
import com.giganticsheep.nofragmentbase.ui.base.GridRecyclerViewRelativeLayoutContainer;
import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.navigation.LightContainerAdapter;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by anne on 24/11/15.
 */
public class RoomContainer extends GridRecyclerViewRelativeLayoutContainer<RoomScreen>
                            implements RoomScreen.ViewAction {

    private Group group;

    @InjectView(R.id.powerToggle)
    ToggleButton powerToggle;

    @InjectView(R.id.nameTextView)
    TextView nameTextView;

    @InjectView(R.id.lightsRecyclerView)
    RecyclerView lightsRecyclerView;

    protected boolean firstSetPower = false;

    public RoomContainer(Context context) {
        super(context);
    }

    public RoomContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected Screen.ViewActionBase getViewAction() {
        return this;
    }

    @Override
    protected void onCreated() {
        getScreenGroup().postControlEvent(new FlowActivity.ViewShownEvent());
    }

    @Override
    protected void setupViews() {
        super.setupViews();

        lightsRecyclerView.setHasFixedSize(true);
        lightsRecyclerView.setAdapter(new GroupAdapter());
        lightsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return lightsRecyclerView;
    }

    @Override
    public void showGroup(Group group) {
        if(group != this.group) {
            this.group = group;
        }
    }

    @Override
    public void showData() {
        super.showData();

        firstSetPower = true;
        lightsRecyclerView.getAdapter().notifyDataSetChanged();
        nameTextView.setText(group.getName());

        // TODO set the power toggle on if any light is on, off otherwise
    }

    @OnCheckedChanged(R.id.powerToggle)
    public final synchronized void onPowerToggle(@NonNull final CompoundButton compoundButton,
                                                 final boolean isChecked) {
        Timber.d("onPowerToggle() views enabled and firstSetPower is %s", firstSetPower ? "true" : "false");
        if(!firstSetPower) {
            getScreen().setPower(isChecked);
        }

        firstSetPower = false;
    }

    @OnClick(R.id.allLightsButton)
    public final void onAllLightsClick() {
        getScreen().onAllLightsClick();
    }

    private class GroupAdapter extends LightContainerAdapter<GroupAdapter.ClickableLightViewHolder> {

        public GroupAdapter() {
            this.placeholderViewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.list_light_item, null);
            this.placeholderLightContainerLayout = (RelativeLayout) placeholderViewGroup.findViewById(R.id.controlLayout);
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

                getScreenGroup().postControlEvent(
                        new NavigationActivity.ShowLightControlActivityEvent(
                                layoutRect,
                                lightContainerLayout,
                                lightId));
            }
        }
    }
}
