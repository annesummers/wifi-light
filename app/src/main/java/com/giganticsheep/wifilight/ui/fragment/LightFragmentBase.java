package com.giganticsheep.wifilight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.mvp.view.LightViewState;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */

@FragmentArgsInherited
public abstract class LightFragmentBase extends FragmentBase<LightView, LightPresenterBase>
                                    implements LightView {

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    protected final void injectDependencies() {
        getLightControlActivity().getComponent().inject(this);
    }

    @Override
    protected final void populateViews() {
        logger.debug("populateViews()");

        if(getPresenter().getLight() != null) {
            showLight();
        } else {
            logger.warn("showLight() light is null!");
        }
    }

    // MVP

    @NonNull
    @Override
    public final ViewState createViewState() {
        return new LightViewState();
    }

    @Override
    public final void onNewViewStateInstance() {
        getViewState().apply(this, true);
    }

    @NonNull
    @Override
    public final LightViewState getViewState() {
        return (LightViewState) super.getViewState();
    }

    @Override
    public final void showLoading() {
        logger.debug("showLoading()");

        getViewState().setShowLoading();
    }

    @Override
    public void showConnected() {
        logger.debug("showConnected()");

        getViewState().setShowConnected();

        populateViews();
        enableViews(true);
    }

    @Override
    public void showConnecting() {
        logger.debug("showConnecting()");

        getViewState().setShowConnecting();

        populateViews();
        enableViews(false);
    }

    @Override
    public void showDisconnected() {
        logger.debug("showDisconnected()");

        getViewState().setShowDisconnected();

        populateViews();
        enableViews(false);
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

    /*@Override
    public final void setLight(@NonNull Light light) {
        logger.debug("setLight() " + light.id());

        this.light = light;
    }
*/
    @NonNull
    protected final LightControlActivity getLightControlActivity() {
        return (LightControlActivity) getActivity();
    }

    /**
     * Shows the relevant information from the fetched Light.
     */
    protected abstract void showLight();

    /**
     * Disables all the views in this Fragment.
     *
     * @param enable whether to enable the views or not
     */
    protected abstract void enableViews(boolean enable);

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightFragmentBase lightFragment);
    }
}
