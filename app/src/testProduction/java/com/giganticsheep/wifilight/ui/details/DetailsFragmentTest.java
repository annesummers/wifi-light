package com.giganticsheep.wifilight.ui.details;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.ui.base.LightFragmentTestBase;
import com.giganticsheep.wifilight.util.Constants;

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
public class DetailsFragmentTest extends LightFragmentTestBase {

    private TextView idTextView;
    private TextView labelTextView;
    private TextView brightnessTextView;
    private TextView hueTextView;
    private TextView kelvinTextView;
    private TextView saturationTextView;

    @Before
    public void setUp() {
        super.setUp();

        if(BuildConfig.DEBUG) {
            return;
        }

        idTextView = ((DetailsFragment)fragment).idTextView;

        labelTextView = ((DetailsFragment)fragment).nameTextView;

        brightnessTextView = ((DetailsFragment)fragment).brightnessTextView;
        hueTextView = ((DetailsFragment)fragment).hueTextView;
        kelvinTextView = ((DetailsFragment)fragment).kelvinTextView;
        saturationTextView = ((DetailsFragment)fragment).saturationTextView;
    }

    @NonNull
    @Override
    protected String getFragmentName() {
        return RuntimeEnvironment.application.getString(R.string.fragment_name_light_details);
    }

    @Override
    protected void assertViewsEnabled(final boolean enabled) {
        // views are read only so there is nothing to check
    }

    @Test
    @Override
    public void testSetLightDetails() {
        super.testSetLightDetails();

        if(BuildConfig.DEBUG) {
            return;
        }

        assertThat(idTextView.getText(), equalTo(Constants.TEST_ID));
        assertThat(labelTextView.getText(), equalTo(Constants.TEST_LABEL));

        assertThat(brightnessTextView.getText(), equalTo(Integer.toString(LightConstants.convertBrightness(TestConstants.TEST_BRIGHTNESS_DOUBLE))));
        assertThat(hueTextView.getText(), equalTo(Integer.toString(LightConstants.convertHue(TestConstants.TEST_HUE_DOUBLE))));
        assertThat(kelvinTextView.getText(), equalTo(Integer.toString(TestConstants.TEST_KELVIN)));
        assertThat(saturationTextView.getText(), equalTo(Integer.toString(LightConstants.convertSaturation(TestConstants.TEST_SATURATION_DOUBLE))));
    }
}