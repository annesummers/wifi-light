package com.giganticsheep.wifilight.ui.base;

/**
 * Created by anne on 27/10/15.
 */
public interface FrameworkCreator<S, P> {

     P createPresenter();
     S createViewState();

     boolean isRetainingInstance();

     void onViewStateInstanceRestored(boolean retainingInstanceState);

     void onNewViewStateInstance();
}
