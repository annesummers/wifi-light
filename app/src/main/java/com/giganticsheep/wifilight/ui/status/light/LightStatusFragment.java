package com.giganticsheep.wifilight.ui.status.light;

import android.support.annotation.NonNull;
import android.view.View;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.giganticsheep.wifilight.ui.control.LightControlActivityComponent;
import com.giganticsheep.wifilight.ui.status.StatusFragment;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class LightStatusFragment extends StatusFragment<LightStatusView,
                                                        LightStatusPresenter,
                                                        LightControlActivityComponent>
                                    implements LightStatusView {

    public LightStatusFragment() {
        super();
    }

    @Override
    public final void initialiseViews(View view) {
        super.initialiseViews(view);

        allLightsButton.setVisibility(View.INVISIBLE);
    }

    // MVP

    @NonNull
    @Override
    public LightStatusPresenter createPresenter() {
        return new LightStatusPresenter(getComponent());
    }

    @Override
    public ViewState createViewState() {
        return new LightStatusViewState();
    }

    @NonNull
    @Override
    public final LightStatusViewState getViewState() {
        return (LightStatusViewState) super.getViewState();
    }

    //@DebugLog
    @Override
    public synchronized void showLight(@NonNull final Light light) {
        getViewState().setShowLight(light);

        firstSetPower = true;
        powerToggle.setChecked(light.getPower() == LightControl.Power.ON);
        nameTextView.setText(light.getLabel());
    }

    // Dagger

    @Override
    public LightControlActivityComponent getComponent() {
        return ((LightControlActivity) getActivity()).getComponent();
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightStatusFragment fragmentBase);
    }
}
