package com.giganticsheep.wifilight.ui.navigation;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.base.ActivityModule;
import com.giganticsheep.wifilight.ui.base.ActivityScope;
import com.giganticsheep.wifilight.ui.base.ComponentBase;
import com.giganticsheep.wifilight.ui.navigation.location.LocationScreen;
import com.giganticsheep.wifilight.ui.navigation.room.RoomScreen;

import dagger.Component;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightAppComponent.class},
        modules = { ActivityModule.class})
public interface NavigationActivityComponent extends ComponentBase,
                                                NavigationActivity.Injector,
                                                NavigationScreenGroup.Injector,
                                                LocationScreen.Injector,
                                                RoomScreen.Injector{ }
