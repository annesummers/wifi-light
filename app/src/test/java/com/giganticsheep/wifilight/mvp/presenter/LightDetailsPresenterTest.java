package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.TestLightResponse;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.TestConstants;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class LightDetailsPresenterTest extends LightPresenterTestBase {

    @Override
    protected LightPresenterBase createPresenter(LightPresenterBase.Injector injector) {
        return new LightDetailsPresenter(injector);
    }

    @Test
    public void testHandleLightChanged() throws Exception {
        Light light = new TestLightResponse(TestConstants.TEST_ID);

        getPresenter().handleLightChanged(new LightChangedEvent(light));

        assertThat(view.getLight(), equalTo(light));
        assertThat(view.getState(), equalTo(view.STATE_SHOW_LIGHT_CONNECTED));
    }

    private LightDetailsPresenter getPresenter() {
        return (LightDetailsPresenter) presenter;
    }
}