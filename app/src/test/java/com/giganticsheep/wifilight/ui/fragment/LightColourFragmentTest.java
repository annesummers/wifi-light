package com.giganticsheep.wifilight.ui.fragment;

import android.widget.SeekBar;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.network.LightResponse;
import com.giganticsheep.wifilight.base.TestConstants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class LightColourFragmentTest extends LightFragmentTestBase {

    @Override
    protected String getFragmentName() {
        return RuntimeEnvironment.application.getString(R.string.fragment_name_light_colour);
    }

    @Test
    public void testSetLightDetails() throws Exception {
        super.testSetLightDetails();

        SeekBar brightnessSeekbar = (SeekBar) fragment.getView().findViewById(R.id.brightness_seekbar);
        SeekBar hueSeekbar = (SeekBar) fragment.getView().findViewById(R.id.hue_seekbar);
        SeekBar kelvinSeekbar = (SeekBar) fragment.getView().findViewById(R.id.kelvin_seekbar);
        SeekBar saturationSeekbar = (SeekBar) fragment.getView().findViewById(R.id.saturation_seekbar);

        assertThat(brightnessSeekbar.getProgress(), equalTo(LightResponse.convertBrightness(TestConstants.TEST_BRIGHTNESS_DOUBLE)));
        assertThat(hueSeekbar.getProgress(), equalTo(LightResponse.convertHue(TestConstants.TEST_HUE_DOUBLE)));
        assertThat(kelvinSeekbar.getProgress(), equalTo(TestConstants.TEST_KELVIN - LightResponse.KELVIN_BASE));
        assertThat(saturationSeekbar.getProgress(), equalTo(LightResponse.convertSaturation(TestConstants.TEST_SATURATION_DOUBLE)));
    }
}