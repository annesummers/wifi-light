package com.giganticsheep.wifilight.ui.navigation;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.base.ComponentBase;
import com.giganticsheep.wifilight.ui.base.ActivityModule;
import com.giganticsheep.wifilight.ui.base.ActivityScope;
import com.giganticsheep.wifilight.ui.navigation.group.GroupAdapter;
import com.giganticsheep.wifilight.ui.navigation.group.GroupFragment;
import com.giganticsheep.wifilight.ui.navigation.group.GroupPresenter;
import com.giganticsheep.wifilight.ui.navigation.location.LocationAdapter;
import com.giganticsheep.wifilight.ui.navigation.location.LocationFragment;
import com.giganticsheep.wifilight.ui.navigation.location.LocationPresenter;
import com.giganticsheep.wifilight.ui.status.group.GroupStatusFragment;
import com.giganticsheep.wifilight.ui.status.group.GroupStatusPresenter;
import com.giganticsheep.wifilight.ui.status.location.LocationStatusFragment;
import com.giganticsheep.wifilight.ui.status.location.LocationStatusPresenter;

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
                                                NavigationViewStateActivity.Injector,
                                                NavigationPresenter.Injector,
                                                LocationPresenter.Injector,
                                                LocationFragment.Injector,
                                                LocationStatusPresenter.Injector,
                                                LocationStatusFragment.Injector,
                                                LocationAdapter.Injector,
                                                GroupPresenter.Injector,
                                                GroupFragment.Injector,
                                                GroupAdapter.Injector,
                                                GroupStatusPresenter.Injector,
                                                GroupStatusFragment.Injector { }
