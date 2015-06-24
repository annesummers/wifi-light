package com.giganticsheep.wifilight.ui.rx;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.giganticsheep.wifilight.Logger;
import com.giganticsheep.wifilight.WifiLightApplication;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.observables.AndroidObservable;
import rx.functions.Action1;
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
    private final Map<RXFragment, FragmentAttachmentDetails> fragmentAttachmentQueue = new HashMap<>();
    private Handler mainThreadHandler;

    protected final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLayout = createActivityLayout();

        mainThreadHandler = new Handler(Looper.getMainLooper());

        if(savedInstanceState != null) {
            Parcelable[] fragments = savedInstanceState.getParcelableArray(ATTACHED_FRAGMENTS_EXTRA);

            if(fragments != null) {
                for (Parcelable fragment : fragments) {
                    FragmentAttachmentDetails fragmentDetails = ((FragmentAttachmentDetails) fragment);
                    attachedFragments.put(fragmentDetails.position, fragmentDetails);
                }
            }
        }

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
    public final void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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

        for(final RXFragment fragment : fragmentAttachmentQueue.keySet()) {
            fragment.attachToActivity(this, fragmentAttachmentQueue.get(fragment));
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
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RXActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Queues a fragment for attachment to this Activity once the Activity is
     * in a state to have Fragments attached.
     *
     * @param fragment the Fragment to queue for attachment
     */
    public final void queueFragmentForAttachment(final RXFragment fragment, FragmentAttachmentDetails details) {
        fragmentAttachmentQueue.put(fragment, details);
    }

    /**
     * @param name the name of the Fragment to create
     * @param position the position in the layout to attach the Fragment
     * @param addToBackStack whether to attach the fragment to the backstack or not
     */
    protected final void attachNewFragment(final String name, final int position, final boolean addToBackStack) {
        compositeSubscription.add(bind(RXFragment.create(name, getRXApplication()))
                .doOnNext(new Action1<RXFragment>() {
                    @Override
                    public void call(RXFragment fragment) {
                        FragmentAttachmentDetails details = new FragmentAttachmentDetails(name, position, addToBackStack);
                        addFragment(details);

                        if (fragmentsResumed()) {
                            fragment.attachToActivity(RXActivity.this, details);
                        } else {
                            queueFragmentForAttachment(fragment, details);
                        }
                    }
                })
                .subscribe());
    }

    /**
     * @param name the name of the Fragment to create
     * @param position the position in the layout to attach the Fragment
     * @param addToBackStack whether to attach the fragment to the backstack or not
     */
    protected final void attachFragment(final String name, final int position, final boolean addToBackStack) {
        final FragmentManager fragmentManager = getFragmentManager();

        RXFragment fragment = (RXFragment) fragmentManager.findFragmentByTag(name);

        if(fragment == null) {
            attachNewFragment(name, position, addToBackStack);
        } else {
            fragment.attachToActivity(this, new FragmentAttachmentDetails(name, position, addToBackStack));
        }
    }

    /**
     * Adds a fragment to the hash of attached fragments.  This does not actually attach the fragment.
     *
     * @param details the attachment details of the fragment
     */
    void addFragment(FragmentAttachmentDetails details) {
        if(attachedFragments.containsKey(details.position())) {
            final String oldName = attachedFragments.get(details.position()).name();
            if(oldName.equals(details.name())) {
                return;
            } else {
                attachedFragments.remove(details.position());
            }
        }

        attachedFragments.put(details.position(), details);
    }

    /**
     * @return whether the fragments have been resumed or not
     */
    public final boolean fragmentsResumed() {
        return fragmentsResumed;
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

    protected abstract ActivityLayout createActivityLayout();

    public static class FragmentAttachmentDetails implements Parcelable {
        private final String name;
        private final int position;
        private final boolean addToBackStack;

        public FragmentAttachmentDetails(final String name, final int position, final boolean addToBackStack) {
            this.name = name;
            this.position = position;
            this.addToBackStack = addToBackStack;
        }

        protected FragmentAttachmentDetails(final Parcel in) {
            name = in.readString();
            position = in.readInt();
            addToBackStack = in.readByte() != 0;
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
            return name;
        }

        public int position() {
            return position;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(final Parcel parcel, final int flags) {
            parcel.writeString(name);
            parcel.writeInt(position);
            parcel.writeByte((byte) (addToBackStack ? 1 : 0));
        }

        public boolean addToBackStack() {
            return addToBackStack;
        }

        @Override
        public String toString() {
            return "FragmentAttachmentDetails{" +
                    "position=" + position +
                    ", name='" + name + '\'' +
                    ", addToBackStack=" + addToBackStack +
                    '}';
        }
    }
}
