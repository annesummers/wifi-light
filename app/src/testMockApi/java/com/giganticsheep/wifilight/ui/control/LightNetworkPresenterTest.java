package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.ui.WifiLightTestsComponent;
import com.giganticsheep.wifilight.api.FetchLightNetworkEvent;
import com.giganticsheep.wifilight.api.GroupFetchedEvent;
import com.giganticsheep.wifilight.api.LightFetchedEvent;
import com.giganticsheep.wifilight.api.LocationFetchedEvent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.MockLight;
import com.giganticsheep.wifilight.api.network.MockLightControlImpl;
import com.giganticsheep.wifilight.base.MockedTestBase;
import com.giganticsheep.wifilight.ui.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.DaggerTestPresenterComponent;
import com.giganticsheep.wifilight.ui.base.TestLightNetworkView;
import com.giganticsheep.wifilight.ui.base.TestPresenterComponent;
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
public class LightNetworkPresenterTest extends MockedTestBase {

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
    public void setUp() {
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
    public void testNetworkReceivedOK() {
        if(BuildConfig.DEBUG) {
            return;
        }

        presenter.onEvent(new LocationFetchedEvent(testLightNetwork.getLocation()));

        for(int i = 0; i < testLightNetwork.groupCount(); i++) {
            presenter.onEvent(new GroupFetchedEvent(testLightNetwork.get(i)));
        }

        for(int i = 0; i < testLightNetwork.groupCount(); i++) {
            for(int j = 0; j < testLightNetwork.lightCount(i); j++) {
                LightViewData lightViewData = testLightNetwork.get(i, j);

                MockLight light = new MockLight(lightViewData.getId(), lightViewData.getLabel());
                light.group.id = lightViewData.getGroupId();
                light.connected = lightViewData.isConnected();

                presenter.onEvent(new LightFetchedEvent(light));
            }
        }

        presenter.onEvent(new FetchLightNetworkEvent());

        LightNetwork lightNetwork = view.getLightNetwork();

        assertThat(lightNetwork.groupCount(), equalTo(testLightNetwork.groupCount()));

        for(int i = 0; i < testLightNetwork.groupCount(); i++) {
            assertThat(lightNetwork.get(i).getId(), equalTo(testLightNetwork.get(i).getId()));
            assertThat(lightNetwork.lightCount(i), equalTo(testLightNetwork.lightCount(i)));
        }

        for(int i = 0; i < testLightNetwork.groupCount(); i++) {
            for (int j = 0; j < testLightNetwork.lightCount(i); j++) {
                assertThat(lightNetwork.get(i, j).getId(), equalTo(testLightNetwork.get(i, j).getId()));
            }
        }
    }
}