package com.giganticsheep.wifilight.ui.fragment;

import android.support.annotation.NonNull;
import android.widget.SeekBar;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.base.TestConstants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class WhiteFragmentTest extends LightFragmentTestBase {

    @NonNull
    @Override
    protected String getFragmentName() {
        return RuntimeEnvironment.application.getString(R.string.fragment_name_light_white);
    }

    @Test
    public void testSetLightDetails() {
        super.testSetLightDetails();

        SeekBar brightnessSeekbar = (SeekBar) fragment.getView().findViewById(R.id.brightness_seekbar);
        SeekBar kelvinSeekbar = (SeekBar) fragment.getView().findViewById(R.id.kelvin_seekbar);

        assertThat(brightnessSeekbar.getProgress(), equalTo(LightConstants.convertBrightness(TestConstants.TEST_BRIGHTNESS_DOUBLE)));
        assertThat(kelvinSeekbar.getProgress(), equalTo(TestConstants.TEST_KELVIN - LightConstants.KELVIN_BASE));
    }
}
