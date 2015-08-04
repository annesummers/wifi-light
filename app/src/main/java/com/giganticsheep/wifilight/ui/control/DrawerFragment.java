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
    @InjectView(R.id.left_drawer) ExpandableListView drawerListView;
    @InjectView(R.id.drawer_layout) RelativeLayout drawerLayout;

    private DrawerAdapter adapter;

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
        adapter = new DrawerAdapter(getLightControlActivity().getComponent());
        drawerListView.setAdapter(adapter);

        drawerListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            getPresenter().setPosition(groupPosition, childPosition);
            getPresenter().fetchLight(adapter.getChild(groupPosition, childPosition));

            drawerListView.expandGroup(groupPosition);
            drawerListView.setSelectedChild(groupPosition, childPosition, true);

            getLightControlActivity().closeDrawer();

            return true;
        });
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

    private void clickDrawerItem(final int groupPosition, final int childPosition) {
        long packedPos = ExpandableListView.getPackedPositionForChild(groupPosition, childPosition);
        int flatPos = drawerListView.getFlatListPosition(packedPos);
        int adjustedPos = flatPos - drawerListView.getFirstVisiblePosition();

        View childToClick = drawerListView.getChildAt(adjustedPos);
        long id = adapter.getChildId(groupPosition, childPosition);

        drawerListView.performItemClick(childToClick, adjustedPos, id);
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
                                 final int groupPosition,
                                 final int childPosition) {
        getViewState().setShowLightNetwork(lightNetwork, groupPosition, childPosition);

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        drawerLayout.setVisibility(View.VISIBLE);

        adapter.setLightNetwork(lightNetwork);//, groupPosition, childPosition);
        adapter.notifyDataSetChanged();

        drawerTextView.setText(lightNetwork.getLocation().getName());

        drawerListView.expandGroup(0);

        clickDrawerItem(childPosition, groupPosition);
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
