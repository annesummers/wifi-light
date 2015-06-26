package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.view.View;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class LightEffectsFragment extends LightFragment {

    public static LightEffectsFragment newInstance(String name) {
        LightEffectsFragment fragment = new LightEffectsFragment();

        Bundle args = new Bundle();
        args.putString(RXFragment.FRAGMENT_ARGS_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public LightEffectsFragment() {
        super();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_effects;
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

  /*  @Override
    protected void initialiseData(Bundle savedInstanceState) {
        super.initialiseData(savedInstanceState);



        //app.registerForEvents(this);
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        app.registerForEvents(this);
    }

    @Override
    protected void initialiseViews(View view) {

    }
    @Override
    protected void setLightDetails() {

    }

    @Override
    protected void destroyViews() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        app.unregisterForEvents(this);
    }

    @Subscribe
    public void lightChange(LightNetwork.LightDetailsEvent event) {
        logger.debug("lightChange()");

        handleLightChange(event.light());
    }
}
