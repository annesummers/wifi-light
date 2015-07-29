package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
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
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OK);
        getPresenter().fetchLightNetwork();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LOADING));

        //  TODO test events
    }

    @Test
    public void testTestFetchLightConnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OK);
        testFetchLight();

        assertThat(view.getLight().getId(), equalTo(Constants.TEST_ID));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testTestFetchLightDisconnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OFF);
        testFetchLight();

        assertThat(view.getLight().getId(), equalTo(Constants.TEST_ID));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testTestFetchLightError() {
        if(BuildConfig.DEBUG) {
            return;
        }

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