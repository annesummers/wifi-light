package com.giganticsheep.wifilight.ui.base;

import android.app.Application;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public abstract class BaseApplication extends Application {
   // private BaseApplicationComponent baseApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

       //baseApplicationComponent = createApplicationComponent();
    }

    //public BaseApplicationComponent getApplicationComponent() {
   //     return baseApplicationComponent;
   // }

  //  public abstract FragmentFactory createFragmentFactory();

    //protected abstract BaseApplicationComponent createApplicationComponent();

    //public interface FragmentFactory {
    //    Observable<? extends BaseFragment> createFragmentAsync(String name);
     //   BaseFragment createFragment(String name) throws Exception;
    //}
}
