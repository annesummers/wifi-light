package com.giganticsheep.wifilight.ui.base;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.ui.UITestBase;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.giganticsheep.wifilight.ui.locations.LightNetworkViewState;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.util.SupportFragmentTestUtil;

import timber.log.Timber;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */
public abstract class LightFragmentTestBase extends UITestBase {

    protected LightFragmentBase fragment;
    protected TestPresenter presenter;

    @Before
    public void setUp() {
        if(BuildConfig.DEBUG) {
            return;
        }

        try {
            fragment = (LightFragmentBase) fragmentFactory.createFragment(getFragmentName());
        } catch (Exception e) {
            Assert.fail("Fragment " + getFragmentName() + "does not exist");
        }

        try {
            SupportFragmentTestUtil.startFragment(fragment, LightControlActivity.class);
        } catch(Exception e) {
            Timber.d(e.getMessage(), "Animation throws an exception under Robolectric");
        }

        presenter = new TestPresenter(fragment.getLightControlActivity().getComponent(), fragment);

        presenter.attachView(fragment.getMvpView());
        fragment.setPresenter(presenter);
    }

    protected void testSetLightDetails() {
        if(BuildConfig.DEBUG) {
            return;
        }

        assertThat(fragment.getView(), not(nullValue()));

        try {
            fragment.showLight(presenter.getLight());
        } catch(Exception e) {
            Timber.d(e.getMessage(), "Animation throws an exception under Robolectric");
        }
    }

    @Test
    public void testShowLoading() {
        if(BuildConfig.DEBUG) {
            return;
        }

        Assert.assertThat(fragment.getView(), not(nullValue()));

        fragment.showLoading();

        assertViewsEnabled(false);

        MatcherAssert.assertThat(fragment.getViewState().state(), equalTo(LightNetworkViewState.STATE_SHOW_LOADING));
    }

    @Test
    public void testShowConnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        MatcherAssert.assertThat(fragment.getView(), not(nullValue()));

        try {
            fragment.showConnected(presenter.getLight());
        } catch(Exception e) {
            Timber.d(e.getMessage(), "Animation throws an exception under Robolectric");
        }

        assertViewsEnabled(true);
//
       // MatcherAssert.assertThat(fragment.getViewState().state(), equalTo(LightStatusViewState.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testShowDisconnected() {
        if(BuildConfig.DEBUG) {
            return;
        }

        MatcherAssert.assertThat(fragment.getView(), not(nullValue()));

        try {
            fragment.showDisconnected(presenter.getLight());
        } catch(Exception e) {
            Timber.d(e.getMessage(), "Animation throws an exception under Robolectric");
        }

        assertViewsEnabled(false);

        //MatcherAssert.assertThat(fragment.getViewState().state(), equalTo(LightStatusViewState.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testShowConnecting() {
        if(BuildConfig.DEBUG) {
            return;
        }

        MatcherAssert.assertThat(fragment.getView(), not(nullValue()));

        try {
            fragment.showConnecting(presenter.getLight());
        } catch(Exception e) {
            Timber.d(e.getMessage(), "Animation throws an exception under Robolectric");
        }

        assertViewsEnabled(false);

        //MatcherAssert.assertThat(fragment.getViewState().state(), equalTo(LightStatusViewState.STATE_SHOW_LIGHT_CONNECTING));
    }

    @Test
    public void testShowError() {
        if(BuildConfig.DEBUG) {
            return;
        }

        MatcherAssert.assertThat(fragment.getView(), not(nullValue()));

        fragment.showError();

        assertViewsEnabled(false);

        MatcherAssert.assertThat(fragment.getViewState().state(), equalTo(LightNetworkViewState.STATE_SHOW_ERROR));
    }

    @Test
    public void testShowErrorException() {
        if(BuildConfig.DEBUG) {
            return;
        }

        MatcherAssert.assertThat(fragment.getView(), not(nullValue()));

        fragment.showError(new Exception("Test"));

        assertViewsEnabled(false);

        MatcherAssert.assertThat(fragment.getViewState().state(), equalTo(LightNetworkViewState.STATE_SHOW_ERROR));
    }

    protected abstract String getFragmentName();

    // TODO why are some of the views not coming back enabled?
    protected abstract void assertViewsEnabled(boolean enabled);
}
