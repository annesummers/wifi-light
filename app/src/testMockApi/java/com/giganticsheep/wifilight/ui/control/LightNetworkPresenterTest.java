package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.MockLightControlImpl;
import com.giganticsheep.wifilight.api.network.test.MockLight;
import com.giganticsheep.wifilight.base.MockedTestBase;
import com.giganticsheep.wifilight.ui.WifiLightTestsComponent;
import com.giganticsheep.wifilight.ui.base.DaggerTestPresenterComponent;
import com.giganticsheep.wifilight.ui.base.LightChangedEvent;
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

        view.showLightNetwork(new LightNetwork(), 0, 0, 0);

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

        presenter.onEvent(new LightControl.FetchLightNetworkEvent(testLightNetwork));

        LightNetwork lightNetwork = view.getLightNetwork();

        assertThat(lightNetwork.locationCount(), equalTo(testLightNetwork.locationCount()));
        for(int i = 0; i < testLightNetwork.locationCount(); i++) {
            assertThat(lightNetwork.getLocation(i).getId(), equalTo(testLightNetwork.getLocation(i).getId()));
            assertThat(lightNetwork.groupCount(i), equalTo(testLightNetwork.groupCount(i)));

            for(int j = 0; j < testLightNetwork.groupCount(i); j++) {
                assertThat(lightNetwork.getGroup(i, j).getId(), equalTo(testLightNetwork.getGroup(i, j).getId()));
                assertThat(lightNetwork.lightCount(i, j), equalTo(testLightNetwork.lightCount(i, j)));

                for(int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                    assertThat(lightNetwork.getLight(i, j, k), equalTo(testLightNetwork.getLight(i, j, k)));
                }
            }
        }
    }
}