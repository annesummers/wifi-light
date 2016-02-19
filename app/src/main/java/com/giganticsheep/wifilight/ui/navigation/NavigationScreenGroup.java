package com.giganticsheep.wifilight.ui.navigation;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.ui.base.LocationChangedEvent;
import com.giganticsheep.wifilight.ui.base.WifiLightScreenGroup;
import com.giganticsheep.wifilight.ui.navigation.location.LocationScreen;

import flow.Flow;

/**
 * Created by anne on 15/02/16.
 */
public class NavigationScreenGroup extends WifiLightScreenGroup
                                    implements HasComponent<NavigationActivityComponent> {

    private final NavigationActivityComponent component;

    public NavigationScreenGroup(NavigationActivityComponent component) {
        this.component = component;
        this.component.inject(this);

        eventBus.registerForEvents(this);

        fetchLightNetwork();
    }

    public void onEventMainThread(@NonNull final LocationChangedEvent event) {
        LocationScreen locationScreen;

        if(Flow.get(context).getHistory().top().getClass().isAssignableFrom(LocationScreen.class)) {
            locationScreen = Flow.get(context).getHistory().top();
        } else {
            locationScreen = new LocationScreen(this);
            Flow.get(context).set(locationScreen);
        }

        locationScreen.fetchLocation(event.getLocationId());
    }

    @Override
    public NavigationActivityComponent getComponent() {
        return component;
    }

    public interface Injector {
        void inject(NavigationScreenGroup screenGroup);
    }
}
