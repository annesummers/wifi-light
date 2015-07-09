package com.giganticsheep.wifilight.ui.fragment;

import android.widget.SeekBar;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.SupportFragmentTestUtil;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class LightColourFragmentTest {

    private LightColourFragment fragment;

    @Before
    public void setUp() throws Exception {
        fragment = LightColourFragment.newInstance(
                RuntimeEnvironment.application.getString(R.string.fragment_name_light_colour));

        SupportFragmentTestUtil.startFragment(fragment, MainActivity.class);
    }

    @After
    public void tearDown() throws Exception {

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

        SeekBar brightnessSeekbar = (SeekBar) fragment.getView().findViewById(R.id.brightness_seekbar);
        SeekBar hueSeekbar = (SeekBar) fragment.getView().findViewById(R.id.hue_seekbar);
        SeekBar kelvinSeekbar = (SeekBar) fragment.getView().findViewById(R.id.kelvin_seekbar);
        SeekBar saturationSeekbar = (SeekBar) fragment.getView().findViewById(R.id.saturation_seekbar);

        assertThat(brightnessSeekbar.getProgress(), equalTo(Light.convertBrightness(TestConstants.TEST_BRIGHTNESS_DOUBLE)));
        assertThat(hueSeekbar.getProgress(), equalTo(Light.convertHue(TestConstants.TEST_HUE_DOUBLE)));
        assertThat(kelvinSeekbar.getProgress(), equalTo(TestConstants.TEST_KELVIN - Light.KELVIN_BASE));
        assertThat(saturationSeekbar.getProgress(), equalTo(Light.convertSaturation(TestConstants.TEST_SATURATION_DOUBLE)));
    }
}