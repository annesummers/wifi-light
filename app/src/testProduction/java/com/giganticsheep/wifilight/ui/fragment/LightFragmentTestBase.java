package com.giganticsheep.wifilight.ui.fragment;

import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.mvp.presenter.TestPresenter;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.giganticsheep.wifilight.ui.UITestBase;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.util.SupportFragmentTestUtil;

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
    public void setUp() throws Exception {
        fragment = (LightFragmentBase) fragmentFactory.createFragment(getFragmentName());
        presenter = new TestPresenter(new LightPresenterBase.Injector() {
            @Override
            public void inject(LightPresenterBase lightPresenter) { }
        }, fragment);

        SupportFragmentTestUtil.startFragment(fragment, LightControlActivity.class);

        presenter.attachView(fragment.getMvpView());
        fragment.setPresenter(presenter);
    }

    @Test
    public void testSetLightDetails() throws Exception {
        assertThat(fragment.getView(), not(nullValue()));

        fragment.showLight(presenter.getLight());
    }

    protected abstract String getFragmentName();
}
