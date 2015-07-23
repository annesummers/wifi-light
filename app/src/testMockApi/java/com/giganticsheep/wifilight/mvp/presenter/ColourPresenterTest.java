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
public class ColourPresenterTest extends FragmentPresenterTestBase {

    @NonNull
    @Override
    protected FragmentPresenterBase doCreatePresenter(@NonNull final LightPresenterBase.Injector injector,
                                                      @NonNull final ControlPresenter controlPresenter) {
        return new ColourPresenter(injector, controlPresenter);
    }

    @Test
    public void testSetHueConnected() {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getHue(), equalTo(TestConstants.TEST_HUE_INT));
    }

    @Test
    public void testSetHueDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        controlPresenter.fetchLight(Constants.TEST_ID);

        int hue = getPresenter().getLight().getHue();

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getHue(), equalTo(hue));
    }

    @Test
    public void testSetHueConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        controlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OK);
        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getHue(), equalTo(TestConstants.TEST_HUE_INT));
    }

    @Test
    public void testSetHueDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OFF);

        int hue = getPresenter().getLight().getHue();

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getHue(), equalTo(hue));
    }

    @Test
    public void testSetHueError() {
        setTestStatus(LightControl.Status.ERROR);
        controlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @Test
    public void testSetSaturationConnected() {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getSaturation(), equalTo(TestConstants.TEST_SATURATION_INT));
    }

    @Test
    public void testSetSaturationDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        controlPresenter.fetchLight(Constants.TEST_ID);

        int saturation = getPresenter().getLight().getSaturation();

        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getSaturation(), equalTo(saturation));
    }

    @Test
    public void testSetSaturationConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        controlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OK);
        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getSaturation(), equalTo(TestConstants.TEST_SATURATION_INT));
    }

    @Test
    public void testSetSaturationDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        int saturation = getPresenter().getLight().getSaturation();

        setTestStatus(LightControl.Status.OFF);
        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getSaturation(), equalTo(saturation));
    }

    @Test
    public void testSetSaturationError() {
        setTestStatus(LightControl.Status.ERROR);
        controlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @NonNull
    protected ColourPresenter getPresenter() {
        return (ColourPresenter) presenter;
    }
}