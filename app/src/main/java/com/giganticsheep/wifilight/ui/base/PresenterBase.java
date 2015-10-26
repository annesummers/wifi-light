package com.giganticsheep.wifilight.ui.base;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/08/15. <p>
 * (*_*)
 */
public abstract class PresenterBase<V extends ViewBase>  {
    private WeakReference<V> viewRef;

    @NonNull
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject protected EventBus eventBus;
    @Inject protected ErrorStrings errorStrings;
    @Inject public LightControl lightControl;

    public void attachView(@NonNull final V view) {
        viewRef = new WeakReference<V>(view);

        eventBus.registerForEvents(this);
    }

    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }

        eventBus.unregisterForEvents(this);
    }

    protected V getView() {
        if(viewRef == null || viewRef.get() == null) {
            //throw new ViewNotAttachedException();
            return null;
        }
        return viewRef.get();
    }

    /**
     * Called when the Presenter is destroyed; overridden to cleanup members and
     * to unsubscribe from any services or events the Presenter may be subscribed to
     */
    public void onDestroy() {
        compositeSubscription.unsubscribe();
    }

    /**
     * Called when there is an error.
     *
     * @param event contains the error details.
     */
    @DebugLog
    public synchronized void onEvent(@NonNull final ErrorEvent event) {
        getView().showError(event.getError());
    }

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

    private class ViewNotAttachedException extends Exception {
    }
}
