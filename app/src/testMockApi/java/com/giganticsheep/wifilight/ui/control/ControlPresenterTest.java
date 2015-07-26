package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.ui.base.LightPresenterTestBase;
import com.giganticsheep.wifilight.ui.base.light.TestLightView;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class ControlPresenterTest extends LightPresenterTestBase {

    @NonNull
    @Override
    protected ControlPresenter createPresenter(@NonNull final ControlPresenter.Injector injector) {
        return new ControlPresenter(injector);
    }

    @Test
    public void testFetchLightsFromNetwork() {
        setTestStatus(LightControl.Status.OK);
        getPresenter().fetchLights(true);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LOADING));
    }

    @Test
    public void testFetchLightsFromCacheCached() {
        setTestStatus(LightControl.Status.OK);
        getPresenter().fetchLights(true);

        // TODO how to test this as it has no feedback in the PResenter

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LOADING));

        getPresenter().fetchLights(false);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LOADING));
    }

    @Test
    public void testFetchLightsFromCacheNotCached() {
        setTestStatus(LightControl.Status.OK);
        getPresenter().fetchLights(false);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LOADING));
    }

    @Test
    public void testTestFetchLightConnected() {
        setTestStatus(LightControl.Status.OK);
        testFetchLight();

        assertThat(view.getLight().id(), equalTo(Constants.TEST_ID));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testTestFetchLightDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        testFetchLight();

        assertThat(view.getLight().id(), equalTo(Constants.TEST_ID));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testTestFetchLightError() {
        setTestStatus(LightControl.Status.ERROR);
        getPresenter().fetchLight(Constants.TEST_ID);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void testFetchLight() {
        getPresenter().fetchLight(Constants.TEST_ID);
        handleLightChangedEvent();
    }

    @NonNull
    @Override
    protected ControlPresenter getPresenter() {
        return (ControlPresenter) presenter;
    }
}