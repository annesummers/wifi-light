package com.giganticsheep.wifilight.ui.preferences;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.base.ActivityScope;

import dagger.Component;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 28/07/15. <p>
 * (*_*)
 */

@ActivityScope
@Component (
        dependencies = {WifiLightAppComponent.class},
        modules = { WifiPreferenceActivityModule.class })
public interface WifiPreferenceActivityComponent extends WifiPreferenceActivity.ServerPreferencesFragment.Injector {
}
