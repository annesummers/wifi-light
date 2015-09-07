package com.giganticsheep.wifilight.ui.control.network;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.api.network.MockLightControlImpl;
import com.giganticsheep.wifilight.ui.control.PresenterTestBase;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 26/07/15. <p>
 * (*_*)
 */
public class LightNetworkPresenterTest extends PresenterTestBase {

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
    public void testFetchLightOk() {
        if(BuildConfig.DEBUG) {
            return;
        }

        view.showLightNetwork(new LightNetwork(), 0, 0, 0);

        int oldViewState = view.getState();

        setTestStatus(LightControl.Status.OK);
        fetchLightAndHandleEvent();

        assertThat(view.getState(), equalTo(oldViewState));
    }

    @Test
    public void testNetworkReceivedOK() {
        if(BuildConfig.DEBUG) {
            return;
        }

       // presenter.onEvent(new LightControl.FetchLightNetworkEvent(testLightNetwork));

        LightNetwork lightNetwork = view.getLightNetwork();

        assertThat(lightNetwork.lightLocationCount(), equalTo(testLightNetwork.lightLocationCount()));
        for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
            assertThat(lightNetwork.getLightLocation(i).getId(), equalTo(testLightNetwork.getLightLocation(i).getId()));
            assertThat(lightNetwork.lightGroupCount(i), equalTo(testLightNetwork.lightGroupCount(i)));

            for(int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                assertThat(lightNetwork.getLightGroup(i, j).getId(), equalTo(testLightNetwork.getLightGroup(i, j).getId()));
                assertThat(lightNetwork.lightCount(i, j), equalTo(testLightNetwork.lightCount(i, j)));

                for(int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                    assertThat(lightNetwork.getLight(i, j, k), equalTo(testLightNetwork.getLight(i, j, k)));
                }
            }
        }
    }

    private void setTestStatus(LightControl.Status status) {
        ((MockLightControlImpl)presenter.lightControl).setStatus(status);
    }

    private void fetchLightAndHandleEvent() {
        //presenter.fetchLight(Constants.TEST_ID);
        //LightChangedEvent event = getCheckedEvent(LightChangedEvent.class);
    }
}