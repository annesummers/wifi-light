package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.mvp.view.TestLightView;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 *
 * Created by anne on 15/07/15. <p>
 *
 * (*_*)
 */
public abstract class FragmentPresenterTestBase extends LightPresenterTestBase {

   // protected ControlPresenter controlPresenter;

   /* @NonNull
    @Override
    protected final LightPresenterBase createPresenter(@NonNull final LightPresenterBase.Injector injector) {
        //controlPresenter = new ControlPresenter(injector);
     //   controlPresenter.attachView(view);

        return doCreatePresenter(injector, controlPresenter);
    }*/
/*
    @Test
    public void testGetCurrentLightNoLight() {
        Light light = getPresenter().getLight();

        assertThat(light, equalTo(null));
    }
*/
   /* @Test
    public void testGetCurrentLightWithLight() {
        setTestStatus(LightControl.Status.OK);
        controlPresenter.fetchLight(Constants.TEST_ID);

        Light light = getPresenter().getLight();

        assertThat(light, not(nullValue()));
        assertThat(light.id(), equalTo(Constants.TEST_ID));
    }*/

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
        fetchLightAndHandleEvent();

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    @NonNull
    protected LightPresenterBase getPresenter() {
        return presenter;
    }

    //protected abstract FragmentPresenterBase doCreatePresenter(LightPresenterBase.Injector injector, ControlPresenter controlPresenter);*/
}
