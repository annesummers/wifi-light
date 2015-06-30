package com.giganticsheep.wifilight.ui;

import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.view.ViewGroup;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.ui.fragment.LightColourFragment;
import com.giganticsheep.wifilight.ui.fragment.LightEffectsFragment;
import com.robotium.solo.Solo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;


/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;
    private MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testLoadsViewPager() {
        solo.assertCurrentActivity("wrongActivity", MainActivity.class);

        activity = (MainActivity)solo.getCurrentActivity();
        FragmentManager fragmentManager  = activity.getSupportFragmentManager();

        assertThat(fragmentManager.findFragmentById(R.id.pager), instanceOf(LightColourFragment.class));

        ViewGroup tabs = (ViewGroup) solo.getView(R.id.sliding_tabs);

        View secondTab = tabs.getChildAt(1);
        solo.clickOnView(secondTab);

        assertThat(fragmentManager.findFragmentById(R.id.pager), instanceOf(LightEffectsFragment.class));

        View firstTab = tabs.getChildAt(0);
        solo.clickOnView(firstTab);

        assertThat(fragmentManager.findFragmentById(R.id.pager), instanceOf(LightColourFragment.class));

    }

    public void testLoadsLightDetails() {

    }

    public void testRefreshShown() {

    }

    public void testRefreshRefreshesData() {

    }

    public void testShowsLoadingView() {

    }

    public void testShowsErrorView() {

    }

    public void testShowsLightView() {

    }
}