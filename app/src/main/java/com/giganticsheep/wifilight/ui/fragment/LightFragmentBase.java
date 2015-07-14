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

    @Icicle protected Light light;

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
        logger.debug("populateViews()");
        if(light == null) {
            String currentLightId = getMainActivity().getCurrentLightId();

            if(currentLightId != null) {
                getPresenter().fetchLight(currentLightId);
            }
        }

        if(light != null) {
            showLight();
        } else {
            logger.warn("showLight() light is null!");
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
        logger.debug("showLoading()");

        getViewState().setShowLoading();
    }

    @Override
    public void showConnected() {
        logger.debug("showConnected()");

        getViewState().setShowConnected();

        populateViews();
    }

    @Override
    public void showDisconnected() {
        logger.debug("showDisconnected()");

        getViewState().setShowDisconnected();
    }

    @Override
    public void showError() {
        logger.debug("showError()");

        getViewState().setShowError();
    }

    @Override
    public void showError(Throwable throwable) {
        showError();
    }

    @Override
    public void setLight(Light light) {
        logger.debug("setLight() " + light.id());

        this.light = light;

        //showConnected();
    }

    @Override
    public LightViewState getViewState() {
        return (LightViewState) super.getViewState();
    }

    protected LightControlActivity getMainActivity() {
        return (LightControlActivity) getActivity();
    }

    /**
     * Shows the relevant information from the fetched Light.
     */
    protected abstract void showLight();

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightFragmentBase lightFragment);
    }
}
