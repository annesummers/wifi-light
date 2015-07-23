package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.mvp.view.TestLightView;
import com.giganticsheep.wifilight.util.Constants;
import com.giganticsheep.wifilight.util.TestEventBus;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class ControlPresenterTest extends LightPresenterTestBase {

    @NonNull
    @Override
    protected LightPresenterBase createPresenter(@NonNull final LightPresenterBase.Injector injector) {
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
    public void testGetCurrentLightNoLight() {
        Light light = getPresenter().getLight();

        assertThat(light, equalTo(null));
    }

    @Test
    public void testGetCurrentLightWithLight() {
        setTestStatus(LightControl.Status.OK);
        getPresenter().fetchLight(Constants.TEST_ID);

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
    public void testTestFetchLightDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        doTestFetchLight();
        
        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testTestFetchLightError() {
        setTestStatus(LightControl.Status.ERROR);
        getPresenter().fetchLight(Constants.TEST_ID);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    private void doTestFetchLight() {
        getPresenter().fetchLight(Constants.TEST_ID);

        Object lightChangedEvent = ((TestEventBus)eventBus).popLastMessage();
        assertThat(lightChangedEvent, instanceOf(LightChangedEvent.class));

        getPresenter().handleLightChanged((LightChangedEvent)lightChangedEvent);

        assertThat(getPresenter().getLight().id(), equalTo(Constants.TEST_ID));
    }

    @NonNull
    private ControlPresenter getPresenter() {
        return (ControlPresenter) presenter;
    }
}