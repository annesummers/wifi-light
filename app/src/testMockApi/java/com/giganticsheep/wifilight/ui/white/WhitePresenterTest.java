package com.giganticsheep.wifilight.ui.white;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterTestBase;
import com.giganticsheep.wifilight.ui.base.light.TestLightView;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public class WhitePresenterTest extends LightPresenterTestBase {

    @NonNull
    @Override
    protected WhitePresenter createPresenter(WhitePresenter.Injector injector) {
        return new WhitePresenter(injector);
    }

    @NonNull
    @Override
    protected WhitePresenter getPresenter() {
        return (WhitePresenter) presenter;
    }

    @Test
    public void testSetKelvinConnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        testSetKelvin();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getKelvin(), equalTo(TestConstants.TEST_KELVIN));
    }

    @Test
    public void testSetKelvinDisconnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        int kelvin = view.getLight().getKelvin();

        testSetKelvin();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(view.getLight().getKelvin(), equalTo(kelvin));
    }


    @Test
    public void testSetKelvinConnectedWasDisconnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.OK);
        testSetKelvin();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(view.getLight().getKelvin(), equalTo(TestConstants.TEST_KELVIN));
    }

    @Test
    public void testSetKelvinDisconnectedWasConnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        int kelvin = view.getLight().getKelvin();

        setTestStatus(LightControl.Status.OFF);
        testSetKelvin();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(view.getLight().getKelvin(), equalTo(kelvin));
    }

    @Test
    public void testSetKelvinError() {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.ERROR);
        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void testSetKelvin() {
        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);
        handleLightChangedEvent();
    }
}
