package com.giganticsheep.wifilight.ui.navigation;

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

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by anne on 15/02/16.
 */
public class NavigationScreenGroup extends ScreenGroup implements HasComponent<NavigationActivityComponent> {

    @NonNull
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final NavigationActivityComponent component;

    @Inject protected ErrorStrings errorStrings;
    @Inject protected EventBus eventBus;
    @Inject public LightControl lightControl;

    public NavigationScreenGroup(NavigationActivityComponent component) {
        this.component = component;
        this.component.inject(this);

        fetchLightNetwork();
    }

    public void fetchLightNetwork() {
        subscribe(lightControl.fetchLightNetwork(), new Subscriber<LightNetwork>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                eventBus.postMessage(new ErrorEvent(e));
                postControlEvent(new FlowActivity.ShowErrorEvent(e));
            }

            @Override
            public void onNext(LightNetwork lightNetwork) {
                eventBus.postMessage(new LightControl.FetchLightNetworkEvent(lightNetwork));
            }
        });
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { }

    /**
     * Subscribes to observable with subscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param subscriber the Subscriber to subscribe with
     * @param <T> the type the Observable is observing
     */
    protected <T> void subscribe(@NonNull final Observable<T> observable,
                                 @NonNull final Subscriber<T> subscriber) {
        compositeSubscription.add(observable.subscribe(subscriber));
    }

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
