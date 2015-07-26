package com.giganticsheep.wifilight.ui.base.light;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import hugo.weaving.DebugLog;

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
      /*  Light light = light;
        if(light != null) {
            getPresenter().handleLightChanged(light);
        }*/
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
        getViewState().apply(this, false);
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
    public void showConnected(@NonNull final Light light) {
        getViewState().setShowConnected(light);

        showLight(light);
        enableViews(true);
    }

    @DebugLog
    @Override
    public void showConnecting(@NonNull final Light light) {
        getViewState().setShowConnecting(light);

        showLight(light);
        enableViews(false);
    }

    @DebugLog
    @Override
    public void showDisconnected(@NonNull final Light light) {
        getViewState().setShowDisconnected(light);

        showLight(light);
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

    /**
     * Shows the relevant information from the fetched Light.
     */
    protected abstract void showLight(@NonNull final Light light);

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
