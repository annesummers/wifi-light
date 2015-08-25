package com.giganticsheep.wifilight.ui.base.light;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.test.MockLight;
import com.giganticsheep.wifilight.util.Constants;

import junit.framework.Assert;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightViewStateTest {

    @Test
    public void testShowLoading() {
        final AtomicBoolean loadingCalled = new AtomicBoolean(false);

        LightView view = new LightView() {
            @Override
            public void showLoading() {
                loadingCalled.set(true);
            }

            @Override
            public void showError(Throwable throwable) {
                Assert.fail("showError(Throwable) instead of showLoading()");
            }

            @Override
            public void showError() {
                Assert.fail("showError() instead of showLoading()");
            }

            @Override
            public void showConnected(Light light) {
                Assert.fail("showConnected() instead of showLoading()");
            }

            @Override
            public void showConnecting(Light light) {
                Assert.fail("showConnecting() instead of showLoading()");
            }

            @Override
            public void showDisconnected(Light light) {
                Assert.fail("showDisconnected() instead of showLoading()");
            }
        };

        LightViewState viewState = new LightViewState();
        viewState.setShowLoading();
        viewState.apply(view, false);

        assertTrue(loadingCalled.get());
    }

    @Test
    public void testShowError() {
        final AtomicBoolean errorCalled = new AtomicBoolean(false);

        LightView view = new LightView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showError()");
            }

            @Override
            public void showError(Throwable throwable) {
                Assert.fail("showError(Throwable) instead of showError()");
            }

            @Override
            public void showError() {
                errorCalled.set(true);
            }

            @Override
            public void showConnected(Light light) {
                Assert.fail("showConnected() instead of showError()");
            }

            @Override
            public void showConnecting(Light light) {
                Assert.fail("showConnecting() instead of showError()");
            }

            @Override
            public void showDisconnected(Light light) {
                Assert.fail("showDisconnected() instead of showError()");
            }
        };

        LightViewState viewState = new LightViewState();
        viewState.setShowError();
        viewState.apply(view, false);

        assertTrue(errorCalled.get());
    }

    @Test
    public void testShowErrorThrowable() {
        final AtomicBoolean errorCalled = new AtomicBoolean(false);

        LightView view = new LightView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showError()");
            }

            @Override
            public void showError() {
                Assert.fail("showError() instead of showError(Throwable)");
            }

            @Override
            public void showError(Throwable throwable) {
                errorCalled.set(true);
            }

            @Override
            public void showConnected(Light light) {
                Assert.fail("showConnected() instead of showError()");
            }

            @Override
            public void showConnecting(Light light) {
                Assert.fail("showConnecting() instead of showError()");
            }

            @Override
            public void showDisconnected(Light light) {
                Assert.fail("showDisconnected() instead of showError()");
            }
        };

        LightViewState viewState = new LightViewState();
        viewState.setShowError(new Throwable("Test"));
        viewState.apply(view, false);

        assertTrue(errorCalled.get());
    }

    @Test
    public void testShowConnected() {
        final AtomicBoolean connectedCalled = new AtomicBoolean(false);

        LightView view = new LightView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showConnected()");
            }

            @Override
            public void showError(Throwable throwable) {
                Assert.fail("showError(Throwable) instead of showConnected()");
            }

            @Override
            public void showError() {
                Assert.fail("showError() instead of showConnected()");
            }

            @Override
            public void showConnected(Light light) {
                connectedCalled.set(true);
                assertThat(light.getId(), equalTo(Constants.TEST_ID));
            }

            @Override
            public void showConnecting(Light light) {
                Assert.fail("showConnecting() instead of showConnected()");
            }

            @Override
            public void showDisconnected(Light light) {
                Assert.fail("showDisconnected() instead of showConnected()");
            }
        };

        LightViewState viewState = new LightViewState();
        viewState.setShowConnected(new MockLight(Constants.TEST_ID, Constants.TEST_LABEL));
        viewState.apply(view, false);

        assertTrue(connectedCalled.get());
    }

    @Test
    public void testShowConnecting() {
        final AtomicBoolean connectingCalled = new AtomicBoolean(false);

        LightView view = new LightView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showConnected()");
            }

            @Override
            public void showError(Throwable throwable) {
                Assert.fail("showError(Throwable) instead of showConnecting()");
            }

            @Override
            public void showError() {
                Assert.fail("showError() instead of showConnecting()");
            }

            @Override
            public void showConnected(Light light) {
                Assert.fail("showConnected() instead of showConnecting()");
            }

            @Override
            public void showConnecting(Light light) {
                connectingCalled.set(true);
                assertThat(light.getId(), equalTo(Constants.TEST_ID));
            }

            @Override
            public void showDisconnected(Light light) {
                Assert.fail("showDisconnected() instead of showConnecting()");
            }
        };

        LightViewState viewState = new LightViewState();
        viewState.setShowConnecting(new MockLight(Constants.TEST_ID, Constants.TEST_LABEL));
        viewState.apply(view, false);

        assertTrue(connectingCalled.get());
    }

    @Test
    public void testShowDisconnected() {
        final AtomicBoolean disconnectedCalled = new AtomicBoolean(false);

        LightView view = new LightView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showDisconnected()");
            }

            @Override
            public void showError(Throwable throwable) {
                Assert.fail("showError(Throwable) instead of showDisconnected()");
            }

            @Override
            public void showError() {
                Assert.fail("showError() instead of showDisconnected()");
            }

            @Override
            public void showConnected(Light light) {
                Assert.fail("showConnected() instead of showDisconnected()");
            }

            @Override
            public void showConnecting(Light light) {
                Assert.fail("showConnecting() instead of showDisconnected()");
            }

            @Override
            public void showDisconnected(Light light) {
                disconnectedCalled.set(true);
                assertThat(light.getId(), equalTo(Constants.TEST_ID));
            }
        };

        LightViewState viewState = new LightViewState();
        viewState.setShowDisconnected(new MockLight(Constants.TEST_ID, Constants.TEST_LABEL));
        viewState.apply(view, false);

        assertTrue(disconnectedCalled.get());
    }
}
