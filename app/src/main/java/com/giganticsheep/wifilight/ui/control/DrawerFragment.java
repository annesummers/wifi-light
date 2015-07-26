package com.giganticsheep.wifilight.ui.control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

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

    @InjectView(R.id.left_drawer) ExpandableListView drawerListView;

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

            drawerListView.setSelectedChild(groupPosition, childPosition, true);

            getLightControlActivity().closeDrawer();

            return true;
        });
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
                                 final int groupPosition,
                                 final int childPosition) {
        getViewState().setShowLightNetwork(lightNetwork, groupPosition, childPosition);

        adapter.setLightNetwork(lightNetwork, groupPosition, childPosition);
        adapter.notifyDataSetChanged();

        clickDrawerItem(groupPosition);
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
