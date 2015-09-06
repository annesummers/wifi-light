package com.giganticsheep.wifilight.ui.control.network;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.Map;

import butterknife.InjectView;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class LightNetworkDrawerFragment extends FragmentBase<LightNetworkView, LightNetworkPresenter>
                                         implements LightNetworkView,
                                                HasComponent<LightNetworkDrawerFragmentComponent> {

    private LightNetworkDrawerFragmentComponent component;

    @InjectView(R.id.error_layout) FrameLayout errorLayout;
    @InjectView(R.id.loading_layout) FrameLayout loadingLayout;

    @InjectView(R.id.drawer_textview) TextView drawerTextView;
    @InjectView(R.id.location_list) ExpandableListView locationsListView;
    @InjectView(R.id.drawer_layout) RelativeLayout drawerLayout;

    private LightLocationAdapter adapter;

    private final Map<Integer, Boolean> groupCheckedMap = new ArrayMap<>();

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
    protected void initialiseViews(final View view) {
        LightNetworkClickListener lightNetworkClickListener = new LightNetworkClickListener(component,
                                                                                            locationsListView);

        adapter = new LightLocationAdapter(component);
        //adapter = new LightGroupAdapter(component);
        locationsListView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_drawer;
    }

   // @NonNull
   // private LightControlActivity getLightControlActivity() {
    //    return (LightControlActivity) getActivity();
   // }

    @NonNull
     private NavigationActivity getNavigationActivity() {
        return (NavigationActivity) getActivity();
     }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    protected boolean animateOnShow() {
        return false;
    }

    private void clickDrawerItem(final int lightLocationPosition) {//,
                                // final int lightGroupPosition,
                               //  final int lightPosition) {
       /* if(lightGroupPosition == Constants.INVALID) {
            // selected the location at lightLocationPosition
        } else if (lightPosition == Constants.INVALID) {
            int adjustedPos = getAdjustedPositionFromPackedPosition(
                    ExpandableListView.getPackedPositionForGroup(lightGroupPosition));

            View groupToClick = locationsListView.getChildAt(adjustedPos);
            long id = adapter.getGroupId(lightGroupPosition);

            locationsListView.performItemClick(groupToClick, adjustedPos, id);
        } else {*/
            locationsListView.expandGroup(0);

            int adjustedPos = getAdjustedPositionFromPackedPosition(
                    ExpandableListView.getPackedPositionForChild(
                            0,
                            lightLocationPosition));

            View childToClick = locationsListView.getChildAt(adjustedPos);
            long id = adapter.getChildId(0, lightLocationPosition);

            locationsListView.performItemClick(childToClick, adjustedPos, id);
      //  }
    }

    private int getAdjustedPositionFromPackedPosition(final long packedPos) {
        int flatPost = locationsListView.getFlatListPosition(packedPos);
        return flatPost - locationsListView.getFirstVisiblePosition();
    }

    // MVP

    @Override
    public ViewState createViewState() {
        ViewState viewState = getNavigationActivity().getDrawerViewState();

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
        return new LightNetworkPresenter(component);
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
        //adapter.setLocationPosition(locationPosition);
        adapter.notifyDataSetChanged();

        //drawerTextView.setText(lightNetwork.getLightLocation(locationPosition).getName());
        //locationsListView.expandGroup(groupPosition);

        clickDrawerItem(locationPosition);//, groupPosition, lightPosition);
    }

    @DebugLog
    @Override
    public void showError() {
        getViewState().setShowError();
    }

    @DebugLog
    @Override
    public void showError(Throwable throwable) {
        getViewState().setShowError(throwable);
    }

    // Dagger

    @Override
    protected final void injectDependencies() {
        super.injectDependencies();

        WifiLightApplication application = ((WifiLightApplication)getActivity().getApplication());

        component = DaggerLightNetworkDrawerFragmentComponent.builder()
                .wifiLightAppComponent(application.getComponent())
                .lightNetworkDrawerFragmentModule(new LightNetworkDrawerFragmentModule(this))
                .build();

        component.inject(this);
    }

    @Override
    public LightNetworkDrawerFragmentComponent getComponent() {
        return component;
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightNetworkDrawerFragment derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightNetworkDrawerFragment lightNetworkDrawerFragment);
    }
}
