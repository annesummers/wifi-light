package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.util.Constants;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class ControlPresenterTest extends LightPresenterTestBase {

    @NonNull
    @Override
    protected LightControlPresenter createPresenter(@NonNull final LightControlPresenter.Injector injector) {
        return new LightControlPresenter(injector);
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
    protected LightControlPresenter getPresenter() {
        return (LightControlPresenter) presenter;
    }
}