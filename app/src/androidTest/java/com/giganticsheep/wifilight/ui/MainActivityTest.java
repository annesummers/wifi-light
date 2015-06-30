package com.giganticsheep.wifilight.ui;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import static org.junit.Assert.*;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2 {
    private MainActivity mainActivity;
    private Instrumentation instrumentation;

    public MainActivityTest(Class activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        instrumentation = getInstrumentation();
        mainActivity = (MainActivity) getActivity();
    }

    public void testLoadsViewPager() {

    }

    public void testLoadsLightDetails() {

    }

    public void testRefreshShown() {

    }

    public void testRefreshRefreshesData() {

    }
}