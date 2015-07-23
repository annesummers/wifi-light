package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.TestLightResponse;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.fragment.LightFragmentBase;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 16/07/15. <p>
 * (*_*)
 */
public class TestPresenter extends LightPresenterBase {

    private final TestLightResponse light = new TestLightResponse(TestConstants.TEST_ID);
    private final LightFragmentBase fragment;

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    public TestPresenter(@NonNull Injector injector, LightFragmentBase fragment) {
        super(injector);

        this.fragment = fragment;

        light.label = TestConstants.TEST_LABEL;

        light.connected = TestConstants.TEST_CONNECTED.equals(TestConstants.TEST_CONNECTED_STRING) ? true : false;
        light.power = TestConstants.TEST_POWER ? LightControl.Power.ON : LightControl.Power.OFF;

        light.brightness = TestConstants.TEST_BRIGHTNESS_DOUBLE;
        light.color.hue = TestConstants.TEST_HUE_DOUBLE;
        light.color.kelvin = TestConstants.TEST_KELVIN;
        light.color.saturation = TestConstants.TEST_SATURATION_DOUBLE;
    }

    @Override
    public void fetchLight(final String id) {
        if (light.id().equals(id)) {
            fragment.getPresenter().handleLightChanged(light);
        }
    }

    @Override
    public Light getLight() {
        return light;
    }
}
