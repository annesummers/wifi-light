package com.giganticsheep.wifilight.ui.status.location;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.ui.navigation.NavigationViewStateActivity;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivityComponent;
import com.giganticsheep.wifilight.ui.status.StatusFragment;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class LocationStatusFragment extends StatusFragment<LocationStatusView,
                                                            LocationStatusPresenter,
                                                            NavigationActivityComponent>
                                    implements LocationStatusView {

    public LocationStatusFragment() {
        super();
    }

    // MVP

    @NonNull
    @Override
    public LocationStatusPresenter createPresenter() {
        return new LocationStatusPresenter(getComponent());
    }

    @Override
    public ViewState createViewState() {
        return new LocationStatusViewState();
    }

    @NonNull
    @Override
    public final LocationStatusViewState getViewState() {
        return (LocationStatusViewState) super.getViewState();
    }

    @DebugLog
    @Override
    public synchronized void showLocation(@NonNull final Location location) {
        getViewState().setShowLocation(location);

        firstSetPower = true;
        //powerToggle.setChecked(location.getPower() == LightControl.Power.ON);
        nameTextView.setText(location.getName());
    }

    // Dagger

    @Override
    public NavigationActivityComponent getComponent() {
        return ((NavigationViewStateActivity) getActivity()).getComponent();
    }

    @Override
    public void injectDependencies() {
        getComponent().inject(this);
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LocationStatusFragment fragmentBase);
    }
}
