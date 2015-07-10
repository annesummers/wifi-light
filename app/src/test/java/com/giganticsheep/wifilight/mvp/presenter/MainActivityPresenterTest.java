package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.base.TestConstants;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class MainActivityPresenterTest extends LightPresenterTest {

    @Override
    protected LightPresenter createPresenter(LightPresenter.Injector injector) {
        return new MainActivityPresenter(injector);
    }

    @Test
    public void testFetchLights() throws Exception {
        getPresenter().fetchLights();

        assertThat(view.getState(), equalTo(view.STATE_SHOW_LOADING));
    }

    @Test
    public void testGetCurrentLight() throws Exception {
        String lightId = getPresenter().getCurrentLight();

        assertThat(lightId, equalTo(null));
    }

    @Test
    public void testHandleFetchLightsSuccess() throws Exception {
        Light light = new Light(TestConstants.TEST_ID);

        getPresenter().handleLightDetails(new LightNetwork.LightDetailsEvent(light));
        getPresenter().handleFetchLightsSuccess(new LightNetwork.FetchLightsSuccessEvent());

        assertThat(view.getState(), equalTo(view.STATE_SHOW_LIGHT_DETAILS));
        assertThat(getPresenter().getCurrentLight(), equalTo(light.id()));
    }

    @Test
    public void testHandleLightDetails() throws Exception {
        Light light = new Light(TestConstants.TEST_ID);

        getPresenter().handleLightDetails(new LightNetwork.LightDetailsEvent(light));

        assertThat(view.getLight(), equalTo(light));
    }

    private MainActivityPresenter getPresenter() {
        return (MainActivityPresenter) presenter;
    }
}