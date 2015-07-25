package com.giganticsheep.wifilight.mvp.view;

import com.giganticsheep.wifilight.mvp.presenter.LightNetworkPresenter;

import junit.framework.Assert;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static junit.framework.Assert.assertTrue;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetworkViewStateTest {

    @Test
    public void testShowLoading() {
        final AtomicBoolean loadingCalled = new AtomicBoolean(false);

        LightNetworkView view = new LightNetworkView() {
            @Override
            public void showLoading() {
                loadingCalled.set(true);
            }

            @Override
            public void showLightNetwork(LightNetworkPresenter.LightNetwork lightNetwork) {
                Assert.fail("showLightNetwork() instead of showLoading()");
            }

            @Override
            public void showError() {
                Assert.fail("showError() instead of showLoading()");
            }

            @Override
            public void showError(Throwable throwable) {
                Assert.fail("showError(Throwable) instead of showLoading()");
            }
        };

        LightNetworkViewState viewState = new LightNetworkViewState();
        viewState.setShowLoading();
        viewState.apply(view, false);

        assertTrue(loadingCalled.get());
    }

    @Test
    public void testShowError() {
        final AtomicBoolean errorCalled = new AtomicBoolean(false);

        LightNetworkView view = new LightNetworkView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showError()");
            }

            @Override
            public void showLightNetwork(LightNetworkPresenter.LightNetwork lightNetwork) {
                Assert.fail("showLightNetwork() instead of showError()");
            }

            @Override
            public void showError() {
                errorCalled.set(true);
            }

            @Override
            public void showError(Throwable throwable) {
                Assert.fail("showError(Throwable) instead of showError()");
            }
        };

        LightNetworkViewState viewState = new LightNetworkViewState();
        viewState.setShowError();
        viewState.apply(view, false);

        assertTrue(errorCalled.get());
    }

    @Test
    public void testShowErrorThrowable() {
        final AtomicBoolean errorCalled = new AtomicBoolean(false);

        LightNetworkView view = new LightNetworkView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showError(Throwable)");
            }

            @Override
            public void showLightNetwork(LightNetworkPresenter.LightNetwork lightNetwork) {
                Assert.fail("showLightNetwork() instead of showError(Throwable)");
            }

            @Override
            public void showError() {
                Assert.fail("showError() instead of showError(Throwable)");
            }

            @Override
            public void showError(Throwable throwable) {
                errorCalled.set(true);
            }
        };

        LightNetworkViewState viewState = new LightNetworkViewState();
        viewState.setShowError(new Throwable("Test"));
        viewState.apply(view, false);

        assertTrue(errorCalled.get());
    }

    @Test
    public void testShowLightNetwork() {
        final AtomicBoolean showNetworkCalled = new AtomicBoolean(false);

        LightNetworkView view = new LightNetworkView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showLightNetwork()");
            }

            @Override
            public void showLightNetwork(LightNetworkPresenter.LightNetwork lightNetwork) {
                showNetworkCalled.set(true);
            }

            @Override
            public void showError() {
                Assert.fail("showError() instead of showLightNetwork()");
            }

            @Override
            public void showError(Throwable throwable) {
                Assert.fail("showError(Throwable) instead of showLightNetwork()");
            }
        };

        LightNetworkViewState viewState = new LightNetworkViewState();
        viewState.setShowLightNetwork();
        viewState.apply(view, false);

        assertTrue(showNetworkCalled.get());
    }
}
