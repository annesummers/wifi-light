package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.di.ApplicationScope;
import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.di.IOScheduler;
import com.giganticsheep.wifilight.di.UIScheduler;
import com.giganticsheep.wifilight.ui.base.BaseApplication;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Module//(includes = BaseAppModule.class)
public class WifiLightAppModule {
    protected final WifiLightApplication application;

    public WifiLightAppModule(WifiLightApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    BaseLogger provideBaseLogger() {
        return new ApplicationLogger(application);
    }

    @Provides
    @ApplicationScope
    WifiLightApplication provideApplication() {
        return application;
    }

    @Provides
    @ApplicationScope
    EventBus provideEventBus() {
        return new AndroidEventBus();
    }

    @Provides
    @ApplicationScope
    BaseApplication.FragmentFactory provideFragmentFactory() {
        return application.createFragmentFactory();
    }

    @Provides
    @ApplicationScope
    NetworkDetails provideNetworkDetails(WifiLightApplication application) {
        return application.getNetworkDetails();
    }

    /*@Provides
    @UIScheduler
    @ApplicationScope
    Scheduler provideUiScheduler() {
        //return AndroidSchedulers.mainThread();
        return Schedulers.immediate();
    }
*/
    @Provides
    //@IOScheduler
    @ApplicationScope
    Scheduler provideIoScheduler() {
        //return Schedulers.immediate();
        return Schedulers.io();
    }
}
