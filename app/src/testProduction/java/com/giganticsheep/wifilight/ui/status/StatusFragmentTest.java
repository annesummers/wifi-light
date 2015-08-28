package com.giganticsheep.wifilight.ui.status;

import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
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
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class StatusFragmentTest extends LightFragmentTestBase {

    private ToggleButton powerToggle;
    private TextView statusTextView;

    @Before
    public void setUp() {
        super.setUp();

        if(BuildConfig.DEBUG) {
            return;
        }

        powerToggle = ((StatusFragment)fragment).powerToggle;
        statusTextView = ((StatusFragment)fragment).statusTextView;
    }

    @NonNull
    @Override
    protected String getFragmentName() {
        return RuntimeEnvironment.application.getString(R.string.fragment_name_light_status);
    }

    @Override
    protected void assertViewsEnabled(final boolean enabled) {
        //assertThat(powerToggle.isEnabled(), equalTo(enabled));
    }

    @Test
    @Override
    public void testSetLightDetails() {
        super.testSetLightDetails();

        if(BuildConfig.DEBUG) {
            return;
        }

        assertThat(powerToggle.isChecked(), equalTo(TestConstants.TEST_POWER));
        assertThat(statusTextView.getText(), equalTo(TestConstants.TEST_CONNECTED_STRING));
    }
}
