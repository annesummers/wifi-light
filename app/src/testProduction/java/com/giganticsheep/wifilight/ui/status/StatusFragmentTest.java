package com.giganticsheep.wifilight.ui.status;

import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
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
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class StatusFragmentTest extends LightFragmentTestBase {

    @NonNull
    @Override
    protected String getFragmentName() {
        return RuntimeEnvironment.application.getString(R.string.fragment_name_light_status);
    }

    @Test
    public void testSetLightDetails() {
        if(BuildConfig.DEBUG) {
            return;
        }

        super.testSetLightDetails();

        ToggleButton powerToggle = (ToggleButton) fragment.getView().findViewById(R.id.power_toggle);
        TextView statusTextView = (TextView) fragment.getView().findViewById(R.id.status_textview);

        assertThat(powerToggle.isChecked(), equalTo(TestConstants.TEST_POWER));
        assertThat(((String)statusTextView.getText()), equalTo(TestConstants.TEST_DISCONNECTED_STRING));
    }
}
