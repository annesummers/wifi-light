package com.giganticsheep.wifilight.ui.fragment;

import android.os.Bundle;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.ui.MainActivity;
import com.giganticsheep.wifilight.ui.base.BaseFragment;
import com.giganticsheep.wifilight.ui.presenter.LightPresenter;
import com.giganticsheep.wifilight.ui.view.LightView;
import com.giganticsheep.wifilight.ui.view.LightViewState;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import javax.inject.Inject;

import icepick.Icicle;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */

@FragmentArgsInherited
public abstract class LightFragment extends BaseFragment<LightView, LightPresenter>
                                    implements LightView {

    @Icicle protected Light light;

    @Inject LightNetwork lightNetwork;
    @Inject EventBus eventBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    protected void injectDependencies() {
        getMainActivity().getComponent().inject(this);
    }

    @Override
    protected void populateViews() {
        if(light != null) {
            setLightDetails();
        } else {
            getPresenter().fetchLight(getMainActivity().getCurrentLight());
        }
    }

    @Override
    public ViewState createViewState() {
        return new LightViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        getViewState().apply(this, false);
    }

    @Override
    public void showLoading() {
        LightViewState vs = (LightViewState) viewState;
        vs.setShowLoading();

        getMainActivity().showLoadingView();
    }

    @Override
    public void showLightDetails() {
        LightViewState vs = (LightViewState) viewState;
        vs.setShowLightDetails();

        getMainActivity().showLightView();
        populateViews();
    }

    @Override
    public void lightChanged(Light light) {
        this.light = light;

        showLightDetails();
    }

    @Override
    public void showError() {
        LightViewState vs = (LightViewState) viewState;
        vs.setShowError();

        getMainActivity().showErrorView();
    }

    @Override
    public void showError(Throwable throwable) {
        showToast(throwable.getMessage());
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    protected abstract void setLightDetails();

    public interface Injector {
        void inject(LightFragment lightFragment);
    }
}
