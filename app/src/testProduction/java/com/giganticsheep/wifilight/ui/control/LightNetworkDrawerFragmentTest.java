package com.giganticsheep.wifilight.ui.control;

import android.view.View;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.ui.UITestBase;
import com.giganticsheep.wifilight.ui.control.network.LightNetworkDrawerFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.SupportFragmentTestUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 26/07/15. <p>
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class LightNetworkDrawerFragmentTest extends UITestBase {

    private LightNetworkDrawerFragment fragment;

    @Before
    public void setUp() {
        if (BuildConfig.DEBUG) {
            return;
        }

        try {
            fragment = (LightNetworkDrawerFragment) fragmentFactory.createFragment("Drawer");
        } catch(Exception e) {
            Assert.fail("Drawer fragment does not exist");
        }

        SupportFragmentTestUtil.startFragment(fragment, LightControlActivity.class);
    }

    @Test
    public void testShowLoading() {
        if(BuildConfig.DEBUG) {
            return;
        }

        Assert.assertThat(fragment.getView(), not(nullValue()));

        fragment.showLoading();

        View loadingView = fragment.getView().findViewById(R.id.loading_layout);
        View errorView = fragment.getView().findViewById(R.id.error_layout);
        View mainView = fragment.getView().findViewById(R.id.drawer_layout);

        assertThat(loadingView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(errorView.getVisibility(), equalTo(View.GONE));
        assertThat(mainView.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void testShowLightNetwork() {
        if(BuildConfig.DEBUG) {
            return;
        }

        fragment.showLightNetwork(testLightNetwork, 0, 0, 0);

        View loadingView = fragment.getView().findViewById(R.id.loading_layout);
        View errorView = fragment.getView().findViewById(R.id.error_layout);
        View mainView = fragment.getView().findViewById(R.id.drawer_layout);

        assertThat(loadingView.getVisibility(), equalTo(View.GONE));
        assertThat(errorView.getVisibility(), equalTo(View.GONE));
        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testShowError() {
        if(BuildConfig.DEBUG) {
            return;
        }

        Assert.assertThat(fragment.getView(), not(nullValue()));

        fragment.showError();

        View loadingView = fragment.getView().findViewById(R.id.loading_layout);
        View errorView = fragment.getView().findViewById(R.id.error_layout);
        View mainView = fragment.getView().findViewById(R.id.drawer_layout);

        assertThat(loadingView.getVisibility(), equalTo(View.GONE));
        assertThat(errorView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(mainView.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void testShowErrorException() {
        if(BuildConfig.DEBUG) {
            return;
        }

        Assert.assertThat(fragment.getView(), not(nullValue()));

        fragment.showError(new Exception("Test"));

        View loadingView = fragment.getView().findViewById(R.id.loading_layout);
        View errorView = fragment.getView().findViewById(R.id.error_layout);
        View mainView = fragment.getView().findViewById(R.id.drawer_layout);

        assertThat(loadingView.getVisibility(), equalTo(View.GONE));
        assertThat(errorView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(mainView.getVisibility(), equalTo(View.GONE));
    }
}