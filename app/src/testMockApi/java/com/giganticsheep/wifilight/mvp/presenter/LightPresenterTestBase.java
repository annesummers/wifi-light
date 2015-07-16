package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.MockLightControlImpl;
import com.giganticsheep.wifilight.base.WifiLightTestBase;
import com.giganticsheep.wifilight.mvp.view.TestLightView;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public abstract class LightPresenterTestBase extends WifiLightTestBase {

    protected TestPresenterComponent component;

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

        view = new TestLightView(this, baseLogger);
        presenter.attachView(view);
    }

    @Test
    public void testFetchLightNullId() {
        presenter.fetchLight(null);

        assertThat(view.getState(), equalTo(TestLightView.STATE_SHOW_ERROR));
    }

    protected void setTestStatus(LightControl.Status status) {
        ((MockLightControlImpl)presenter.lightControl).setStatus(status);
    }

    protected void setLightTimeout() {
        ((MockLightControlImpl)presenter.lightControl).setLightTimeout(true);
    }

    protected abstract LightPresenterBase createPresenter(LightPresenterBase.Injector injector);

}
