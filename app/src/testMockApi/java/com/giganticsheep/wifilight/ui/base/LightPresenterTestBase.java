package com.giganticsheep.wifilight.ui.base;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.MockLightControlImpl;
import com.giganticsheep.wifilight.base.WifiLightTestBase;
import com.giganticsheep.wifilight.ui.ErrorEvent;
import com.giganticsheep.wifilight.ui.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.TestPresenterComponent;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.base.light.TestLightView;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public abstract class LightPresenterTestBase extends WifiLightTestBase {

    private TestPresenterComponent component;

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

        view = new TestLightView(this);
        presenter.attachView(view);
    }

    @Test
    public void testTestFetchLightConnected() {
        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTED));
    }

    @Test
    public void testTestFetchLightConnecting() {
        setTestStatus(LightControl.Status.OK);
        setLightTimeout();

        fetchLightAndHandleEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_CONNECTING));
    }

    @Test
    public void testTestFetchLightDisconnected() {
        setTestStatus(LightControl.Status.OFF);
        fetchLightAndHandleEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_LIGHT_DISCONNECTED));
    }

    @Test
    public void testTestFetchLightError() {
        setTestStatus(LightControl.Status.ERROR);
        presenter.fetchLight(Constants.TEST_ID);
        handleErrorEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    protected void setTestStatus(LightControl.Status status) {
        ((MockLightControlImpl)presenter.lightControl).setStatus(status);
    }

    protected void setLightTimeout() {
        ((MockLightControlImpl)presenter.lightControl).setLightTimeout(true);
    }

    protected void fetchLightAndHandleEvent() {
        presenter.fetchLight(Constants.TEST_ID);
        handleLightChangedEvent();
    }

    protected void handleLightChangedEvent() {
        LightChangedEvent event = getCheckedEvent(LightChangedEvent.class);

        presenter.handleLightChanged(event.getLight());
    }

    protected void handleErrorEvent() {
        ErrorEvent event = getCheckedEvent(ErrorEvent.class);

        presenter.handleError(event.getError());
    }

    @NonNull
    protected LightPresenterBase getPresenter() {
        return presenter;
    }

    protected abstract LightPresenterBase createPresenter(LightPresenterBase.Injector injector);
}