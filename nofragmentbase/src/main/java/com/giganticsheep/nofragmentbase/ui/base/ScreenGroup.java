package com.giganticsheep.nofragmentbase.ui.base;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by anne on 11/11/15.
 */
public abstract class ScreenGroup implements Parcelable,
        SubscriptionHandler {

    private final EventBus groupEventBus;

    private SubscriptionDelegate subscriptionDelegate = new SubscriptionDelegate();

    public ScreenGroup() {
        groupEventBus = new EventBus();
    }

    @Override
    public <T> Subscription subscribe(@NonNull final Observable<T> observable,
                                      @NonNull final Subscriber<T> subscriber) {
        return subscriptionDelegate.subscribe(observable, subscriber);
    }

    @Override
    public void shutdown() {
        subscriptionDelegate.shutdown();
    }

    @Override
    public void clearSubscriptions() {
        subscriptionDelegate.clearSubscriptions();
    }

    public void postControlEvent(Object event) {
        groupEventBus.post(event);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void registerForEvents(Screen screen) {
        groupEventBus.register(screen);
    }

    public void unRegisterForEvents(Screen screen) {
        groupEventBus.unregister(screen);
    }

    public void registerForEvents(FlowActivity activity) {
        groupEventBus.register(activity);
    }

    public void unRegisterForEvents(FlowActivity activity) {
        groupEventBus.unregister(activity);
    }

    protected static abstract class ScreenGroupSubscriber<T> extends Subscriber<T> {

        protected final WeakReference<ScreenGroup> screenGroupWeakReference;

        public ScreenGroupSubscriber(ScreenGroup screenGroup) {
            screenGroupWeakReference = new WeakReference<>(screenGroup);
        }
    }
}
