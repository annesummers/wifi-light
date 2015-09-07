package com.giganticsheep.wifilight.ui.navigation.group;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivityComponent;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import butterknife.InjectView;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class GroupFragment extends FragmentBase<GroupView, GroupPresenter, NavigationActivityComponent>
                                implements GroupView {

    private GroupAdapter adapter;

    @Arg String groupId;

    @InjectView(R.id.lights_recycler_view) RecyclerView lightsRecyclerView;
    @InjectView(R.id.name_textview) TextView groupNameTextView;

    @DebugLog
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if(savedInstanceState != null) {
            Timber.d("here");
        }
    }

    @DebugLog
    @Override
    protected void initialiseViews(@NonNull final View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new GroupAdapter(getComponent());
        lightsRecyclerView.setHasFixedSize(true);
        lightsRecyclerView.setLayoutManager(layoutManager);
        lightsRecyclerView.setAdapter(adapter);
        lightsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //getPresenter().fetchGroup(groupId);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_group;
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    protected boolean animateOnShow() {
        return false;
    }

    // MVP

    @Override
    public GroupPresenter createPresenter() {
        return new GroupPresenter(getComponent());
    }

    @Override
    public ViewState createViewState() {
        return new GroupViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        getViewState().apply(this, false);
    }

    @NonNull
    @Override
    public final GroupViewState getViewState() {
        return (GroupViewState) super.getViewState();
    }

    @Override
    public void showLoading() {
        getViewState().setShowLoading();
    }

    @Override
    public void showGroup(@NonNull final Group group) {
        getViewState().setShowGroup(group);

        groupNameTextView.setText(group.getName());

        adapter.setGroup(group);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        getViewState().setShowError();
    }

    @Override
    public void showError(Throwable throwable) {
        getViewState().setShowError(throwable);
    }

    // Dagger

    @Override
    public NavigationActivityComponent getComponent() {
        return ((NavigationActivity) getActivity()).getComponent();
    }

    @Override
    protected void injectDependencies() {
        getComponent().inject(this);
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LocationFragment derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(GroupFragment groupFragment);
    }
}
