package com.giganticsheep.wifilight.ui;

import android.os.Bundle;

import com.giganticsheep.wifilight.api.model.LightDataResponse;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.ui.rx.BaseApplication;
import com.giganticsheep.wifilight.ui.rx.BaseFragment;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import javax.inject.Inject;

import icepick.Icicle;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */

@FragmentArgsInherited
public abstract class LightFragment extends BaseFragment {

   // private static final String CURRENT_LIGHT_ARGUMENT = "current_light";

    @Icicle protected LightDataResponse light;

    @Inject LightNetwork lightNetwork;
    @Inject BaseApplication.EventBus eventBus;

    @Override
    protected void injectDependencies() {
        getMainActivity().getComponent().inject(this);
    }

    protected void initialiseData(Bundle savedInstanceState) { }


    @Override
    protected void populateViews() {
        if(light != null && viewsInitialised) {
            setLightDetails();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        logger.debug("onStop()");

        //getArguments().putSerializable(CURRENT_LIGHT_ARGUMENT, light);
    }

    protected void handleLightChange(LightDataResponse light) {
        this.light = light;

        populateViews();
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getBaseActivity();
    }

    protected abstract void setLightDetails();
}
