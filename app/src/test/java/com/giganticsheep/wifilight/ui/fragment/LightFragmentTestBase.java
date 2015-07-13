package com.giganticsheep.wifilight.ui.fragment;

import com.giganticsheep.wifilight.api.network.LightResponse;
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

        LightResponse testLight = new LightResponse(TestConstants.TEST_ID);
        testLight.brightness = TestConstants.TEST_BRIGHTNESS_DOUBLE;
        testLight.color.hue = TestConstants.TEST_HUE_DOUBLE;
        testLight.color.kelvin = TestConstants.TEST_KELVIN;
        testLight.color.saturation = TestConstants.TEST_SATURATION_DOUBLE;

        fragment.lightChanged(testLight);
        fragment.showLight();
    }

    protected abstract String getFragmentName();
}
