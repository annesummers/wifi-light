package com.giganticsheep.wifilight.ui.fragment;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.MainActivity;
import com.giganticsheep.wifilight.ui.UITest;

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
public abstract class LightFragmentTest extends UITest {

    protected LightFragment fragment;

    @Before
    public void setUp() throws Exception {
        fragment = (LightFragment) fragmentFactory.createFragment(getFragmentName());

        SupportFragmentTestUtil.startFragment(fragment, MainActivity.class);
    }

    @Test
    public void testSetLightDetails() throws Exception {
        assertThat(fragment.getView(), not(nullValue()));

        Light testLight = new Light(TestConstants.TEST_ID);
        testLight.brightness = TestConstants.TEST_BRIGHTNESS_DOUBLE;
        testLight.color.hue = TestConstants.TEST_HUE_DOUBLE;
        testLight.color.kelvin = TestConstants.TEST_KELVIN;
        testLight.color.saturation = TestConstants.TEST_SATURATION_DOUBLE;

        fragment.lightChanged(testLight);
        fragment.setLightDetails();
    }

    protected abstract String getFragmentName();
}
