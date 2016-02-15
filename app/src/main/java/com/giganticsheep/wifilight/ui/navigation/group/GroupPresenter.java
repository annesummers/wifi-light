package com.giganticsheep.wifilight.ui.navigation.group;

import android.os.Parcel;
import android.os.Parcelable;

import com.giganticsheep.nofragmentbase.ui.base.ViewHandler;
import com.giganticsheep.nofragmentbase.ui.base.ViewStateHandler;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.base.PresenterBase;

import rx.Subscriber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class GroupPresenter extends PresenterBase<GroupScreen.GroupViewInterface> {
    
    public GroupPresenter(ViewStateHandler viewState, ViewHandler<GroupScreen.GroupViewInterface> viewHandler) {
        super(viewState, viewHandler);
    }

    public GroupPresenter(Parcel in) {
        super(in);
    }
    
    public void fetchGroup(String groupId) {
        getViewState().setStateLoading();

        subscribe(lightControl.fetchGroup(groupId),
                new Subscriber<Group>() {
                    @Override
                    public void onCompleted() {
                        getViewState().setStateShowing();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().setStateError(e);
                    }

                    @Override
                    public void onNext(Group group) {
                        getView().showGroup(group);
                    }
                });
    }

    public static final Parcelable.Creator<GroupPresenter> CREATOR = new Parcelable.Creator<GroupPresenter>() {
        public GroupPresenter createFromParcel(Parcel source) {
            return new GroupPresenter(source);
        }

        public GroupPresenter[] newArray(int size) {
            return new GroupPresenter[size];
        }
    };

  /*  public GroupPresenter(@NonNull final Injector injector) {
        injector.inject(this);
    }*/

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Location} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Location}.
     */
   /* @DebugLog
    public void onEventMainThread(@NonNull final GroupChangedEvent event) {
        subscribe(lightControl.fetchGroup(event.getGroupId()),
                new Subscriber<Group>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        Timber.e("fetchGroup", e);
                    }

                    @Override
                    public void onNext(@NonNull final Group group) {
                        getView().showGroup(group);
                    }
                });
    }*/

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightPresenterBase derived class to inject itself
     * into the Component.
     */
   /* public interface Injector {
        /**
         * Injects the lightPresenter class into the Component implementing this interface.
         *
         * @param groupPresenter the class to inject.
         */
       // void inject(final GroupPresenter groupPresenter);
    //}*/
}
