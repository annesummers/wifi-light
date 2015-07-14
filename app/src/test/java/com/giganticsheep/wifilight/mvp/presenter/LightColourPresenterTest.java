package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.TestLightResponse;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.TestConstants;

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
        testSetPower(LightControl.Power.OFF);
    }

    @Test
    public void testSetPowerOn() throws Exception {
        testSetPower(LightControl.Power.ON);
    }

    private void testSetPower(LightControl.Power power) {
        getPresenter().setPower(power, TestConstants.TEST_DURATION);
    }

    @Test
    public void testHandleLightChangedConnected() throws Exception {
        TestLightResponse light = new TestLightResponse(TestConstants.TEST_ID);
        light.connected = true;

        getPresenter().handleLightChanged(new LightChangedEvent(light));

        assertThat(view.getLight(), equalTo((Light)light));
        assertThat(view.getState(), equalTo(view.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testHandleLightChangedDisconnected() throws Exception {
        TestLightResponse light = new TestLightResponse(TestConstants.TEST_ID);
        light.connected = false;

        getPresenter().handleLightChanged(new LightChangedEvent(light));

        assertThat(view.getLight(), equalTo((Light)light));
        assertThat(view.getState(), equalTo(view.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    private LightColourPresenter getPresenter() {
        return (LightColourPresenter) presenter;
    }
}