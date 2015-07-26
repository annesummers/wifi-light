package com.giganticsheep.wifilight.ui.control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnItemClick;
import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class DrawerFragment extends FragmentBase<LightNetworkView, LightNetworkPresenter>
                            implements LightNetworkView  {

    @Inject EventBus eventBus;

    @InjectView(R.id.left_drawer) ListView drawerListView;

    DrawerAdapter adapter;

    @DebugLog
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        adapter = new DrawerAdapter(getLightControlActivity().getComponent());

        //if(drawerSelectedPosition != Constants.INVALID) {
        //    drawerListView.setItemChecked(drawerSelectedPosition, true);
        //}
    }

    @DebugLog
    @Override
    protected void initialiseViews(View view) {
        drawerListView.setAdapter(adapter);
    }

    void setupDrawer(final FrameLayout drawerContainer, final DrawerLayout drawerLayout) {
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.

    }

    @DebugLog
    @OnItemClick(R.id.left_drawer)
    public void onItemClick(final AdapterView<?> parent,
                            final View view,
                            final int position,
                            final long id) {

        getPresenter().setPosition(position);
        getPresenter().fetchLight(adapter.getItem(position));

        drawerListView.setItemChecked(position, true);

        getLightControlActivity().closeDrawer();
    }

    public void clickDrawerItem(final int position) {
        drawerListView.performItemClick(null, position, 0L);
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

    // MVP

    @Override
    public ViewState createViewState() {
        return new LightNetworkViewState();
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
    }

    @DebugLog
    @Override
    public void showLightNetwork(@NonNull final LightNetwork lightNetwork,
                                 final int position) {
        getViewState().setShowLightNetwork(lightNetwork, position);

        adapter.setLightNetwork(lightNetwork, position);
        adapter.notifyDataSetChanged();

        clickDrawerItem(position);
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

    @DebugLog
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
