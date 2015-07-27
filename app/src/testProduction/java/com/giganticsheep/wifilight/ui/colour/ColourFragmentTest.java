package com.giganticsheep.wifilight.ui.colour;

import android.support.annotation.NonNull;
import android.widget.SeekBar;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.base.LightFragmentTestBase;

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

    @NonNull
    @Override
    protected String getFragmentName() {
        return RuntimeEnvironment.application.getString(R.string.fragment_name_light_colour);
    }

    @Test
    public void testSetLightDetails() {
        if(BuildConfig.DEBUG) {
            return;
        }

        super.testSetLightDetails();

        SeekBar brightnessSeekbar = (SeekBar) fragment.getView().findViewById(R.id.brightness_seekbar);
        SeekBar hueSeekbar = (SeekBar) fragment.getView().findViewById(R.id.hue_seekbar);
        SeekBar saturationSeekbar = (SeekBar) fragment.getView().findViewById(R.id.saturation_seekbar);

        assertThat(brightnessSeekbar.getProgress(), equalTo(LightConstants.convertBrightness(TestConstants.TEST_BRIGHTNESS_DOUBLE)));
        assertThat(hueSeekbar.getProgress(), equalTo(LightConstants.convertHue(TestConstants.TEST_HUE_DOUBLE)));
        assertThat(saturationSeekbar.getProgress(), equalTo(LightConstants.convertSaturation(TestConstants.TEST_SATURATION_DOUBLE)));
    }
}