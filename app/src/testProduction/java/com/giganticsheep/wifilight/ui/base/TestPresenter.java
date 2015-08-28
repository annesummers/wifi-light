package com.giganticsheep.wifilight.ui.base;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.test.MockLight;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.util.Constants;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 16/07/15. <p>
 * (*_*)
 */
public class TestPresenter extends LightPresenterBase {

    private final MockLight light = new MockLight(Constants.TEST_ID, Constants.TEST_LABEL);
    private final LightFragmentBase fragment;

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    public TestPresenter(@NonNull final Injector injector,
                         @NonNull final LightFragmentBase fragment) {
        super(injector);

        this.fragment = fragment;

        light.connected = TestConstants.TEST_CONNECTED.equals(TestConstants.TEST_CONNECTED_STRING) ? true : false;
        light.power = TestConstants.TEST_POWER ?
                LightControl.Power.ON.getPowerString() :
                LightControl.Power.OFF.getPowerString();

        light.brightness = TestConstants.TEST_BRIGHTNESS_DOUBLE;
        light.color.hue = TestConstants.TEST_HUE_DOUBLE;
        light.color.kelvin = TestConstants.TEST_KELVIN;
        light.color.saturation = TestConstants.TEST_SATURATION_DOUBLE;
    }

    @Override
    public void fetchLight(final String id) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        if (light.getId().equals(id)) {
            fragment.getPresenter().handleLightChanged(light);
        }
    }

    public Light getLight() {
        return light;
    }
}
