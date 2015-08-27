package com.giganticsheep.wifilight.ui.control;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.network.test.MockLight;
import com.giganticsheep.wifilight.ui.colour.ColourFragment;
import com.giganticsheep.wifilight.ui.details.DetailsFragment;
import com.giganticsheep.wifilight.ui.status.StatusFragment;
import com.giganticsheep.wifilight.ui.white.WhiteFragment;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import timber.log.Timber;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;


/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class LightControlActivityTest {

    private ActivityController<LightControlActivity> activityController;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(LightControlActivity.class);
    }

    @After
    public void tearDown() { }

    @Test
    public void testCreateNotNull() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = activityController
                .create()
                .postCreate(null)
                .start()
                .resume()
                .visible()
                .get();
        assertThat(activity, not(nullValue()));
    }

    @Test
    public void testRecreateNotNull() {
        if(BuildConfig.DEBUG) {
            return;
        }

        Bundle savedState = new Bundle();
        activityController
                .create()
                .postCreate(savedState)
                .start()
                .resume()
                .pause()
                .stop()
                .saveInstanceState(savedState)
                .destroy();

        activityController = Robolectric.buildActivity(LightControlActivity.class);
        LightControlActivity activity = activityController
                .create(savedState)
                .postCreate(savedState)
                .start()
                .resume()
                .visible()
                .get();

        assertThat(activity, not(nullValue()));
    }

    @Test
    public void testLoadsViewPager() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        FragmentManager fragmentManager  = activity.getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);
        assertThat(fragment, instanceOf(WhiteFragment.class));

        Fragment fragment2 = fragmentManager.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
        assertThat(fragment2, instanceOf(ColourFragment.class));
    }

    @Test
    public void testLoadsTabs() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        ViewGroup tabs = (ViewGroup) activityController.get().findViewById(R.id.sliding_tabs);

        assertThat(tabs, not(nullValue()));
    }

    @Test
    public void testConfigurationChanged() {
        if(BuildConfig.DEBUG) {
            return;
        }

        Bundle savedState = new Bundle();
        activityController
                .create()
                .postCreate(savedState)
                .start()
                .resume();

        LightControlActivity activity = activityController.get();

        activity.showConnected(new MockLight(Constants.TEST_ID, Constants.TEST_LABEL));

        Configuration newConfig = new Configuration();
        newConfig.orientation = Configuration.ORIENTATION_LANDSCAPE;

        try {
            activity.onConfigurationChanged(newConfig);
        } catch (Exception e) {
            Timber.e(e, "Exception thrown");
        }

        View loadingView = activity.findViewById(R.id.loading_layout);
        View errorView = activity.findViewById(R.id.error_layout);
        View mainView = activity.findViewById(R.id.light_layout);
        View disconnectedView = activity.findViewById(R.id.disconnected_layout);

        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(errorView.getVisibility(), equalTo(View.GONE));
        assertThat(loadingView.getVisibility(), equalTo(View.GONE));
        assertThat(disconnectedView.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void testShowsLightStatusFragment() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        FragmentManager fragmentManager  = activity.getSupportFragmentManager();

        assertThat(fragmentManager.findFragmentById(R.id.container), instanceOf(StatusFragment.class));
    }

    @Test
    public void testShowsLightDetailsFragment() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        FragmentManager fragmentManager  = activity.getSupportFragmentManager();

        assertThat(fragmentManager.findFragmentById(R.id.container2), instanceOf(DetailsFragment.class));
    }

    @Test
    public void testShowsLoadingView() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        activity.showLoading();

        View loadingView = activity.findViewById(R.id.loading_layout);
        View errorView = activity.findViewById(R.id.error_layout);
        View mainView = activity.findViewById(R.id.light_layout);
        View disconnectedView = activity.findViewById(R.id.disconnected_layout);

        assertThat(loadingView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(errorView.getVisibility(), equalTo(View.GONE));
        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(disconnectedView.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void testShowsErrorView() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        activity.showError();

        View loadingView = activity.findViewById(R.id.loading_layout);
        View errorView = activity.findViewById(R.id.error_layout);
        View mainView = activity.findViewById(R.id.light_layout);
        View disconnectedView = activity.findViewById(R.id.disconnected_layout);

        assertThat(errorView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(loadingView.getVisibility(), equalTo(View.GONE));
        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(disconnectedView.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void testShowsConnectedView() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        activity.showConnected(new MockLight(Constants.TEST_ID, Constants.TEST_LABEL));

        View loadingView = activity.findViewById(R.id.loading_layout);
        View errorView = activity.findViewById(R.id.error_layout);
        View mainView = activity.findViewById(R.id.light_layout);
        View disconnectedView = activity.findViewById(R.id.disconnected_layout);

        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(errorView.getVisibility(), equalTo(View.GONE));
        assertThat(loadingView.getVisibility(), equalTo(View.GONE));
        assertThat(disconnectedView.getVisibility(), equalTo(View.GONE));
    }

    @Test
    public void testShowsConnectingView() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        activity.showConnecting(new MockLight(Constants.TEST_ID, Constants.TEST_LABEL));

        View loadingView = activity.findViewById(R.id.loading_layout);
        View errorView = activity.findViewById(R.id.error_layout);
        View mainView = activity.findViewById(R.id.light_layout);
        View disconnectedView = activity.findViewById(R.id.disconnected_layout);

        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(errorView.getVisibility(), equalTo(View.GONE));
        assertThat(loadingView.getVisibility(), equalTo(View.GONE));
        assertThat(disconnectedView.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testShowsDisconnectedView() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        activity.showDisconnected(new MockLight(Constants.TEST_ID, Constants.TEST_LABEL));

        View loadingView = activity.findViewById(R.id.loading_layout);
        View errorView = activity.findViewById(R.id.error_layout);
        View mainView = activity.findViewById(R.id.light_layout);
        View disconnectedView = activity.findViewById(R.id.disconnected_layout);

        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(errorView.getVisibility(), equalTo(View.GONE));
        assertThat(loadingView.getVisibility(), equalTo(View.GONE));
        assertThat(disconnectedView.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testRefreshShown() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        View refreshButton = activity.findViewById(R.id.action_refresh);

        assertThat(refreshButton, not(nullValue()));
        assertThat(refreshButton.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testRefreshRefreshesData() {
        if(BuildConfig.DEBUG) {
            return;
        }

        LightControlActivity activity = createAndGetActivity();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);

        shadowActivity.clickMenuItem(R.id.action_refresh);

        View loadingView = activity.findViewById(R.id.loading_layout);
        View errorView = activity.findViewById(R.id.error_layout);
        View mainView = activity.findViewById(R.id.light_layout);

        assertThat(loadingView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(errorView.getVisibility(), equalTo(View.GONE));
        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
    }

    private LightControlActivity createAndGetActivity() {
        activityController
                .create()
                .postCreate(null)
                .visible();

        return activityController.get();
    }
}