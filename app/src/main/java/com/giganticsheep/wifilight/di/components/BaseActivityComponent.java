package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.di.modules.BaseActivityModule;
import com.giganticsheep.wifilight.di.PerActivity;

import dagger.Component;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@PerActivity
@Component(dependencies = BaseApplicationComponent.class, modules = BaseActivityModule.class)
public interface BaseActivityComponent {

    //Exposed to sub-graphs.
    //Context context();
   // BaseActivity activity();
}
