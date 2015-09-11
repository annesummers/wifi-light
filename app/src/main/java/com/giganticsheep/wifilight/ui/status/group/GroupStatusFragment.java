package com.giganticsheep.wifilight.ui.status.group;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivityComponent;
import com.giganticsheep.wifilight.ui.status.StatusFragment;
import com.giganticsheep.wifilight.ui.status.light.LightStatusViewState;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class GroupStatusFragment extends StatusFragment<GroupStatusView,
                                                        GroupStatusPresenter,
                                                        NavigationActivityComponent>
                                    implements GroupStatusView {

    public GroupStatusFragment() {
        super();
    }

    // MVP

    @NonNull
    @Override
    public GroupStatusPresenter createPresenter() {
        return new GroupStatusPresenter(getComponent());
    }

    @Override
    public ViewState createViewState() {
        return new LightStatusViewState();
    }

    @NonNull
    @Override
    public final GroupStatusViewState getViewState() {
        return (GroupStatusViewState) super.getViewState();
    }

    @DebugLog
    @Override
    public synchronized void showGroup(@NonNull final Group group) {
        getViewState().setShowGroup(group);

        firstSetPower = true;
        nameTextView.setText(group.getName());
    }

    // Dagger

    @Override
    public NavigationActivityComponent getComponent() {
        return (NavigationActivityComponent) getBaseActivity().getComponent();
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(GroupStatusFragment fragmentBase);
    }
}
