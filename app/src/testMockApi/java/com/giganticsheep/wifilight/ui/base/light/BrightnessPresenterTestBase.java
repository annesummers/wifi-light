package com.giganticsheep.wifilight.ui.base.light;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.base.LightPresenterTestBase;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public abstract class BrightnessPresenterTestBase extends LightPresenterTestBase {

    @NonNull
    @Override
    protected BrightnessPresenterBase getPresenter() {
        return (BrightnessPresenterBase) presenter;
    }

    @Test
    public void testSetBrightnessConnected() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        testSetBrightness();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getBrightness(), equalTo(TestConstants.TEST_BRIGHTNESS_INT));
    }

    @Test
    public void testSetBrightnessDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        int brightness = view.getLight().getBrightness();

        testSetBrightness();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(view.getLight().getBrightness(), equalTo(brightness));
    }

    @Test
    public void testSetBrightnessConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.OK);
        testSetBrightness();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getBrightness(), equalTo(TestConstants.TEST_BRIGHTNESS_INT));
    }

    @Test
    public void testSetBrightnessDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        int brightness = view.getLight().getBrightness();

        setTestStatus(LightControl.Status.OFF);
        testSetBrightness();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(view.getLight().getBrightness(), equalTo(brightness));
    }

    @Test
    public void testSetBrightnessError() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.ERROR);
        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void testSetBrightness() {
        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);
        handleLightChangedEvent();
    }
}
