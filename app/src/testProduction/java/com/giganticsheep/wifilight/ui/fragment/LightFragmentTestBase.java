package com.giganticsheep.wifilight.ui.fragment;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.TestLightResponse;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
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

        fragment.showLight();
    }

    protected abstract String getFragmentName();

    private class TestPresenter extends LightPresenterBase {

        private final TestLightResponse light = new TestLightResponse(TestConstants.TEST_ID);
        private final LightFragmentBase fragment;

        /**
         * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
         *
         * @param injector an Injector used to inject this object into a Component that will
         *                 provide the injected class members.
         */
        protected TestPresenter(@NonNull Injector injector, LightFragmentBase fragment) {
            super(injector);

            this.fragment = fragment;

            light.brightness = TestConstants.TEST_BRIGHTNESS_DOUBLE;
            light.color.hue = TestConstants.TEST_HUE_DOUBLE;
            light.color.kelvin = TestConstants.TEST_KELVIN;
            light.color.saturation = TestConstants.TEST_SATURATION_DOUBLE;
        }

        @Override
        public void fetchLight(final String id) {
            if(light.id().equals(id)) {
                fragment.getPresenter().handleLightChanged(light);
            }
        }

        @Override
        public Light getLight() {
            return light;
        }
    }
}
