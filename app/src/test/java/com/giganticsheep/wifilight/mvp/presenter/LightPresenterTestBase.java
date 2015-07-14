package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.base.WifiLightTestBase;
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
    // TODO test the presenters with a simple mock network service

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
        protected final int STATE_SHOW_LIGHT_CONNECTED = 1;
        protected final int STATE_SHOW_LIGHT_DISCONNECTED = 2;
        protected final int STATE_SHOW_ERROR = 3;

        private int state = STATE_SHOW_LOADING;
        private Light light;

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
        public void showConnected() {
            state = STATE_SHOW_LIGHT_CONNECTED;

            logger.warn("showConnected()");
            logger.warn(light.id());
        }

        @Override
        public void showDisconnected() {
            state = STATE_SHOW_LIGHT_DISCONNECTED;

            logger.warn("showDisconnected()");
            logger.warn(light.id());
        }

        @Override
        public void setLight(Light light) {
            this.light = light;
        }

        public Light getLight() {
            return light;
        }

        public int getState() {
            return state;
        }
    }

}
