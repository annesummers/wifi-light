package com.giganticsheep.wifilight.ui.navigation.location;

import android.os.Parcel;
import android.os.Parcelable;

import com.giganticsheep.nofragmentbase.ui.base.ViewHandler;
import com.giganticsheep.nofragmentbase.ui.base.ViewStateHandler;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.ui.base.PresenterBase;

import rx.Subscriber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class LocationPresenter extends PresenterBase<LocationScreen.LocationViewInterface> {

    public LocationPresenter(ViewStateHandler viewState, ViewHandler<LocationScreen.LocationViewInterface> viewHandler) {
        super(viewState, viewHandler);
    }

    public LocationPresenter(Parcel in) {
        super(in);
    }

    // public LocationPresenter(@NonNull final Injector injector) {
  //      injector.inject(this);
   // }

    public void fetchLocation(String locationId) {
        getViewState().setStateLoading();

        subscribe(lightControl.fetchLocation(locationId),
                new Subscriber<Location>() {
                    @Override
                    public void onCompleted() {
                        getViewState().setStateShowing();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().setStateError(e);
                    }

                    @Override
                    public void onNext(Location location) {
                        getView().showLocation(location);
                    }
                });
    }

    public static final Parcelable.Creator<LocationPresenter> CREATOR = new Parcelable.Creator<LocationPresenter>() {
        public LocationPresenter createFromParcel(Parcel source) {
            return new LocationPresenter(source);
        }

        public LocationPresenter[] newArray(int size) {
            return new LocationPresenter[size];
        }
    };

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Location} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Location}.
     */
   /* @DebugLog
    public void onEventMainThread(@NonNull final LocationChangedEvent event) {
        subscribe(lightControl.fetchLocation(event.getLocationId()),
                new Subscriber<Location>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("fetchLocation", e);
                    }

                    @Override
                    public void onNext(Location location) {
                        getView().showLocation(location);
                    }
                });

    }*/

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightPresenterBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        /**
         * Injects the lightPresenter class into the Component implementing this interface.
         *
         * @param locationPresenter the class to inject.
         */
        void inject(final LocationPresenter locationPresenter);
    }
}
