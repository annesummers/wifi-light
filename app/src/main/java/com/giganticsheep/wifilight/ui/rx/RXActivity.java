package com.giganticsheep.wifilight.ui.rx;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.giganticsheep.wifilight.Logger;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.model.LightNetwork;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.observables.AndroidObservable;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public abstract class RXActivity extends ActionBarActivity {

    private static final String ATTACHED_FRAGMENTS_EXTRA = "attached_fragments_extra";

    @SuppressWarnings("FieldNotUsedInToString")
    protected final Logger logger = new Logger(getClass().getName());

    private final Map<Integer, FragmentAttachmentDetails> attachedFragments = new HashMap<>();
    private ActivityLayout activityLayout;
    private boolean fragmentsResumed = true;
    private final Collection<RXFragment> fragmentAttachmentQueue = new ArrayList<>();

    protected final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLayout = createActivityLayout();

        if(savedInstanceState != null) {
            final int containerCount = activityLayout().fragmentContainerCount();
            for (int i = 0; i < containerCount; i++) {
                if (attachedFragments.containsKey(i)) {
                    final FragmentAttachmentDetails fragmentAttachmentDetails = attachedFragments.get(i);

                    attachNewFragment(fragmentAttachmentDetails.name(),
                            fragmentAttachmentDetails.position(),
                            fragmentAttachmentDetails.addToBackStack());
                }
            }
        }

        initialiseViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeSubscription.unsubscribe();
    }

    @Override
    protected void onStart() {
        super.onStart();

        WifiLightApplication.application().registerForEvents(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        WifiLightApplication.application().unregisterForEvents(this);
    }

    @Override
    public final void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        final FragmentAttachmentDetails[] fragments = attachedFragments.values().toArray(
                new FragmentAttachmentDetails[attachedFragments.size()]);

        outState.putParcelableArray(ATTACHED_FRAGMENTS_EXTRA, fragments);

        fragmentsResumed = false;
    }

    @Override
    public final void onResumeFragments() {
        super.onResumeFragments();

        fragmentsResumed = true;

        for(final RXFragment fragment : fragmentAttachmentQueue) {
            fragment.attachToActivity(this);
            fragmentAttachmentQueue.remove(fragment);
            break;
        }
    }

    /**
     * Pops the backstack.
     */
    public final void popBackStack() {
        if (fragmentsResumed()) {
            getFragmentManager().popBackStackImmediate();
        }

        // TODO what if the fragments haven't been resumed?
    }

    /**
     * @param message the message to show in the toast
     */
    public final void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * @param fragment the Fragment to queue for attachment
     */
    public final void queueFragmentForAttachment(final RXFragment fragment) {
        fragmentAttachmentQueue.add(fragment);
    }

    /**
     * @param name the name of the Fragment to create
     * @param position the position in the layout to attach the Fragment
     * @param addToBackStack whether to attach the fragment to the backstack or not
     */
    protected final void attachNewFragment(final String name, final int position, final boolean addToBackStack) {
        Subscription subscription = null;

        compositeSubscription.add(RXFragment.create(name, getRXApplication())
                .flatMap(new Func1<RXFragment, Observable<?>>() {
                    @Override
                    public Observable<RXFragment> call(final RXFragment fragment) {
                        return fragment.attachToActivity(RXActivity.this, name, position, addToBackStack);
                    }
                })
                .subscribe());
    }
    /**
     * Adds a fragment to the hash of attached fragments.  This does not actually attach the fragment.
     *
     * @param name the name of the attached Fragment
     * @param position the position of the attached Fragment
     */
    void addFragment(final String name, final int position, final boolean addToBackStack) {
        if(attachedFragments.containsKey(position)) {
            final String oldName = attachedFragments.get(position).name();
            if(oldName.equals(name)) {
                return;
            } else {
                attachedFragments.remove(position);
            }
        }

        attachedFragments.put(position, new FragmentAttachmentDetails(position, name, addToBackStack));
    }

    /**
     * @return whether the fragments have been resumed or not
     */
    public final boolean fragmentsResumed() {
        return fragmentsResumed;
    }

    @Subscribe
    public void lightChangeSuccessful(LightNetwork.SuccessEvent event) {
        // TODO: React to the event somehow!
        showToast("Changed!");
    }

    /**
     * @param position the position in the activity
     * @return the resource container id
     */
    final int containerIdFromPosition(final int position) {
        return activityLayout.fragmentContainer(position);
    }

    /**
     * Returns a fragment at a given position.
     *
     * @param position the position the fragment is attached at
     * @return the found fragment
     */
    protected RXFragment findFragment(final int position) {
        final FragmentManager fragmentManager = getFragmentManager();

        return (RXFragment) fragmentManager.findFragmentById(containerIdFromPosition(position));
    }

    /**
     * Returns a fragment with the given fragment attach information.
     *
     * @param fragmentDetails the attach information associated with the fragment
     * @return the found fragment
     */
    protected RXFragment findFragment(final AttachDetails fragmentDetails) {
        return findFragment(fragmentDetails.position());
    }

    protected <T> Observable<? extends T> bind(final Observable<? extends T> observable) {
        return AndroidObservable.bindActivity(this, observable);
    }

    private ActivityLayout activityLayout() {
        return activityLayout;
    }

    private RXApplication getRXApplication() {
        return (RXApplication) getApplication();
    }

    // abstract methods

    protected abstract void initialiseViews();

    protected abstract ActivityLayout createActivityLayout();

    private static class FragmentAttachmentDetails implements Parcelable {
        private final int mPosition;
        private final String mName;
        private boolean mAddToBackStack;

        public FragmentAttachmentDetails(final int position, final String name, final boolean addToBackStack) {
            mPosition = position;
            mName = name;
            mAddToBackStack = addToBackStack;
        }

        protected FragmentAttachmentDetails(final Parcel in) {
            mPosition = in.readInt();
            mName = in.readString();
            mAddToBackStack = in.readByte() != 0;
        }

        public static final Parcelable.Creator<FragmentAttachmentDetails> CREATOR = new Parcelable.Creator<FragmentAttachmentDetails>() {
            @Override
            public FragmentAttachmentDetails createFromParcel(final Parcel source) {
                return new FragmentAttachmentDetails(source);
            }

            @Override
            public FragmentAttachmentDetails[] newArray(final int size) {
                return new FragmentAttachmentDetails[size];
            }
        };

        public String name() {
            return mName;
        }

        public int position() {
            return mPosition;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel parcel, final int flags) {
            parcel.writeString(mName);
            parcel.writeInt(mPosition);
            parcel.writeByte((byte) (mAddToBackStack ? 1 : 0));
        }

        public boolean addToBackStack() {
            return mAddToBackStack;
        }
    }
}
