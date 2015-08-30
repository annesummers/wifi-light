package com.giganticsheep.wifilight.ui.base;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;
import com.giganticsheep.wifilight.base.error.SilentErrorSubscriber;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

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
public abstract class PresenterBase<V extends ViewBase> extends MvpBasePresenter<V> {

    @NonNull
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject protected EventBus eventBus;
    @Inject protected ErrorStrings errorStrings;
    @Inject public LightControl lightControl;

    @Override
    public void attachView(@NonNull final V view) {
        super.attachView(view);

        eventBus.registerForEvents(this).subscribe(new SilentErrorSubscriber());
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        eventBus.unregisterForEvents(this).subscribe(new SilentErrorSubscriber());
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
}
