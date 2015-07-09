package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.ui.fragment.LightColourFragment;
import com.giganticsheep.wifilight.ui.fragment.LightDetailsFragment;
import com.giganticsheep.wifilight.ui.fragment.LightEffectsFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

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
public class MainActivityTest {

    private ActivityController<MainActivity> activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(MainActivity.class);
    }

    @After
    public void tearDown() throws Exception { }

    @Test
    public void test_create_notNull() {
        MainActivity activity = activityController
                .create()
                .start()
                .resume()
                .visible()
                .get();
        assertThat(activity, not(nullValue()));
    }

    @Test
    public void test_recreate_notNull() {
        Bundle savedState = new Bundle();
        activityController
                .create()
                .start()
                .resume();

        activityController
                .pause()
                .stop()
                .saveInstanceState(savedState)
                .destroy();

        activityController = Robolectric.buildActivity(MainActivity.class);
        MainActivity activity = activityController
                .create(savedState)
                .start()
                .resume()
                .visible()
                .get();

        assertThat(activity, not(nullValue()));
    }

    @Test
    public void testLoadsViewPager() {
        // TODO how to test the viewpager; at the moment find view by id just returns the last
        // view attached to the pager
        activityController
                .create()
                .visible();

        FragmentManager fragmentManager  = activityController.get().getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 0);
        assertThat(fragment, instanceOf(LightColourFragment.class));

        Fragment fragment2 = fragmentManager.findFragmentByTag("android:switcher:" + R.id.pager + ":" + 1);
        assertThat(fragment2, instanceOf(LightEffectsFragment.class));

        ViewGroup tabs = (ViewGroup) activityController.get().findViewById(R.id.sliding_tabs);

        assertThat(tabs, not(nullValue()));

      /*  View firstTab = tabs.getChildAt(0);
        firstTab.performClick();

        assertThat(fragment.getView().getVisibility(), equalTo(View.VISIBLE));
        assertThat(fragment2.getView().getVisibility(), equalTo(View.INVISIBLE));

        View secondTab = tabs.getChildAt(1);
        secondTab.performClick();

        assertThat(fragment.getView().getVisibility(), equalTo(View.INVISIBLE));
        assertThat(fragment2.getView().getVisibility(), equalTo(View.VISIBLE));*/
    }

    @Test
    public void testLoadsLightDetails() {
        activityController
            .create()
            .visible();

        FragmentManager fragmentManager  = activityController.get().getSupportFragmentManager();

        assertThat(fragmentManager.findFragmentById(R.id.container), instanceOf(LightDetailsFragment.class));
    }

    @Test
    public void testShowsLoadingView() {
        activityController
                .create()
                .visible();

        MainActivity activity = activityController.get();

        activity.showLoadingView();

        View loadingView = activity.findViewById(R.id.loading_view);
        View errorView = activity.findViewById(R.id.error_view);
        View mainView = activity.findViewById(R.id.light_layout);

        assertThat(loadingView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(errorView.getVisibility(), equalTo(View.INVISIBLE));
        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void testShowsErrorView() {
        activityController
                .create()
                .visible();

        MainActivity activity = activityController.get();

        activity.showErrorView();

        View loadingView = activity.findViewById(R.id.loading_view);
        View errorView = activity.findViewById(R.id.error_view);
        View mainView = activity.findViewById(R.id.light_layout);

        assertThat(errorView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(loadingView.getVisibility(), equalTo(View.INVISIBLE));
        assertThat(mainView.getVisibility(), equalTo(View.INVISIBLE));
    }

    @Test
    public void testShowsLightView() {
        activityController
                .create()
                .visible();

        MainActivity activity = activityController.get();

        activity.showLightView();

        View loadingView = activity.findViewById(R.id.loading_view);
        View errorView = activity.findViewById(R.id.error_view);
        View mainView = activity.findViewById(R.id.light_layout);

        assertThat(mainView.getVisibility(), equalTo(View.VISIBLE));
        assertThat(errorView.getVisibility(), equalTo(View.INVISIBLE));
        assertThat(loadingView.getVisibility(), equalTo(View.INVISIBLE));
    }

    public void testRefreshShown() {
    }

    public void testRefreshRefreshesData() {

    }
}