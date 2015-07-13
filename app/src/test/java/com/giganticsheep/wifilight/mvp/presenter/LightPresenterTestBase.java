package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.api.network.LightResponse;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.base.WifiLightTestBase;
import com.giganticsheep.wifilight.mvp.presenter.dagger.TestPresenterComponent;
import com.giganticsheep.wifilight.mvp.view.LightView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public abstract class LightPresenterTestBase extends WifiLightTestBase {
    protected TestPresenterComponent component;

    @Override
    protected void createComponentAndInjectDependencies() {
        component = DaggerTestPresenterComponent.builder()
                .wifiLightTestsComponent(WifiLightTestsComponent.Initializer.init())
                .build();

        component.inject(this);
    }

    protected LightPresenterBase presenter;
    protected TestLightView view;

    @Before
    public void setUp() throws Exception {
        presenter = createPresenter(component);

        view = new TestLightView();
        presenter.attachView(view);
    }

    @After
    public void tearDown() throws Exception { }

    @Test
    public void testFetchLight() {
        presenter.fetchLight(TestConstants.TEST_ID);

        assertThat(view.getState(), equalTo(view.STATE_SHOW_LOADING));
    }

    @Test
    public void testFetchLightNullID() {
        presenter.fetchLight(null);

        assertThat(view.getState(), equalTo(view.STATE_SHOW_ERROR));
    }

    protected abstract LightPresenterBase createPresenter(LightPresenterBase.Injector injector);

    protected class TestLightView implements LightView {
        protected final int STATE_SHOW_LOADING = 0;
        protected final int STATE_SHOW_LIGHT_DETAILS = 1;
        protected final int STATE_SHOW_ERROR = 2;

        private int state = STATE_SHOW_LOADING;
        private LightResponse light;

        @Override
        public void showLoading() {
            state = STATE_SHOW_LOADING;

            logger.warn("showLoading()");
        }

        @Override
        public void showError() {
            state = STATE_SHOW_ERROR;

            logger.warn("showError()");
        }

        @Override
        public void showError(Throwable throwable) {
            state = STATE_SHOW_ERROR;

            logger.warn("showError() " + throwable.getMessage());
        }

        @Override
        public void showMainView() {
            state = STATE_SHOW_LIGHT_DETAILS;

            logger.warn("showMainView()");
            logger.warn(light.toString());
        }

        @Override
        public void lightChanged(LightResponse light) {
            this.light = light;

            showMainView();
        }

        public LightResponse getLight() {
            return light;
        }

        public int getState() {
            return state;
        }
    }
}
