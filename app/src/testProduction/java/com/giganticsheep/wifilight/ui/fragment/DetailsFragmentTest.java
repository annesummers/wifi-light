package com.giganticsheep.wifilight.ui.fragment;

import android.support.annotation.NonNull;
import android.widget.TextView;

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
 * Created by anne on 30/06/15.
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class DetailsFragmentTest extends LightFragmentTestBase {

    @NonNull
    @Override
    protected String getFragmentName() {
        return RuntimeEnvironment.application.getString(R.string.fragment_name_light_details);
    }

    @Test
    public void testSetLightDetails() {
        super.testSetLightDetails();

        TextView idTextView = (TextView) fragment.getView().findViewById(R.id.id_textview);
        TextView labelTextView = (TextView) fragment.getView().findViewById(R.id.name_textview);

        TextView brightnessTextView = (TextView) fragment.getView().findViewById(R.id.brightness_textview);
        TextView hueTextView = (TextView) fragment.getView().findViewById(R.id.hue_textview);
        TextView kelvinTextView = (TextView) fragment.getView().findViewById(R.id.kelvin_textview);
        TextView saturationTextView = (TextView) fragment.getView().findViewById(R.id.saturation_textview);

        assertThat((String)(idTextView.getText()), equalTo(TestConstants.TEST_ID));
        assertThat((String)(labelTextView.getText()), equalTo(TestConstants.TEST_LABEL));

        assertThat((String)(brightnessTextView.getText()), equalTo(Integer.toString(LightConstants.convertBrightness(TestConstants.TEST_BRIGHTNESS_DOUBLE))));
        assertThat((String)(hueTextView.getText()), equalTo(Integer.toString(LightConstants.convertHue(TestConstants.TEST_HUE_DOUBLE))));
        assertThat((String)(kelvinTextView.getText()), equalTo(Integer.toString(TestConstants.TEST_KELVIN)));
        assertThat((String)(saturationTextView.getText()), equalTo(Integer.toString(LightConstants.convertSaturation(TestConstants.TEST_SATURATION_DOUBLE))));
    }
}