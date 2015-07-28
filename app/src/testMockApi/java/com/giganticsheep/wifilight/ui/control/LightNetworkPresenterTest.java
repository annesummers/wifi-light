package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.api.FetchLightsEvent;
import com.giganticsheep.wifilight.api.FetchLocationsEvent;
import com.giganticsheep.wifilight.api.FetchedGroupEvent;
import com.giganticsheep.wifilight.api.FetchedLightEvent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.GroupData;
import com.giganticsheep.wifilight.api.network.LightResponse;
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
    public void testNetworkReceivedOK() throws Exception {
       // if(BuildConfig.DEBUG) {
       //     return;
       // }

        GroupData testGroup = new GroupData();
        testGroup.name = Constants.TEST_GROUP_LABEL;
        testGroup.id = Constants.TEST_GROUP_ID;

        GroupData testGroup2 = new GroupData();
        testGroup2.name = Constants.TEST_GROUP_LABEL2;
        testGroup2.id = Constants.TEST_GROUP_ID2;

        LightResponse testLight1 = new LightResponse(Constants.TEST_ID);
        testLight1.label = Constants.TEST_LABEL;
        testLight1.group = testGroup;

        LightResponse testLight2 = new LightResponse(Constants.TEST_ID2);
        testLight2.label = Constants.TEST_LABEL2;
        testLight2.group = testGroup;

        LightResponse testLight3 = new LightResponse(Constants.TEST_ID3);
        testLight3.label = Constants.TEST_LABEL3;
        testLight3.group = testGroup2;

        presenter.onEvent(new FetchLocationsEvent(1));

        presenter.onEvent(new FetchedGroupEvent(testGroup));
        presenter.onEvent(new FetchedGroupEvent(testGroup2));

        presenter.onEvent(new FetchedLightEvent(testLight1));
        presenter.onEvent(new FetchedLightEvent(testLight2));
        presenter.onEvent(new FetchedLightEvent(testLight3));

        presenter.onEvent(new FetchLightsEvent(3));

        LightNetwork lightNetwork = view.getLightNetwork();

        assertThat(lightNetwork.groupCount(), equalTo(2));
        assertThat(lightNetwork.get(0).getId(), equalTo(testGroup.id));
        assertThat(lightNetwork.get(1).getId(), equalTo(testGroup2.id));
        assertThat(lightNetwork.get(0, 0).getId(), equalTo(testLight1.id()));
        assertThat(lightNetwork.get(0, 1).getId(), equalTo(testLight2.id()));
        assertThat(lightNetwork.get(1, 0).getId(), equalTo(testLight3.id()));
        assertThat(lightNetwork.lightCount(0), equalTo(2));
        assertThat(lightNetwork.lightCount(1), equalTo(1));
    }
}