package com.giganticsheep.wifilight.ui.status;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
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
public abstract class StatusPresenterTest extends LightPresenterTestBase {

   /* @NonNull
    @Override
    protected StatusPresenterBase createPresenter(@NonNull final StatusPresenterBase.Injector injector) {
        return new StatusPresenterBase(injector);
    }

    @NonNull
    @Override
    protected StatusPresenterBase getPresenter() {
        return (StatusPresenterBase) presenter;
    }*/

    @Override
    protected LightPresenterBase createPresenter(LightPresenterBase.Injector injector) {
        return null;
    }

    @Test
    public void testSetPowerConnected() {
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
    public void testSetPowerDisconnected() {
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
    }/*

    @Test
    public void testSetPowerOffError() {
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
    public void testSetPowerOnError() {
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
*/}
