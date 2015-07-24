package com.giganticsheep.wifilight.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.mvp.view.LightViewState;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */

@FragmentArgsInherited
public abstract class LightFragmentBase extends FragmentBase<LightView, LightPresenterBase>
                                    implements LightView {

    protected OnLightSeekBarChangeListener seekBarChangeListener;

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
        Light light = getPresenter().getLight();
        if(light != null) {
            getPresenter().handleLightChanged(light);
        }
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

    @DebugLog
    @Override
    public final void showLoading() {
        getViewState().setShowLoading();

        enableViews(false);
    }

    @DebugLog
    @Override
    public void showConnected() {
        getViewState().setData(getPresenter().getLight());
        getViewState().setShowConnected();

        showLight();
        enableViews(true);
    }

    @DebugLog
    @Override
    public void showConnecting() {
        getViewState().setData(getPresenter().getLight());
        getViewState().setShowConnecting();

        showLight();
        enableViews(false);
    }

    @DebugLog
    @Override
    public void showDisconnected() {
        getViewState().setData(getPresenter().getLight());
        getViewState().setShowDisconnected();

        showLight();
        enableViews(false);
    }

    @DebugLog
    @Override
    public void showError() {
        getViewState().setShowError();
    }

    @Override
    public void showError(Throwable throwable) {
        showError();
    }

    private void showLight() {
        Light light = getPresenter().getLight();

        if(light == null) {
            Timber.e("showLight() light is null");
            return;
        }

        showLight(light);
    }

    /**
     * Shows the relevant information from the fetched Light.
     */
    protected abstract void showLight(Light light);

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
