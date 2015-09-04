package com.giganticsheep.wifilight.ui.control.network;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.model.LightNetwork;

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
        if(BuildConfig.DEBUG) {
            return;
        }

        final AtomicBoolean loadingCalled = new AtomicBoolean(false);

        LightNetworkView view = new LightNetworkView() {
            @Override
            public void showLoading() {
                loadingCalled.set(true);
            }

            @Override
            public void showLightNetwork(LightNetwork lightNetwork,
                                         int locationPosition,
                                         int groupPosition,
                                         int lightPosition) {
                Assert.fail("showLocation() instead of showLoading()");
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
        if(BuildConfig.DEBUG) {
            return;
        }

        final AtomicBoolean errorCalled = new AtomicBoolean(false);

        LightNetworkView view = new LightNetworkView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showError()");
            }

            @Override
            public void showLightNetwork(LightNetwork lightNetwork,
                                         int locationPosition,
                                         int groupPosition,
                                         int lightPosition) {
                Assert.fail("showLocation() instead of showError()");
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
        if(BuildConfig.DEBUG) {
            return;
        }

        final AtomicBoolean errorCalled = new AtomicBoolean(false);

        LightNetworkView view = new LightNetworkView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showError(Throwable)");
            }

            @Override
            public void showLightNetwork(LightNetwork lightNetwork,
                                         int locationPosition,
                                         int groupPosition,
                                         int lightPosition) {
                Assert.fail("showLocation() instead of showError(Throwable)");
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
        if(BuildConfig.DEBUG) {
            return;
        }

        final AtomicBoolean showNetworkCalled = new AtomicBoolean(false);

        LightNetworkView view = new LightNetworkView() {
            @Override
            public void showLoading() {
                Assert.fail("showLoading() instead of showLocation()");
            }

            @Override
            public void showLightNetwork(LightNetwork lightNetwork,
                                         int locationPosition,
                                         int groupPosition,
                                         int lightPosition) {
                showNetworkCalled.set(true);
            }

            @Override
            public void showError() {
                Assert.fail("showError() instead of showLocation()");
            }

            @Override
            public void showError(Throwable throwable) {
                Assert.fail("showError(Throwable) instead of showLocation()");
            }
        };

        LightNetworkViewState viewState = new LightNetworkViewState();
        viewState.setShowLightNetwork(new LightNetwork(), 0, 0, 0);
        viewState.apply(view, false);

        assertTrue(showNetworkCalled.get());
    }
}
