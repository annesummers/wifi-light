package com.giganticsheep.wifilight.ui.base;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.FragmentFactory;
import com.giganticsheep.wifilight.base.Logger;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public abstract class BaseFragment <V extends MvpView, P extends MvpPresenter<V>> extends MvpViewStateFragment<V, P> {

    private static final int INVALID = -1;

    public static final String FRAGMENT_ARGS_NAME = "name";
    private static final String FRAGMENT_ARGS_ATTACH_TO_ROOT = "attach_to_root";

    @SuppressWarnings("FieldNotUsedInToString")
    protected Logger logger;

    //@Inject protected WifiLightApplication app;

    protected final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Arg String name;

    private LayoutInflater layoutInflater;
    FragmentAttachmentDetails attachmentDetails;

    protected boolean viewsInitialised;

    @Arg boolean attachToRoot;
    private int orientation;

    private Handler mainThreadHandler;
    @Inject
    BaseLogger baseLogger;

    /**
     * Creates the named Fragment
     *
     * @param name the name of the Fragment to create
     * @return the created Fragment
     * @throws Exception if the name of the fragment doesn't match any in the application
     */
    public static BaseFragment create(final String name, final FragmentFactory fragmentFactory) throws Exception {
        return fragmentFactory.createFragment(name);
    }

    /**
     * Constructs a new RXFragment.
     */
    protected BaseFragment() { }

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

        mainThreadHandler = new Handler(Looper.getMainLooper());

        orientation = getResources().getConfiguration().orientation;
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater,
                                   @Nullable final ViewGroup container,
                                   @Nullable final Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);

        layoutInflater = inflater;

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logger = new Logger(Integer.toHexString(System.identityHashCode(this)) +
                " " + getClass().getName(), baseLogger);

        initialiseViews(view);

        viewsInitialised = true;

        populateViews();
    }

    @Override
    public final void onConfigurationChanged(final Configuration config) {
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
    }

    /**
     * @return the name of this Fragment
     */
    public final String name() {
        return name;
    }

    /**
     * Attaches this Fragment to the specified Activity
     *
     * @param activity the Activity to attach to
     * @return the Observable to subscribe to
     */
    public final void attachToActivity(final BaseActivity activity,
                                       final FragmentAttachmentDetails attachmentDetails) {
        this.attachmentDetails = attachmentDetails;

        doAttachToActivity(activity);
    }

    /**
     * Show toast.
     *
     * @param message the message
     */
    public final void showToast(final String message) {
        getBaseActivity().showToast(message);
    }

    /**
     * Show toast.
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
    protected final BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    /**
     * Reinitialise views.
     */
    protected final void reinitialiseViews() {
        destroyViews();

        final ViewGroup rootView = (ViewGroup) getView();
        if (rootView != null) {
            rootView.removeAllViews();
        }

        layoutInflater.inflate(getLayoutRes(), rootView);

        initialiseViews(rootView);
    }

    protected <T> void subscribe(final Observable<T> observable, Subscriber<T> subscriber) {
        compositeSubscription.add(observable.subscribe(subscriber));
        // TODO subscruption management
   }

    private void doAttachToActivity(final BaseActivity activity) {
        final String name = attachmentDetails.name();
        final int position = attachmentDetails.position();
        final boolean addToBackStack = attachmentDetails.addToBackStack();

        final FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int attachId = 0;

        if(position != INVALID) {
            attachId = activity.containerIdFromPosition(position);
        }

        if(attachId != 0) {
            final BaseFragment existingFragment = (BaseFragment) fragmentManager.findFragmentById(attachId);
            if (existingFragment != null) {// && !existingFragment.equals(this)) {
                fragmentTransaction.detach(existingFragment);
            }

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            Fragment fragment = fragmentManager.findFragmentByTag(name);
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
     * Destroy the views associated with this Fragment
     */
    protected void destroyViews() { }

    // abstract methods

    /**
     * Should this Fragment's views be reinitialised on rotation
     * @return boolean
     */
    protected abstract boolean reinitialiseOnRotate();
}
