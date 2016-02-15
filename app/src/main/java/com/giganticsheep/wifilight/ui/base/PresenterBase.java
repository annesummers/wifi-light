package com.giganticsheep.wifilight.ui.base;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ViewHandler;
import com.giganticsheep.nofragmentbase.ui.base.ViewStateHandler;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 30/08/15. <p>
 * (*_*)
 */
public abstract class PresenterBase<V extends Screen.ViewInterfaceBase> extends com.giganticsheep.nofragmentbase.ui.base.PresenterBase<V> {

    @NonNull
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    protected EventBus eventBus;
    protected ErrorStrings errorStrings;
   // @Inject public LightControl lightControl;

    protected LightControl lightControl;

    public PresenterBase(ViewStateHandler viewState, ViewHandler<V> viewHandler) {
        super(viewState, viewHandler);

        lightControl = WifiLightApplication.getEngine();
        eventBus = WifiLightApplication.getEventBus();
        errorStrings = WifiLightApplication.getErrorStrings();
    }

    public PresenterBase(Parcel in) {
        super(in);

        lightControl = WifiLightApplication.getEngine();
        eventBus = WifiLightApplication.getEventBus();
        errorStrings = WifiLightApplication.getErrorStrings();
    }
/*
    @Override
    public void attachView(@NonNull final V view) {
        super.attachView(view);

        eventBus.registerForEvents(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        eventBus.unregisterForEvents(this);
    }
*/
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
  /*  @DebugLog
    public synchronized void onEvent(@NonNull final ErrorEvent event) {
        getView().showError(event.getError());
    }*/

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
