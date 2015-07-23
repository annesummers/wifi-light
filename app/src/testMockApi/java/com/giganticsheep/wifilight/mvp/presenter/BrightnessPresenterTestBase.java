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
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public abstract class BrightnessPresenterTestBase extends FragmentPresenterTestBase {

    @NonNull
    @Override
    protected BrightnessPresenterBase getPresenter() {
        return (BrightnessPresenterBase) presenter;
    }

    @Test
    public void testSetBrightnessConnected() {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getBrightness(), equalTo(TestConstants.TEST_BRIGHTNESS_INT));
    }

    @Test
    public void testSetBrightnessDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        controlPresenter.fetchLight(Constants.TEST_ID);

        int brightness = getPresenter().getLight().getBrightness();

        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getBrightness(), equalTo(brightness));
    }

    @Test
    public void testSetBrightnessConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        controlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OK);
        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getBrightness(), equalTo(TestConstants.TEST_BRIGHTNESS_INT));
    }

    @Test
    public void testSetBrightnessDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        int brightness = getPresenter().getLight().getBrightness();

        setTestStatus(LightControl.Status.OFF);
        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getBrightness(), equalTo(brightness));
    }

    @Test
    public void testSetBrightnessError() {
        setTestStatus(LightControl.Status.ERROR);
        controlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }
}
