package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.MockLightControlImpl;
import com.giganticsheep.wifilight.base.WifiLightTestBase;
import com.giganticsheep.wifilight.ui.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.TestLightNetworkView;
import com.giganticsheep.wifilight.ui.base.TestPresenterComponent;
import com.giganticsheep.wifilight.ui.base.DaggerTestPresenterComponent;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 26/07/15. <p>
 * (*_*)
 */
public class LightNetworkPresenterTest extends WifiLightTestBase {

    private TestPresenterComponent component;

    @Override
    protected void createComponentAndInjectDependencies() {
        component = DaggerTestPresenterComponent.builder()
                .wifiLightTestsComponent(WifiLightTestsComponent.Initializer.init())
                .build();

        component.inject(this);
    }

    private LightNetworkPresenter presenter;
    private TestLightNetworkView view;

    @Before
    public void setUp() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        presenter = new LightNetworkPresenter(component);

        view = new TestLightNetworkView(this);
        presenter.attachView(view);
    }

    @Test
    public void testTestFetchLightOk() {
        if(BuildConfig.DEBUG) {
            return;
        }

        view.showLightNetwork(new LightNetwork(), 0, 0);

        int oldViewState = view.getState();

        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        assertThat(view.getState(), equalTo(oldViewState));
    }

    private void setTestStatus(LightControl.Status status) {
        ((MockLightControlImpl)presenter.lightControl).setStatus(status);
    }

    private void fetchLightAndHandleEvent() {
        presenter.fetchLight(Constants.TEST_ID);
        LightChangedEvent event = getCheckedEvent(LightChangedEvent.class);
    }

    @Test
    public void testOnEvent() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

    }

    @Test
    public void testOnEvent1() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

    }

    @Test
    public void testOnEvent2() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

    }

    @Test
    public void testOnEvent3() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

    }

    @Test
    public void testOnEvent4() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

    }
}