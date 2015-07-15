package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.mvp.view.TestLightView;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class LightColourPresenterTest extends LightFragmentPresenterTestBase {

    @NonNull
    @Override
    protected LightFragmentPresenter doCreatePresenter(LightPresenterBase.Injector injector, LightControlPresenter lightControlPresenter) {
        return new LightColourPresenter(injector, lightControlPresenter);
    }

    @Test
    public void testSetHueConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));

        Light light = getPresenter().getLight();
        assertThat(light, not(nullValue()));
        assertThat(light.getHue(), equalTo(TestConstants.TEST_HUE_INT));
    }

    @Test
    public void testSetHueDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        Light light = getPresenter().getLight();
        int hue = light.getHue();

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));

        light = getPresenter().getLight();
        assertThat(light, not(nullValue()));
        assertThat(getPresenter().getLight().getHue(), equalTo(hue));
    }

    @Test
    public void testSetHueError() {
        setTestStatus(LightControl.Status.ERROR);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    // TODO test status not as expected

    @Test
    public void testSetSaturationConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));

        Light light = getPresenter().getLight();
        assertThat(light, not(nullValue()));
        assertThat(light.getSaturation(), equalTo(TestConstants.TEST_SATURATION_INT));
    }

    @Test
    public void testSetSaturationDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        Light light = getPresenter().getLight();
        int saturation = light.getSaturation();

        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));

        light = getPresenter().getLight();
        assertThat(light, not(nullValue()));
        assertThat(getPresenter().getLight().getSaturation(), equalTo(saturation));
    }

    @Test
    public void testSetSaturationError() {
        setTestStatus(LightControl.Status.ERROR);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @Test
    public void testSetKelvinConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));

        Light light = getPresenter().getLight();
        assertThat(light, not(nullValue()));
        assertThat(light.getKelvin(), equalTo(TestConstants.TEST_KELVIN));
    }

    @Test
    public void testSetKelvinDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        Light light = getPresenter().getLight();
        int kelvin = light.getKelvin();

        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));

        light = getPresenter().getLight();
        assertThat(light, not(nullValue()));
        assertThat(getPresenter().getLight().getKelvin(), equalTo(kelvin));
    }

    @Test
    public void testSetKelvinError() {
        setTestStatus(LightControl.Status.ERROR);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @Test
    public void testSetBrightnessConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));

        Light light = getPresenter().getLight();
        assertThat(light, not(nullValue()));
        assertThat(light.getBrightness(), equalTo(TestConstants.TEST_BRIGHTNESS_INT));
    }

    @Test
    public void testSetBrightnessDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        Light light = getPresenter().getLight();
        int brightness = light.getBrightness();

        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));

        light = getPresenter().getLight();
        assertThat(light, not(nullValue()));
        assertThat(getPresenter().getLight().getBrightness(), equalTo(brightness));
    }

    @Test
    public void testSetBrightnessError() {
        setTestStatus(LightControl.Status.ERROR);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
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

    @NonNull
    protected LightColourPresenter getPresenter() {
        return (LightColourPresenter) presenter;
    }
}