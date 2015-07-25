package com.giganticsheep.wifilight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.mvp.presenter.LightNetworkPresenter;
import com.giganticsheep.wifilight.mvp.view.LightNetworkView;
import com.giganticsheep.wifilight.mvp.view.LightNetworkViewState;
import com.giganticsheep.wifilight.ui.DrawerAdapter;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import javax.inject.Inject;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class DrawerFragment extends FragmentBase<LightNetworkView, LightNetworkPresenter>
                            implements LightNetworkView  {

    @Inject EventBus eventBus;
    private DrawerAdapter adapter;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        adapter = new DrawerAdapter(getLightControlActivity().getComponent(), this);
    }

    @Override
    protected final void injectDependencies() {
        getLightControlActivity().getComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getPresenter().onDestroy();
    }

    @NonNull
    protected final LightControlActivity getLightControlActivity() {
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

    @Override
    public void showLoading() {
        getViewState().setShowLoading();
    }

    @Override
    public void showLightNetwork(LightNetworkPresenter.LightNetwork lightNetwork) {
        getViewState().setShowLightNetwork();
        getViewState().setLightNetwork(lightNetwork);

        adapter.notifyDataSetChanged();

        getLightControlActivity().clickDrawerItem(getPresenter().getPosition());
    }

    @Override
    public void showError() {
        getViewState().setShowError();
    }

    @Override
    public void showError(Throwable throwable) {
        getViewState().setShowError(throwable);
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
