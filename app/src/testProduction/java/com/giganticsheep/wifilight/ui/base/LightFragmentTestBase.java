package com.giganticsheep.wifilight.ui.base;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.ui.UITestBase;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;

import org.junit.Assert;
import org.junit.Before;
import org.robolectric.util.SupportFragmentTestUtil;

import timber.log.Timber;

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

        SupportFragmentTestUtil.startFragment(fragment, LightControlActivity.class);

        presenter = new TestPresenter(fragment.getLightControlActivity().getComponent(), fragment);

        presenter.attachView(fragment.getMvpView());
        fragment.setPresenter(presenter);
    }

    public void testSetLightDetails() {
        assertThat(fragment.getView(), not(nullValue()));

        try {
            fragment.showLight(presenter.getLight());
        } catch(Exception e) {
            Timber.d(e.getMessage(), "Animation throws an exception under Robolectric");
        }
    }

    protected abstract String getFragmentName();
}
