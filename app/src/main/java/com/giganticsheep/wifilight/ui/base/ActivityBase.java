package com.giganticsheep.wifilight.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;
import com.giganticsheep.wifilight.base.Logger;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import icepick.Icicle;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public abstract class ActivityBase<V extends MvpView, P extends MvpPresenter<V>> extends MvpViewStateActivity<V, P> {

    private static final String ATTACHED_FRAGMENTS_EXTRA = "attached_fragments_extra";

    @SuppressWarnings("FieldNotUsedInToString")
    protected Logger logger;

    @Icicle private final Map<Integer, FragmentAttachmentDetails> attachedFragments = new HashMap<>();

    private final Map<FragmentBase, FragmentAttachmentDetails> fragmentAttachmentQueue = new HashMap<>();

    private ActivityLayout activityLayout;
    private boolean fragmentsResumed = true;
    private int orientation;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject protected FragmentFactory fragmentFactory;
    @Inject protected BaseLogger baseLogger;
    @Inject protected EventBus eventBus;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLayout = createActivityLayout();

        setContentView(activityLayout.layoutId());

        logger = new Logger(getClass().getName(), baseLogger);

        if(savedInstanceState != null) {
            // TODO can we get Icepick to handle this?
            Parcelable[] fragments = savedInstanceState.getParcelableArray(ATTACHED_FRAGMENTS_EXTRA);

            if(fragments != null) {
                for (Parcelable fragment : fragments) {
                    FragmentAttachmentDetails fragmentDetails = ((FragmentAttachmentDetails) fragment);
                    attachedFragments.put(fragmentDetails.position(), fragmentDetails);
                }
            }
        }

        initialiseViews();
    }

    @Override
    public final void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);

        final FragmentAttachmentDetails[] fragments = attachedFragments.values().toArray(
                new FragmentAttachmentDetails[attachedFragments.size()]);

        outState.putParcelableArray(ATTACHED_FRAGMENTS_EXTRA, fragments);

        fragmentsResumed = false;
    }

    @Override
    public final void onConfigurationChanged(@NonNull final Configuration config) {
        super.onConfigurationChanged(config);

        if(reinitialiseOnRotate() && config.orientation != orientation) {
            orientation = config.orientation;
            setContentView(activityLayout().layoutId());

            initialiseViews();
        }
    }

    @Override
    public final void onResumeFragments() {
        super.onResumeFragments();

        fragmentsResumed = true;

        for(final FragmentBase fragment : fragmentAttachmentQueue.keySet()) {
            fragment.attachToActivity(this, fragmentAttachmentQueue.get(fragment));
        }

        fragmentAttachmentQueue.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeSubscription.unsubscribe();
    }

    /**
     * @param message the message to show in the toast
     */
    public final void showToast(final String message) {
        Toast.makeText(ActivityBase.this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Queues a fragment for attachment to this Activity once the Activity is
     * in a state to have Fragments attached.
     *
     * @param fragment the Fragment to queue for attachment
     */
    public final void queueFragmentForAttachment(final FragmentBase fragment, FragmentAttachmentDetails details) {
        fragmentAttachmentQueue.put(fragment, details);
    }

    protected void initialiseViews() {
        final int containerCount = activityLayout().fragmentContainerCount();
        for (int i = 0; i < containerCount; i++) {
            if (attachedFragments.containsKey(i)) {
                final FragmentAttachmentDetails fragmentAttachmentDetails = attachedFragments.get(i);

                attachFragment(fragmentAttachmentDetails);
            }
        }
    }

    /**
     * Creates a new fragment and attaches it to this Activity.
     *
     * @param attachmentDetails the details of the fragment to attach
     */
    protected final void attachNewFragment(@NonNull final FragmentAttachmentDetails attachmentDetails) {
        FragmentBase fragment;

        try {
            fragment = FragmentBase.create(attachmentDetails.name(), fragmentFactory);

            addFragment(attachmentDetails);

            if (fragmentsResumed()) {
                fragment.attachToActivity(ActivityBase.this, attachmentDetails);
            } else {
                queueFragmentForAttachment(fragment, attachmentDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * First looks for an existing Fragment and if it exists, attaches that to this Activity.
     * If one is not found, calls attachNewFragment to attach a new Fragment.
     *
     * @param attachmentDetails the details of the fragment to attach
     */
    protected final void attachFragment(@NonNull final FragmentAttachmentDetails attachmentDetails) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentBase fragment = (FragmentBase) fragmentManager.findFragmentByTag(attachmentDetails.name());

        if(fragment == null) {
            attachNewFragment(attachmentDetails);
        } else {
            if (fragmentsResumed()) {
                fragment.attachToActivity(ActivityBase.this, attachmentDetails);
            } else {
                queueFragmentForAttachment(fragment, attachmentDetails);
            }
        }
    }

    /**
     * Adds a fragment to the hash of attached fragments.  This does not actually attach the fragment.
     *
     * @param details the attachment details of the fragment
     */
    void addFragment(@NonNull FragmentAttachmentDetails details) {
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
    @NonNull
    protected FragmentBase findFragment(final int position) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        return (FragmentBase) fragmentManager.findFragmentById(containerIdFromPosition(position));
    }

    /**
     * Returns a fragment with the given fragment attach information.
     *
     * @param fragmentDetails the attach information associated with the fragment
     * @return the found fragment
     */
    @NonNull
    protected FragmentBase findFragment(@NonNull final FragmentAttachmentDetails fragmentDetails) {
        return findFragment(fragmentDetails.position());
    }

    /**
     * Subscribes to observable with subscriber, retaining the resulting Subscription so
     * when the Activity is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param subscriber the Subscriber to subscribe with
     * @param <T>
     */
    protected <T> void subscribe(@NonNull final Observable<T> observable, @NonNull Subscriber<T> subscriber) {
        compositeSubscription.add(observable.subscribe(subscriber));
    }

    /**
     * Subscribes to observable with ErrorSubscriber, retaining the resulting Subscription so
     * when the Activity is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param <T>
     */
    protected <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber<T>(logger));
    }

    private ActivityLayout activityLayout() {
        return activityLayout;
    }

    @NonNull
    private WifiLightApplication getWifiLightApplication() {
        return (WifiLightApplication)getApplication();
    }

    // abstract methods

    protected abstract ActivityLayout createActivityLayout();

    protected abstract boolean reinitialiseOnRotate();
}
