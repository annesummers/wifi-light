package com.giganticsheep.wifilight.ui.navigation.location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.ui.navigation.NavigationActivity;
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
public class LocationFragment extends FragmentBase<LocationView, LocationPresenter>
                                implements LocationView {

    private LocationAdapter adapter;

    @InjectView(R.id.groups_recycler_view) RecyclerView groupsRecyclerView;
    @InjectView(R.id.name_textview) TextView locationNameTextView;

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
    protected void initialiseViews(final View view) {
        //LightNetworkClickListener lightNetworkClickListener = new LightNetworkClickListener(component,
       //         locationsListView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new LocationAdapter(getNavigationActivity().getComponent());
        groupsRecyclerView.setHasFixedSize(true);
        groupsRecyclerView.setLayoutManager(layoutManager);
        groupsRecyclerView.setAdapter(adapter);
        groupsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_location;
    }

    @NonNull
    private NavigationActivity getNavigationActivity() {
        return (NavigationActivity) getActivity();
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    public LocationPresenter createPresenter() {
        return new LocationPresenter(getNavigationActivity().getComponent());
    }

    @Override
    public ViewState createViewState() {
        return new LocationViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        getViewState().apply(this, false);
    }

    @NonNull
    @Override
    public final LocationViewState getViewState() {
        return (LocationViewState) super.getViewState();
    }

    @Override
    public void showLoading() {
        getViewState().setShowLoading();
    }

    @Override
    public void showLocation(@NonNull final Location location) {
        getViewState().setShowLocation(location);

        locationNameTextView.setText(location.getName());

        adapter.setLocation(location);
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

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LocationFragment derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LocationFragment locationFragment);
    }
}
