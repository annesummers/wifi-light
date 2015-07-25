package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.mvp.view.TestLightView;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public class StatusPresenterTest extends LightPresenterTestBase {

    @NonNull
    @Override
    protected LightPresenterBase createPresenter(@NonNull final LightPresenterBase.Injector injector) {
        return new StatusPresenter(injector);
    }

    @NonNull
    @Override
    protected StatusPresenter getPresenter() {
        return (StatusPresenter) presenter;
    }

    @Test
    public void testSetPowerConnected() throws Exception {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        testSetPower(LightControl.Power.OFF);

        assertThat(view.getLight().getPower(), equalTo(LightControl.Power.OFF));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));

        testSetPower(LightControl.Power.ON);

        assertThat(view.getLight().getPower(), equalTo(LightControl.Power.ON));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testSetPowerDisconnected() throws Exception {
        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        LightControl.Power power = view.getLight().getPower();
        testSetPower(LightControl.Power.OFF);

        assertThat(view.getLight().getPower(), equalTo(power));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));

        power = view.getLight().getPower();
        testSetPower(LightControl.Power.ON);

        assertThat(view.getLight().getPower(), equalTo(power));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testSetPowerOffError() throws Exception {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.ERROR);
        getPresenter().setPower(LightControl.Power.OFF, TestConstants.TEST_DURATION);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @Test
    public void testSetPowerOnError() throws Exception {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.ERROR);
        getPresenter().setPower(LightControl.Power.ON, TestConstants.TEST_DURATION);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void testSetPower(LightControl.Power power) {
        getPresenter().setPower(power, TestConstants.TEST_DURATION);
        handleLightChangedEvent();
    }
}
