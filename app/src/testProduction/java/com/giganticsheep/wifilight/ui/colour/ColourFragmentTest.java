package com.giganticsheep.wifilight.ui.control.colour;

import android.support.annotation.NonNull;
import android.widget.SeekBar;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.base.LightFragmentTestBase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class ColourFragmentTest extends LightFragmentTestBase {

    private SeekBar brightnessSeekbar;
    private SeekBar hueSeekbar;
    private SeekBar saturationSeekbar;

    @Before
    public void setUp() {
        super.setUp();

        if(BuildConfig.DEBUG) {
            return;
        }

        brightnessSeekbar = ((ColourFragment) fragment).brightnessSeekBar;
        hueSeekbar = ((ColourFragment) fragment).hueSeekBar;
        saturationSeekbar = ((ColourFragment) fragment).saturationSeekBar;
    }

    @NonNull
    @Override
    protected String getFragmentName() {
        return RuntimeEnvironment.application.getString(R.string.fragment_name_light_colour);
    }

    @Override
    protected void assertViewsEnabled(final boolean enabled) {
        //assertThat(brightnessSeekbar.isEnabled(), equalTo(enabled));
        //assertThat(hueSeekbar.isEnabled(), equalTo(enabled));
        //assertThat(saturationSeekbar.isEnabled(), equalTo(enabled));
    }

    @Test
    @Override
    public void testSetLightDetails() {
        super.testSetLightDetails();

        if(BuildConfig.DEBUG) {
            return;
        }

        assertThat(brightnessSeekbar.getProgress(), equalTo(LightConstants.convertBrightness(TestConstants.TEST_BRIGHTNESS_DOUBLE)));
        assertThat(hueSeekbar.getProgress(), equalTo(LightConstants.convertHue(TestConstants.TEST_HUE_DOUBLE)));
        assertThat(saturationSeekbar.getProgress(), equalTo(LightConstants.convertSaturation(TestConstants.TEST_SATURATION_DOUBLE)));
    }
}