package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.mvp.view.TestLightView;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class LightColourPresenterTest extends LightFragmentPresenterTestBase {

    @NonNull
    @Override
    protected LightFragmentPresenterBase doCreatePresenter(LightPresenterBase.Injector injector, LightControlPresenter lightControlPresenter) {
        return new LightColourPresenter(injector, lightControlPresenter);
    }

    @Test
    public void testSetHueConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getHue(), equalTo(TestConstants.TEST_HUE_INT));
    }

    @Test
    public void testSetHueDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        int hue = getPresenter().getLight().getHue();

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getHue(), equalTo(hue));
    }

    @Test
    public void testSetHueConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OK);
        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getHue(), equalTo(TestConstants.TEST_HUE_INT));
    }

    @Test
    public void testSetHueDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OFF);

        int hue = getPresenter().getLight().getHue();

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getHue(), equalTo(hue));
    }

    @Test
    public void testSetHueError() {
        setTestStatus(LightControl.Status.ERROR);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @Test
    public void testSetSaturationConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getSaturation(), equalTo(TestConstants.TEST_SATURATION_INT));
    }

    @Test
    public void testSetSaturationDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        int saturation = getPresenter().getLight().getSaturation();

        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getSaturation(), equalTo(saturation));
    }

    @Test
    public void testSetSaturationConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OK);
        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getSaturation(), equalTo(TestConstants.TEST_SATURATION_INT));
    }

    @Test
    public void testSetSaturationDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        int saturation = getPresenter().getLight().getSaturation();

        setTestStatus(LightControl.Status.OFF);
        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
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
        assertThat(getPresenter().getLight().getKelvin(), equalTo(TestConstants.TEST_KELVIN));
    }

    @Test
    public void testSetKelvinDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        int kelvin = getPresenter().getLight().getKelvin();

        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getKelvin(), equalTo(kelvin));
    }


    @Test
    public void testSetKelvinConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OK);
        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getKelvin(), equalTo(TestConstants.TEST_KELVIN));
    }

    @Test
    public void testSetKelvinDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        int kelvin = getPresenter().getLight().getKelvin();

        setTestStatus(LightControl.Status.OFF);
        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
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
        assertThat(getPresenter().getLight().getBrightness(), equalTo(TestConstants.TEST_BRIGHTNESS_INT));
    }

    @Test
    public void testSetBrightnessDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        int brightness = getPresenter().getLight().getBrightness();

        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getBrightness(), equalTo(brightness));
    }

    @Test
    public void testSetBrightnessConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OK);
        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getBrightness(), equalTo(TestConstants.TEST_BRIGHTNESS_INT));
    }

    @Test
    public void testSetBrightnessDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        int brightness = getPresenter().getLight().getBrightness();

        setTestStatus(LightControl.Status.OFF);
        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
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
    public void testSetPowerConnected() throws Exception {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        testSetPower(LightControl.Power.OFF);

        assertThat(getPresenter().getLight().getPower(), equalTo(LightControl.Power.OFF));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));

        testSetPower(LightControl.Power.ON);

        assertThat(getPresenter().getLight().getPower(), equalTo(LightControl.Power.ON));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testSetPowerDisconnected() throws Exception {
        setTestStatus(LightControl.Status.OFF);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        LightControl.Power power = getPresenter().getLight().getPower();
        testSetPower(LightControl.Power.OFF);

        assertThat(getPresenter().getLight().getPower(), equalTo(power));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));

        power = getPresenter().getLight().getPower();
        testSetPower(LightControl.Power.ON);

        assertThat(getPresenter().getLight().getPower(), equalTo(power));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testSetPowerOffError() throws Exception {
        setTestStatus(LightControl.Status.ERROR);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        testSetPower(LightControl.Power.OFF);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @Test
    public void testSetPowerOnError() throws Exception {
        setTestStatus(LightControl.Status.ERROR);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        testSetPower(LightControl.Power.ON);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void testSetPower(LightControl.Power power) {
        getPresenter().setPower(power, TestConstants.TEST_DURATION);
    }

    @NonNull
    protected LightColourPresenter getPresenter() {
        return (LightColourPresenter) presenter;
    }
}