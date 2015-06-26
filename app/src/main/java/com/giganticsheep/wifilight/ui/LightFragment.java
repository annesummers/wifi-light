package com.giganticsheep.wifilight.ui;

import android.os.Bundle;

import com.giganticsheep.wifilight.api.LightControlInterface;
import com.giganticsheep.wifilight.api.network.LightDataResponse;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import javax.inject.Inject;

import icepick.Icicle;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */

@FragmentArgsInherited
public abstract class LightFragment extends RXFragment {

   // private static final String CURRENT_LIGHT_ARGUMENT = "current_light";

    @Icicle protected LightDataResponse light;
    @Inject protected LightControlInterface lightController;

    @Override
    public void onStop() {
        super.onStop();

        logger.debug("onStop()");

        //getArguments().putSerializable(CURRENT_LIGHT_ARGUMENT, light);
    }

    public MainActivity getMainActivity() {
        return (MainActivity) getRXActivity();
    }

    protected void initialiseData(Bundle savedInstanceState) {
       /* if(savedInstanceState != null && light == null) {
            // we are storing the saved variables in the arguments as
            // the calls to onSaveInstanceState can sometimes be missed
            Bundle mySavedInstanceState = getArguments();
            light = (LightDataResponse) mySavedInstanceState.getSerializable(CURRENT_LIGHT_ARGUMENT);
        }*/
    }

    @Override
    protected void populateViews() {
        if(light != null && viewsInitialised) {
            setLightDetails();
        }
    }

    protected void handleLightChange(LightDataResponse light) {
        this.light = light;

        populateViews();
    }

    protected abstract void setLightDetails();
}
