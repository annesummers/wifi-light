package com.giganticsheep.wifilight.ui.base;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;
import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;

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
public abstract class FragmentBase<V extends ViewBase, P extends PresenterBase<V>, C extends ComponentBase>
                                                        extends MvpViewStateFragment<V, P>
                                                        implements HasComponent<C> {

    private static final int INVALID = -1;

    private static final String FRAGMENT_ARGS_NAME = "name";
    private static final String FRAGMENT_ARGS_ATTACH_TO_ROOT = "attach_to_root";

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Arg
    public String name;

    @Arg (required = false)
    public boolean attachToRoot;

    private boolean viewsInitialised;

    private int orientation;

    @Icicle boolean firstShow = true;

    private LayoutInflater layoutInflater;
    private FragmentAttachmentDetails attachmentDetails;

    @Inject protected EventBus eventBus;
    @Inject protected ErrorStrings errorStrings;

    /**
     * Creates the named Fragment.
     *
     * @param name the name of the Fragment to create
     * @param fragmentFactory the FragmentFactory used to create the Fragment
     * @return the created Fragment
     * @throws Exception if the name of the fragment doesn't match any in the application
     */
    public static FragmentBase create(@NonNull final String name,
                                      @NonNull final FragmentFactory fragmentFactory) throws Exception {
        return fragmentFactory.createFragment(name);
    }

    /**
     * Creates the named Fragment.
     *
     * @param name the name of the Fragment to create
     * @param args the arguments for the Fragment
     * @param fragmentFactory the FragmentFactory used to create the Fragment
     * @return the created Fragment
     * @throws Exception if the name of the fragment doesn't match any in the application
     */
    public static FragmentBase create(@NonNull final String name,
                                      @NonNull final Map<String, String> args,
                                      @NonNull final FragmentFactory fragmentFactory) throws Exception {
        return fragmentFactory.createFragment(name, args);
    }

    /**
     * Constructs a new BaseFragment.
     */
    protected FragmentBase() { }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();

        if(args != null) {
            if(name == null) {
                name = args.getString(FRAGMENT_ARGS_NAME);
            }

            attachToRoot = args.getBoolean(FRAGMENT_ARGS_ATTACH_TO_ROOT, false);
        }

        orientation = getResources().getConfiguration().orientation;
    }

    @Override
    public final View onCreateView(@NonNull final LayoutInflater inflater,
                                   @Nullable final ViewGroup container,
                                   @Nullable final Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);

        layoutInflater = inflater;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view,
                              @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialiseViews(view);

        viewsInitialised = true;

        if(firstShow) {
            firstShow = false;
            populateViews();
        }

        ViewTreeObserver vto = view.getViewTreeObserver();
        if(vto.isAlive()){
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    removeOnGlobalLayoutListener(view, this);
                    eventBus.postUIMessage(new ActivityBase.FragmentShownEvent());
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener){
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    @Override
    public final void onConfigurationChanged(@NonNull final Configuration config) {
        super.onConfigurationChanged(config);

        if(reinitialiseOnRotate() && config.orientation != orientation) {
            orientation = config.orientation;
            reinitialiseViews();
        }
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();

        viewsInitialised = false;

        destroyViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeSubscription.unsubscribe();

        getPresenter().onDestroy();
    }

    /**
     * @return the name of this Fragment
     */
    @Nullable
    public final String name() {
        return name;
    }

    /**
     * Attaches this Fragment to the specified Activity
     *
     * @param activity the Activity to attach to
     * @param attachmentDetails the details for the Fragment used when attaching to the Activity
     */
    //@DebugLog
    public final void attachToActivity(@NonNull final ActivityBase activity,
                                       @NonNull final FragmentAttachmentDetails attachmentDetails) {
        this.attachmentDetails = attachmentDetails;

        doAttachToActivity(activity);
    }

    /**
     * Show a toast.
     *
     * @param message the message
     */
    protected final void showToast(final String message) {
        getBaseActivity().showToast(message);
    }

    /**
     * Show a toast.
     *
     * @param message the message
     */
    public final void showToast(final int message) {
        getBaseActivity().showToast(getString(message));
    }

    /**
     * Is activity valid.
     *
     * @return the boolean
     */
    protected final boolean isActivityValid() {
        return getActivity() != null && !getActivity().isFinishing();
    }

    /**
     * @return Whether this Fragment is attached to the Activity root or not
     */
    protected final boolean isAttachedToRoot() {
        return attachToRoot;
    }

    /**
     * @return the Activity associated with this Fragment
     */
    @NonNull
    protected ActivityBase getBaseActivity() {
        return (ActivityBase) getActivity();
    }

    /**
     * Destroys the views, detaches them, inflates the layout and
     * initialises the views
     */
    private void reinitialiseViews() {
        destroyViews();

        final ViewGroup rootView = (ViewGroup) getView();
        if (rootView != null) {
            rootView.removeAllViews();
        }

        layoutInflater.inflate(getLayoutRes(), rootView);

        initialiseViews(rootView);
        populateViews();
    }

    /**
     * Subscribes to observable with subscriber, retaining the resulting Subscription so
     * when the Fragment is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param subscriber the Subscriber to subscribe with
     * @param <T> the type the Observable is observing
     */
    private <T> void subscribe(@NonNull final Observable<T> observable,
                               @NonNull final Subscriber<T> subscriber) {
        compositeSubscription.add(observable.subscribe(subscriber));
    }

    /**
     * Subscribes to observable with ErrorSubscriber, retaining the resulting Subscription so
     * when the Fragment is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param <T> the type the Observable is observing
     */
    protected <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber<T>(eventBus, errorStrings));
    }

    private void doAttachToActivity(@NonNull final ActivityBase activity) {
        final String name = attachmentDetails.name;
        final int position = attachmentDetails.position;
        final boolean addToBackStack = attachmentDetails.addToBackStack;

        final FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int attachId = 0;

        if(position != INVALID) {
            attachId = activity.containerIdFromPosition(position);
        }

        if(attachId != 0) {
            final FragmentBase existingFragment = (FragmentBase) fragmentManager.findFragmentById(attachId);
            if (existingFragment != null) {
                fragmentTransaction.detach(existingFragment);
            }

           // fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            if(animateOnShow()) {
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            }

            FragmentBase fragment = (FragmentBase) fragmentManager.findFragmentByTag(name);
            if (fragment != null && fragment.equals(this)) {
                fragmentTransaction.attach(this);
            } else {
                fragmentTransaction.add(attachId, this, name);
            }

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }
        }

        fragmentTransaction.commit();
    }

    private boolean activityExists() {
        return getActivity() != null;
    }

    /**
     * Initialises the views associated with this Fragment
     * @param view the rootView
     */
    protected void initialiseViews(View view) { }

    /**
     * Populates the views associated with this Fragment
     */
    protected void populateViews() { }

    /**
     * Destroys the views associated with this Fragment
     */
    protected void destroyViews() { }

    // abstract methods

    /**
     * Should this Fragment's views be reinitialised on rotation
     * @return boolean
     */
    protected abstract boolean reinitialiseOnRotate();

    protected abstract boolean animateOnShow();
}
