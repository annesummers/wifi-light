package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.TestLightResponse;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.TestConstants;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class LightControlPresenterTest extends LightPresenterTestBase {

    @Override
    protected LightPresenterBase createPresenter(LightPresenterBase.Injector injector) {
        return new LightControlPresenter(injector);
    }

    @Test
    public void testFetchLights() throws Exception {
        getPresenter().fetchLights();

        assertThat(view.getState(), equalTo(view.STATE_SHOW_LOADING));
    }

    @Test
    public void testGetCurrentLight() throws Exception {
        String lightId = getPresenter().getCurrentLightId();

        assertThat(lightId, equalTo(null));
    }

    @Test
    public void testHandleFetchLightsSuccess() throws Exception {
        Light light = new TestLightResponse(TestConstants.TEST_ID);

        getPresenter().handleLightDetails(new LightControl.LightDetailsEvent(light));
        getPresenter().handleFetchLightsSuccess(new LightControl.FetchLightsSuccessEvent());

        assertThat(view.getState(), equalTo(view.STATE_SHOW_LIGHT_DETAILS));
        assertThat(getPresenter().getCurrentLightId(), equalTo(light.id()));
    }

    @Test
    public void testHandleLightDetails() throws Exception {
        Light light = new TestLightResponse(TestConstants.TEST_ID);

        getPresenter().handleLightDetails(new LightControl.LightDetailsEvent(light));

        assertThat(view.getLight(), equalTo(light));
    }

    private LightControlPresenter getPresenter() {
        return (LightControlPresenter) presenter;
    }
}