package com.giganticsheep.wifilight.ui;

import android.os.Bundle;

import com.giganticsheep.wifilight.model.Light;
import com.giganticsheep.wifilight.ui.rx.RXFragment;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */
public abstract class LightFragment extends RXFragment {

    private static final String CURRENT_LIGHT_ARGUMENT = "current_light";

    protected Light light;

    @Override
    public void onStop() {
        super.onStop();

        logger.debug("onStop()");

        getArguments().putSerializable(CURRENT_LIGHT_ARGUMENT, light);
    }

    public MainActivity getMainActivity() {
        return (MainActivity) getRXActivity();
    }

    protected void initialiseData(Bundle savedInstanceState) {
        if(savedInstanceState != null && light == null) {
            // we are storing the saved variables in the arguments as
            // the calls to onSaveInstanceState can sometimes be missed
            Bundle mySavedInstanceState = getArguments();
            light = (Light) mySavedInstanceState.getSerializable(CURRENT_LIGHT_ARGUMENT);
        }
    }

    @Override
    protected void populateViews() {
        if(light != null && viewsInitialised) {
            setLightDetails();
        }
    }

    protected void handleLightChange(Light light) {
        this.light = light;

        setLightDetails();
    }

    protected abstract void setLightDetails();
}
