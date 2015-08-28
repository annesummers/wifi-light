package com.giganticsheep.wifilight.ui.base.light;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.TestConstants;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public class BrightnessPresenterTest extends LightPresenterTestBase {

    @NonNull
    @Override
    protected BrightnessPresenter getPresenter() {
        return (BrightnessPresenter) presenter;
    }

    @Override
    protected LightPresenterBase createPresenter(LightPresenterBase.Injector injector) {
        return new BrightnessPresenter(injector);
    }

    @Test
    public void testSetBrightnessConnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        testSetBrightness();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getBrightness(), equalTo(TestConstants.TEST_BRIGHTNESS_INT));
    }

    @Test
    public void testSetBrightnessDisconnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        int brightness = view.getLight().getBrightness();

        testSetBrightness();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(view.getLight().getBrightness(), equalTo(brightness));
    }

    @Test
    public void testSetBrightnessConnectedWasDisconnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.OK);
        testSetBrightness();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getBrightness(), equalTo(TestConstants.TEST_BRIGHTNESS_INT));
    }

    @Test
    public void testSetBrightnessDisconnectedWasConnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

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
        if(BuildConfig.DEBUG) {
            return;
        }

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
