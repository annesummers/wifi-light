package com.giganticsheep.wifilight.ui.control.network;

import com.giganticsheep.wifilight.ui.base.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/08/15. <p>
 * (*_*)
 */
@Module
public class LightNetworkDrawerFragmentModule {
    private final LightNetworkDrawerFragment fragment;

    public LightNetworkDrawerFragmentModule(LightNetworkDrawerFragment fragment) {
        this.fragment = fragment;
    }

    @ActivityScope
    @Provides
    LightNetworkPresenter providePresenter() {
        return fragment.getPresenter();
    }

    @ActivityScope
    @Provides
    LightNetworkDrawerFragment provideFragment() {
        return fragment;
    }
}
