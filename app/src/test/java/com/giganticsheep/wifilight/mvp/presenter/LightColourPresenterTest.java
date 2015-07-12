package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.api.model.Light;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class LightColourPresenterTest extends LightPresenterTestBase {

    @Override
    protected LightPresenterBase createPresenter(LightPresenterBase.Injector injector) {
        return new LightColourPresenter(injector);
    }

    @Test
    public void testSetHue() throws Exception {
        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);
    }

    @Test
    public void testSetSaturation() throws Exception {
        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);
    }

    @Test
    public void testSetBrightness() throws Exception {
        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);
    }

    @Test
    public void testSetKelvin() throws Exception {
        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);
    }

    @Test
    public void testSetPowerOff() throws Exception {
        testSetPower(ModelConstants.Power.OFF);
    }

    @Test
    public void testSetPowerOn() throws Exception {
        testSetPower(ModelConstants.Power.ON);
    }

    private void testSetPower(ModelConstants.Power power) {
        getPresenter().setPower(power, TestConstants.TEST_DURATION);
    }

    @Test
    public void testHandleLightDetails() throws Exception {
        Light light = new Light(TestConstants.TEST_ID);

        getPresenter().handleLightDetails(new LightNetwork.LightDetailsEvent(light));

        assertThat(view.getLight(), equalTo(light));
        assertThat(view.getState(), equalTo(view.STATE_SHOW_LIGHT_DETAILS));
    }

    private LightColourPresenter getPresenter() {
        return (LightColourPresenter) presenter;
    }
}