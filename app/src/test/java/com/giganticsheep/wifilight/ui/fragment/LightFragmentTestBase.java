package com.giganticsheep.wifilight.ui.fragment;

import com.giganticsheep.wifilight.TestLightResponse;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.giganticsheep.wifilight.ui.UITestBase;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.util.SupportFragmentTestUtil;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */
public abstract class LightFragmentTestBase extends UITestBase {

    protected LightFragmentBase fragment;

    @Before
    public void setUp() throws Exception {
        fragment = (LightFragmentBase) fragmentFactory.createFragment(getFragmentName());

        SupportFragmentTestUtil.startFragment(fragment, LightControlActivity.class);
    }

    @Test
    public void testSetLightDetails() throws Exception {
        assertThat(fragment.getView(), not(nullValue()));

        TestLightResponse light = new TestLightResponse(TestConstants.TEST_ID);
        light.brightness = TestConstants.TEST_BRIGHTNESS_DOUBLE;
        light.color.hue = TestConstants.TEST_HUE_DOUBLE;
        light.color.kelvin = TestConstants.TEST_KELVIN;
        light.color.saturation = TestConstants.TEST_SATURATION_DOUBLE;

        fragment.setLight(light);
        fragment.showLight();
    }

    protected abstract String getFragmentName();
}
