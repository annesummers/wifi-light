package com.giganticsheep.wifilight.ui.base;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;
import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import icepick.Icepick;
import icepick.Icicle;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public abstract class ViewStateActivityBase<V extends ViewBase,
                                            P extends PresenterBase<V>,
                                            S extends ViewStateBase<V>,
                                            C extends ComponentBase>
                                    extends AppCompatActivity
                                    implements HasComponent<C>,
                                                ViewBase {

    private static final String ATTACHED_FRAGMENTS_EXTRA = "attached_fragments_extra";
    public static final String ANIMATION_EXTRA = "animation_extra";

    public static final int ANIMATION_NONE = 0;
    public static final int ANIMATION_FADE = 1;

    @Icicle private final Map<Integer, FragmentAttachmentDetails> attachedFragments = new ArrayMap<>();

    private final Map<FragmentBase, FragmentAttachmentDetails> fragmentAttachmentQueue = new ArrayMap<>();

    protected ActivityLayout activityLayout;
    private boolean fragmentsResumed = true;
    private int orientation;

    private DialogFragment errorDialog;

    private int animation = ANIMATION_NONE;

    private P presenter;
    private S viewState;

    private boolean retainingInstanceState = false;
    @Icicle boolean applyViewState;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject protected FragmentFactory fragmentFactory;
    @Inject protected EventBus eventBus;
    @Inject protected ErrorStrings errorStrings;
    @Inject protected SharedPreferences sharedPreferences;
    private boolean restoringViewState;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies();
        Icepick.restoreInstanceState(this, savedInstanceState);

        doCreatePresenter();
        attachView();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            animation = extras.getInt(ANIMATION_EXTRA, ANIMATION_NONE);
        }

        switch (animation) {
            case ANIMATION_FADE:
                overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);
        }

        eventBus.registerForUIEvents(this);

        activityLayout = createActivityLayout();

        setContentView(activityLayout.layoutId());

        if(savedInstanceState != null) {
            // TODO can we get Icepick to handle this?
            Parcelable[] fragments = savedInstanceState.getParcelableArray(ATTACHED_FRAGMENTS_EXTRA);

            if(fragments != null) {
                for (Parcelable fragment : fragments) {
                    FragmentAttachmentDetails fragmentDetails = ((FragmentAttachmentDetails) fragment);
                    attachedFragments.put(fragmentDetails.position, fragmentDetails);
                }
            }
        } else {
            applyViewState = false;
            attachInitialFragments();
        }

        loadData();
        initialiseViews();
    }

    protected abstract void loadData();

    private void doCreatePresenter() {
        P presenter = getPresenter();
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter == null) {
            throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
        }

        this.presenter = presenter;
    }

    @Override
    public final void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        createOrRestoreViewState(savedInstanceState);
        applyViewState();
    }

    @Override
    public final void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);

        saveViewState(outState);

        Icepick.saveInstanceState(this, outState);

        final FragmentAttachmentDetails[] fragments = attachedFragments.values().toArray(
                new FragmentAttachmentDetails[attachedFragments.size()]);

        outState.putParcelableArray(ATTACHED_FRAGMENTS_EXTRA, fragments);

        fragmentsResumed = false;
    }

    /**
     * Attaches the view to the presenter
     */
    private void attachView() {
        P presenter = getPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        }
        presenter.attachView(getView());
    }

    protected V getView() {
        return (V) this;
    }

    /**
     * Called to detach the view from presenter
     */
    private void detachView() {
        P presenter = getPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        }
        presenter.detachView(isRetainingInstance());
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        ButterKnife.inject(this);
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onConfigurationChanged(@NonNull final Configuration config) {
        super.onConfigurationChanged(config);

        if(reinitialiseOnRotate() && config.orientation != orientation) {
            orientation = config.orientation;
            setContentView(activityLayout().layoutId());

            initialiseViews();

            applyViewState();
        }
    }

    protected boolean createOrRestoreViewState(Bundle savedInstanceState) {
        if (getViewState() != null) {
            retainingInstanceState = true;
            //applyViewState = true;
            return true;
        }

        // Create view state
        viewState = createViewState();
        if (getViewState() == null) {
            throw new NullPointerException(
                    "ViewState is null! Do you return null in createViewState() method?");
        }

        // Try to restore data from bundle (savedInstanceState)
        if (savedInstanceState != null) {

            ViewStateBase restoredViewState =
                    getViewState().restoreInstanceState(savedInstanceState);

            boolean restoredFromBundle = restoredViewState != null;

            if (restoredFromBundle) {
                viewState = (S) restoredViewState;
                retainingInstanceState = false;
                //applyViewState = true;
                return true;
            }
        }

        // ViewState not restored, activity / fragment starting first time
       // applyViewState = false;
        return false;
    }

    public boolean applyViewState() {
        if (applyViewState) {
            setRestoringViewState(true);
            getViewState().apply(getView(), retainingInstanceState);
            setRestoringViewState(false);
            onViewStateInstanceRestored(retainingInstanceState);
            return true;
        }

        onNewViewStateInstance();
        return false;
    }

    @Override
    public void showLoading() {
        getViewState().setShowLoading();
    }

    @Override
    public void showError(Throwable throwable) {
        getViewState().setShowError(throwable);
    }

    protected void setRestoringViewState(boolean restoring) {
        restoringViewState = restoring;
    }
    protected boolean isRestoringViewState() {
        return restoringViewState;
    }

    public void saveViewState(Bundle outState) {
        boolean retainingInstanceState = isRetainingInstance();

        ViewState viewState = getViewState();
        if (viewState == null) {
            throw new NullPointerException("ViewState is null! That's not allowed");
        }

        applyViewState = true;

        // Save the viewstate
        if (!retainingInstanceState) {
            ((ViewStateBase) viewState).saveInstanceState(outState);
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
    protected void onPause() {
        super.onPause();

        switch (animation) {
            case ANIMATION_FADE:
                overridePendingTransition(R.anim.hold, R.anim.push_out_to_left);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(errorDialog != null) {
            new Handler().post(() -> {
                errorDialog.dismiss();
                errorDialog = null;
            });
        }

        compositeSubscription.unsubscribe();

        eventBus.unregisterForEvents(this);
    }

    /**
     * @param message the message to show in the toast
     */
    public final void showToast(final String message) {
        Toast.makeText(ViewStateActivityBase.this, message, Toast.LENGTH_LONG).show();
    }

    public final void showErrorDialog(final Throwable throwable) {
        if (errorDialog != null) {
            errorDialog.dismiss();
        }

        errorDialog = SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setMessage(throwable.getMessage())
                .setPositiveButtonText(android.R.string.ok)
                .show();
    }

    /**
     * Queues a fragment for attachment to this Activity once the Activity is
     * in a state to have Fragments attached.
     *
     * @param fragment the Fragment to queue for attachment
     * @param details the details of the fragment to attach
     */
    private void queueFragmentForAttachment(@NonNull final FragmentBase fragment,
                                            @NonNull final FragmentAttachmentDetails details) {
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
     * @param details the details of the fragment to attach
     */
    @DebugLog
    protected final void attachNewFragment(@NonNull final FragmentAttachmentDetails details) {
        FragmentBase fragment;

        try {
            if(details.argsMap.size() == 0) {
                fragment = FragmentBase.create(details.name, fragmentFactory);
            } else {
                fragment = FragmentBase.create(details.name, details.argsMap, fragmentFactory);
            }

            addFragment(details);

            if (fragmentsResumed()) {
                fragment.attachToActivity(ViewStateActivityBase.this, details);
            } else {
                queueFragmentForAttachment(fragment, details);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DebugLog
    protected final void detachFragment(@NonNull String name) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentBase fragment = (FragmentBase) fragmentManager.findFragmentByTag(name);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.detach(fragment);
        transaction.commit();
    }

    /**
     * First looks for an existing Fragment and if it lightExists, attaches that to this Activity.
     * If one is not found, calls attachNewFragment to attach a new Fragment.
     *
     * @param attachmentDetails the details of the fragment to attach
     */
    protected void attachFragment(@NonNull final FragmentAttachmentDetails attachmentDetails) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentBase fragment = (FragmentBase) fragmentManager.findFragmentByTag(attachmentDetails.name);

        if(fragment == null) {
            attachNewFragment(attachmentDetails);
        } else {
            if (fragmentsResumed()) {
                fragment.attachToActivity(this, attachmentDetails);
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
    protected void addFragment(@NonNull final FragmentAttachmentDetails details) {
        if(attachedFragments.containsKey(details.position)) {
            final String oldName = attachedFragments.get(details.position).name;
            if(oldName.equals(details.name)) {
                return;
            } else {
                attachedFragments.remove(details.position);
            }
        }

        attachedFragments.put(details.position, details);
    }

    /**
     * @return whether the fragments have been resumed or not
     */
    private boolean fragmentsResumed() {
        return fragmentsResumed;
    }

    /**
     * @param position the position in the activity
     * @return the resource container getId
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
    private FragmentBase findFragment(final int position) {
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
        return findFragment(fragmentDetails.position);
    }

    /**
     * Subscribes to observable with subscriber, retaining the resulting Subscription so
     * when the Activity is destroyed the Observable can be unsubscribed from.
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
     * when the Activity is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param <T> the type the Observable is observing
     */
    protected <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber(eventBus, errorStrings));
    }

    private ActivityLayout activityLayout() {
        return activityLayout;
    }

    @NonNull
    protected WifiLightApplication getWifiLightApplication() {
        return (WifiLightApplication)getApplication();
    }

    protected void injectDependencies() {

    }

    public S getViewState() {
        return viewState;
    }

    public static class FragmentShownEvent { }

    // abstract methods

    protected abstract void attachInitialFragments();

    protected abstract ActivityLayout createActivityLayout();

    protected abstract boolean reinitialiseOnRotate();

    protected abstract P createPresenter();

    protected boolean isRetainingInstance() {
        return false;
    }

    public abstract S createViewState();
    protected abstract void restoreInstanceState(Bundle savedInstanceState);

    protected abstract void onNewViewStateInstance();

    protected void onViewStateInstanceRestored(boolean retainingInstanceState) {

    }
}
