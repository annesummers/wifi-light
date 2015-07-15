package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.mvp.view.TestLightView;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

/**
 * DESCRIPTION HERE ANNE <p>
 *
 * Created by anne on 15/07/15. <p>
 *
 * (*_*)
 */
public abstract class LightFragmentPresenterTestBase extends LightPresenterTestBase {

    protected LightControlPresenter lightControlPresenter;

    @NonNull
    @Override
    protected final LightPresenterBase createPresenter(@NonNull final LightPresenterBase.Injector injector) {
        lightControlPresenter = new LightControlPresenter(injector);
        lightControlPresenter.attachView(new TestLightView(this, baseLogger));

        return doCreatePresenter(injector, lightControlPresenter);
    }

    @Test
    public void testGetCurrentLightNoLight() {
        Light light = getPresenter().getLight();

        assertThat(light, equalTo(null));
    }

    @Test
    public void testGetCurrentLightWithLight() {
        setTestStatus(LightControl.Status.OK);
        lightControlPresenter.fetchLight(Constants.TEST_ID);

        Light light = getPresenter().getLight();

        assertThat(light, not(nullValue()));
        assertThat(light.id(), equalTo(Constants.TEST_ID));
    }

    @Test
    public void testTestFetchLightConnected() {
        setTestStatus(LightControl.Status.OK);
        doTestFetchLight();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testTestFetchLightConnecting() {
        setTestStatus(LightControl.Status.OK);
        setLightTimeout();

        doTestFetchLight();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTING));
    }

    @Test
    public void testTestFetchLightDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        doTestFetchLight();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testTestFetchLightError() {
        setTestStatus(LightControl.Status.ERROR);
        doTestFetchLight();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void doTestFetchLight() {
        getPresenter().fetchLight(Constants.TEST_ID);
    }

    @NonNull
    protected LightFragmentPresenter getPresenter() {
        return (LightFragmentPresenter) presenter;
    }

    protected abstract LightFragmentPresenter doCreatePresenter(LightPresenterBase.Injector injector, LightControlPresenter lightControlPresenter);
}