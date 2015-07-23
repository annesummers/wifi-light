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
public class StatusPresenterTest extends FragmentPresenterTestBase {

    @NonNull
    @Override
    protected FragmentPresenterBase doCreatePresenter(@NonNull final LightPresenterBase.Injector injector,
                                                      @NonNull final ControlPresenter controlPresenter) {
        return new StatusPresenter(injector, controlPresenter);
    }

    @NonNull
    @Override
    protected StatusPresenter getPresenter() {
        return (StatusPresenter) presenter;
    }

    @Test
    public void testSetPowerConnected() throws Exception {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        testSetPower(LightControl.Power.OFF);

        assertThat(getPresenter().getLight().getPower(), equalTo(LightControl.Power.OFF));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));

        testSetPower(LightControl.Power.ON);

        assertThat(getPresenter().getLight().getPower(), equalTo(LightControl.Power.ON));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testSetPowerDisconnected() throws Exception {
        setTestStatus(LightControl.Status.OFF);
        controlPresenter.fetchLight(Constants.TEST_ID);

        LightControl.Power power = getPresenter().getLight().getPower();
        testSetPower(LightControl.Power.OFF);

        assertThat(getPresenter().getLight().getPower(), equalTo(power));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));

        power = getPresenter().getLight().getPower();
        testSetPower(LightControl.Power.ON);

        assertThat(getPresenter().getLight().getPower(), equalTo(power));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testSetPowerOffError() throws Exception {
        setTestStatus(LightControl.Status.ERROR);
        controlPresenter.fetchLight(Constants.TEST_ID);

        testSetPower(LightControl.Power.OFF);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @Test
    public void testSetPowerOnError() throws Exception {
        setTestStatus(LightControl.Status.ERROR);
        controlPresenter.fetchLight(Constants.TEST_ID);

        testSetPower(LightControl.Power.ON);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void testSetPower(LightControl.Power power) {
        getPresenter().setPower(power, TestConstants.TEST_DURATION);
    }
}
