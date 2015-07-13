package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.StatusResponse;
//import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.Logger;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 *
 * Base class for all the Presenters that show information about a Light.
 */
public abstract class LightPresenterBase extends MvpBasePresenter<LightView> {

    // TODO use an interface unstead of a LightNetwork object to abstract the Presenters
    // from the model.

    protected final Logger logger;

    protected final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject protected BaseLogger baseLogger;
    @Inject protected EventBus eventBus;
    @Inject protected LightControl lightNetwork;

    protected final FetchLightSubscriber fetchLightSubscriber = new FetchLightSubscriber();
    protected final SetLightSubscriber setLightSubscriber = new SetLightSubscriber();

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    protected LightPresenterBase(Injector injector) {
        injector.inject(this);

        logger = new Logger(getClass().getName(), baseLogger);
    }

    /**
     * Fetches the Light with the given id.
     *
     * @param id the id of the Light to fetch.
     */
    public void fetchLight(String id) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        compositeSubscription.add(lightNetwork.fetchLight(id).subscribe(fetchLightSubscriber));
    }

    /**
     * Called when the Presenter is destroyed, overriden to cleanup members and
     * to unsubscribe from any services or events the Presenter may be subscribed to
     */
    public void onDestroy() {
        compositeSubscription.unsubscribe();
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightPresenterBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightPresenterBase lightPresenter);
    }

    private class SetLightSubscriber extends Subscriber<StatusResponse>  {

        @Override
        public void onCompleted() { }

        @Override
        public void onError(Throwable e) {
            if (isViewAttached()) {
                getView().showError(e);
            }
        }

        @Override
        public void onNext(StatusResponse light) { }
    }

    private class FetchLightSubscriber extends Subscriber<Light>  {

        @Override
        public void onCompleted() {
            if (isViewAttached()) {
                getView().showMainView();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (isViewAttached()) {
                getView().showError(e);
            }
        }

        @Override
        public void onNext(Light light) {
            getView().lightChanged(light);
        }
    }
}
