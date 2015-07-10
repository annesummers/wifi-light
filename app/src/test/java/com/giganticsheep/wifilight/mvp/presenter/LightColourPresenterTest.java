package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.base.WifiLightTest;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.mvp.view.LightView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class LightColourPresenterTest extends WifiLightTest {

    // TODO test the event stuff

    private LightColourPresenter presenter;

    @Override
    protected void injectDependencies() {
        component.inject(this);
    }

    @Before
    public void setUp() throws Exception {
        presenter = new LightColourPresenter(lightNetwork, eventBus);

        LightView view = new LightView() {
            private Light light;

            @Override
            public void showLoading() {
                logger.warn("showLoading()");
            }

            @Override
            public void showError() {
                logger.warn("showError()");
            }

            @Override
            public void showError(Throwable throwable) {
                logger.warn("showError() " + throwable.getMessage());
            }

            @Override
            public void showLightDetails() {
                logger.warn("showLightDetails()");
                logger.warn(light.toString());
            }

            @Override
            public void lightChanged(Light light) {
                this.light = light;

                showLightDetails();
            }
        };
    }

    @After
    public void tearDown() throws Exception { }

    @Test
    public void testSetHue() throws Exception {
        presenter.setHue(TestConstants.TEST_HUE_INT, TestConstants.TEST_DURATION);

       // presenter.handleLightDetails(new LightNetwork.LightDetailsEvent(new Light(TestConstants.TEST_ID)));
    }

    @Test
    public void testSetSaturation() throws Exception {
        presenter.setSaturation(TestConstants.TEST_SATURATION_INT, TestConstants.TEST_DURATION);

       // presenter.handleLightDetails(new LightNetwork.LightDetailsEvent(new Light(TestConstants.TEST_ID)));
    }

    @Test
    public void testSetBrightness() throws Exception {
        presenter.setBrightness(TestConstants.TEST_BRIGHTNESS_INT, TestConstants.TEST_DURATION);

       // presenter.handleLightDetails(new LightNetwork.LightDetailsEvent(new Light(TestConstants.TEST_ID)));
    }

    @Test
    public void testSetKelvin() throws Exception {
        presenter.setKelvin(TestConstants.TEST_KELVIN, TestConstants.TEST_DURATION);

        //presenter.handleLightDetails(new LightNetwork.LightDetailsEvent(new Light(TestConstants.TEST_ID)));
    }

    @Test
    public void testSetPower() throws Exception {
        presenter.setPower(ModelConstants.Power.OFF, TestConstants.TEST_DURATION);

        //presenter.handleLightDetails(new LightNetwork.LightDetailsEvent(new Light(TestConstants.TEST_ID)));
    }
}