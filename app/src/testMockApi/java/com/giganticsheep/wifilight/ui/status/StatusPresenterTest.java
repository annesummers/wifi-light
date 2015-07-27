package com.giganticsheep.wifilight.ui.status;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.ui.base.LightPresenterTestBase;
import com.giganticsheep.wifilight.ui.base.light.TestLightView;

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
    protected StatusPresenter createPresenter(@NonNull final StatusPresenter.Injector injector) {
        return new StatusPresenter(injector);
    }

    @NonNull
    @Override
    protected StatusPresenter getPresenter() {
        return (StatusPresenter) presenter;
    }

    @Test
    public void testSetPowerConnected() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        testSetPower(false);

        assertThat(view.getLight().getPower(), equalTo(LightControl.Power.OFF));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));

        testSetPower(true);

        assertThat(view.getLight().getPower(), equalTo(LightControl.Power.ON));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testSetPowerDisconnected() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        LightControl.Power power = view.getLight().getPower();
        testSetPower(false);

        assertThat(view.getLight().getPower(), equalTo(power));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));

        power = view.getLight().getPower();
        testSetPower(true);

        assertThat(view.getLight().getPower(), equalTo(power));
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testSetPowerOffError() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.ERROR);
        getPresenter().setPower(false);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @Test
    public void testSetPowerOnError() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        setTestStatus(LightControl.Status.ERROR);
        getPresenter().setPower(true);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void testSetPower(boolean isOn) {
        getPresenter().setPower(isOn);
        handleLightChangedEvent();
    }
}
