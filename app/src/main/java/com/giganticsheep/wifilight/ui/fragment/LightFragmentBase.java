package com.giganticsheep.wifilight.ui.fragment;

import android.os.Bundle;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.mvp.view.LightViewState;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import icepick.Icicle;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */

@FragmentArgsInherited
public abstract class LightFragmentBase extends FragmentBase<LightView, LightPresenterBase>
                                    implements LightView {
    // TODO inject stuff into the presenter instead

    @Icicle protected Light light;

   // @Inject LightNetwork lightNetwork;
   // @Inject EventBus eventBus;

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
            String id = getMainActivity().getCurrentLight();
            if(id != null) {
                getPresenter().fetchLight(id);
            }
        }
    }

    // MVP

    @Override
    public ViewState createViewState() {
        return new LightViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        getViewState().apply(this, true);
    }

    @Override
    public void showLoading() {
        getViewState().setShowLoading();
    }

    @Override
    public void showLightDetails() {
        getViewState().setShowLightDetails();

        populateViews();
    }

    @Override
    public void showError() {
        getViewState().setShowError();
    }

    @Override
    public void showError(Throwable throwable) {
        showError();
    }

    @Override
    public void lightChanged(Light light) {
        this.light = light;

        showLightDetails();
    }

    public LightViewState getViewState() {
        return (LightViewState) super.getViewState();
    }

    protected LightControlActivity getMainActivity() {
        return (LightControlActivity) getActivity();
    }

    protected abstract void setLightDetails();

    public interface Injector extends LightPresenterBase.Injector {
        void inject(LightFragmentBase lightFragment);
    }
}
