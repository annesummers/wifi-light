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
public class WhitePresenterTest extends BrightnessPresenterTestBase {

    @NonNull
    @Override
    protected FragmentPresenterBase doCreatePresenter(@NonNull final LightPresenterBase.Injector injector,
                                                      @NonNull final ControlPresenter controlPresenter) {
        return new WhitePresenter(injector, controlPresenter);
    }

    @NonNull
    @Override
    protected WhitePresenter getPresenter() {
        return (WhitePresenter) presenter;
    }

    @Test
    public void testSetKelvinConnected() {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getKelvin(), equalTo(TestConstants.TEST_KELVIN));
    }

    @Test
    public void testSetKelvinDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        controlPresenter.fetchLight(Constants.TEST_ID);

        int kelvin = getPresenter().getLight().getKelvin();

        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getKelvin(), equalTo(kelvin));
    }


    @Test
    public void testSetKelvinConnectedWasDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        controlPresenter.fetchLight(Constants.TEST_ID);

        setTestStatus(LightControl.Status.OK);
        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
        assertThat(getPresenter().getLight().getKelvin(), equalTo(TestConstants.TEST_KELVIN));
    }

    @Test
    public void testSetKelvinDisconnectedWasConnected() {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        int kelvin = getPresenter().getLight().getKelvin();

        setTestStatus(LightControl.Status.OFF);
        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
        assertThat(getPresenter().getLight().getKelvin(), equalTo(kelvin));
    }

    @Test
    public void testSetKelvinError() {
        setTestStatus(LightControl.Status.ERROR);
        controlPresenter.fetchLight(Constants.TEST_ID);

        getPresenter().setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }
}
