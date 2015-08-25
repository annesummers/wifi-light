package com.giganticsheep.wifilight.ui.control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.util.Constants;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import javax.inject.Inject;

import butterknife.InjectView;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class DrawerFragment extends FragmentBase<LightNetworkView, LightNetworkPresenter>
                            implements LightNetworkView  {

    @Inject EventBus eventBus;

    @InjectView(R.id.error_layout) FrameLayout errorLayout;
    @InjectView(R.id.loading_layout) FrameLayout loadingLayout;

    @InjectView(R.id.drawer_textview) TextView drawerTextView;
    @InjectView(R.id.location_list) ExpandableListView locationsListView;
    @InjectView(R.id.drawer_layout) RelativeLayout drawerLayout;

    private LightLocationAdapter adapter;

    @DebugLog
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if(savedInstanceState != null) {
            Timber.d("here");
        }
    }

    @DebugLog
    @Override
    protected void initialiseViews(View view) {
        adapter = new LightLocationAdapter(getLightControlActivity().getComponent());
        locationsListView.setAdapter(adapter);

        locationsListView.setOnChildClickListener(new OnGroupClickListener(eventBus, getPresenter(), getLightControlActivity()));
        locationsListView.setOnGroupClickListener(new OnLocationClickListener(eventBus, getPresenter(), getLightControlActivity()));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_drawer;
    }

    @DebugLog
    @Override
    public void onDestroy() {
        super.onDestroy();

        getPresenter().onDestroy();
    }

    @NonNull
    private LightControlActivity getLightControlActivity() {
        return (LightControlActivity) getActivity();
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    private void clickDrawerItem(final int lightLocationPosition,
                                 final int lightGroupPosition,
                                 final int lightPosition) {
        if(lightGroupPosition == Constants.INVALID) {
            // selected the location at lightLocationPosition
        } else if (lightPosition == Constants.INVALID) {
            locationsListView.expandGroup(lightLocationPosition);

            long packedPos = ExpandableListView.getPackedPositionForChild(lightLocationPosition, lightGroupPosition);
            int flatPos = locationsListView.getFlatListPosition(packedPos);
            int adjustedPos = flatPos - locationsListView.getFirstVisiblePosition();

            View childToClick = locationsListView.getChildAt(adjustedPos);
            long id = adapter.getChildId(lightLocationPosition, lightGroupPosition);

            locationsListView.performItemClick(childToClick, adjustedPos, id);
        } else {
            locationsListView.expandGroup(lightLocationPosition);

            adapter.clickDrawerItem(lightGroupPosition, lightPosition);
        }
    }

    // MVP

    @Override
    public ViewState createViewState() {
        ViewState viewState = getLightControlActivity().getDrawerViewState();

        if (viewState == null) {
            return new LightNetworkViewState();
        } else {
            return viewState;
        }
    }

    @Override
    public void onNewViewStateInstance() {
        getViewState().apply(this, false);
    }

    @NonNull
     @Override
     public final LightNetworkViewState getViewState() {
        return (LightNetworkViewState) super.getViewState();
    }

    @Override
    public LightNetworkPresenter createPresenter() {
        return new LightNetworkPresenter(getLightControlActivity().getComponent());
    }

    @DebugLog
    @Override
    public void showLoading() {
        getViewState().setShowLoading();

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
        drawerLayout.setVisibility(View.GONE);
    }

    @DebugLog
    @Override
    public void showLightNetwork(@NonNull final LightNetwork lightNetwork,
                                 final int locationPosition,
                                 final int groupPosition,
                                 final int lightPosition) {

        getViewState().setShowLightNetwork(lightNetwork, locationPosition, groupPosition, lightPosition);

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        drawerLayout.setVisibility(View.VISIBLE);

        adapter.setLightNetwork(lightNetwork);
        adapter.notifyDataSetChanged();

        locationsListView.expandGroup(0);

        clickDrawerItem(locationPosition, groupPosition, lightPosition);
    }

    @DebugLog
    @Override
    public void showError() {
        getViewState().setShowError();

        errorLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        drawerLayout.setVisibility(View.GONE);
    }

    @DebugLog
    @Override
    public void showError(Throwable throwable) {
        getViewState().setShowError(throwable);

        errorLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        drawerLayout.setVisibility(View.GONE);
    }

    // Dagger

    @Override
    protected final void injectDependencies() {
        getLightControlActivity().getComponent().inject(this);
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(DrawerFragment drawerFragment);
    }
}
