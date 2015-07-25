package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.mvp.view.TestLightView;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class ColourPresenterTest extends BrightnessPresenterTestBase {

    @NonNull
    @Override
    protected final LightPresenterBase createPresenter(@NonNull final LightPresenterBase.Injector injector) {
        return new ColourPresenter(injector);
    }

    @Test
    public void testSetHueConnected() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        testSetHue();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getHue(), equalTo(TestConstants.TEST_HUE_INT));
    }

    @Test
    public void testSetHueDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        int hue = view.getLight().getHue();

        testSetHue();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(view.getLight().getHue(), equalTo(hue));
    }

    @Test
    public void testSetHueConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.OK);
        testSetHue();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getHue(), equalTo(TestConstants.TEST_HUE_INT));
    }

    @Test
    public void testSetHueDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        int hue = view.getLight().getHue();

        setTestStatus(LightControl.Status.OFF);
        testSetHue();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(view.getLight().getHue(), equalTo(hue));
    }

    @Test
    public void testSetHueError() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.ERROR);
        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void testSetHue() {
        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);
        handleLightChangedEvent();
    }

    @Test
    public void testSetSaturationConnected() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        testSetSaturation();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getSaturation(), equalTo(TestConstants.TEST_SATURATION_INT));
    }

    @Test
    public void testSetSaturationDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        int saturation = view.getLight().getSaturation();

        testSetSaturation();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(view.getLight().getSaturation(), equalTo(saturation));
    }

    @Test
    public void testSetSaturationConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.OK);
        testSetSaturation();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getSaturation(), equalTo(TestConstants.TEST_SATURATION_INT));
    }

    @Test
    public void testSetSaturationDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        int saturation = view.getLight().getSaturation();

        setTestStatus(LightControl.Status.OFF);
        testSetSaturation();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(view.getLight().getSaturation(), equalTo(saturation));
    }

    @Test
    public void testSetSaturationError() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.ERROR);
        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void testSetSaturation() {
        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);
        handleLightChangedEvent();
    }

    @NonNull
    protected ColourPresenter getPresenter() {
        return (ColourPresenter) presenter;
    }
}