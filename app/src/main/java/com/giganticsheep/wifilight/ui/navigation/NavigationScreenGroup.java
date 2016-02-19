package com.giganticsheep.wifilight.ui.navigation;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.giganticsheep.nofragmentbase.ui.base.FlowActivity;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;
import com.giganticsheep.wifilight.ui.base.LocationChangedEvent;
import com.giganticsheep.wifilight.ui.navigation.location.LocationScreen;

import javax.inject.Inject;

import flow.Flow;
import rx.Observable;

/**
 * Created by anne on 15/02/16.
 */
public class NavigationScreenGroup extends ScreenGroup implements HasComponent<NavigationActivityComponent> {

    private final NavigationActivityComponent component;

    @Inject protected ErrorStrings errorStrings;
    @Inject protected EventBus eventBus;
    @Inject public LightControl lightControl;
    @Inject protected Context context;

    public NavigationScreenGroup(NavigationActivityComponent component) {
        this.component = component;
        this.component.inject(this);

        eventBus.registerForEvents(this);

        fetchLightNetwork();
    }

    public void fetchLightNetwork() {
        subscribe(lightControl.fetchLightNetwork(), new LightNetworkSubscriber(this));
    }

    private static class LightNetworkSubscriber extends ScreenGroupSubscriber<LightNetwork> {

        public LightNetworkSubscriber(NavigationScreenGroup screenGroup) {
            super(screenGroup);
        }

        @Override
        public void onCompleted() { }

        @Override
        public void onError(Throwable e) {
            NavigationScreenGroup screenGroup = (NavigationScreenGroup) screenGroupWeakReference.get();
            if(screenGroup != null) {
                screenGroup.eventBus.postMessage(new ErrorEvent(e));
                screenGroup.postControlEvent(new FlowActivity.ShowErrorEvent(e));
            }
        }

        @Override
        public void onNext(LightNetwork lightNetwork) {
            NavigationScreenGroup screenGroup = (NavigationScreenGroup) screenGroupWeakReference.get();
            if(screenGroup != null) {
                screenGroup.eventBus.postMessageSticky(new LightControl.FetchLightNetworkEvent(lightNetwork));
            }
        }
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
    public void writeToParcel(Parcel dest, int flags) { }

    /**
     * Subscribes to observable with ErrorSubscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param <T> the type the Observable is observing
     */
    public <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber(eventBus, errorStrings));
    }

    @Override
    public NavigationActivityComponent getComponent() {
        return component;
    }

    public interface Injector {
        void inject(NavigationScreenGroup screenGroup);
    }
}
