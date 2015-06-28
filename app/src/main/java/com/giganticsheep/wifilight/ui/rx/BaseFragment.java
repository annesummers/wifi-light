package com.giganticsheep.wifilight.ui.rx;

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

import com.giganticsheep.wifilight.Logger;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.MosbyFragment;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public abstract class BaseFragment extends MosbyFragment {

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
    @Inject BaseLogger baseLogger;

    /**
     * Creates the named Fragment
     *
     * @param name the name of the Fragment to create
     * @return the created Fragment
     * @throws Exception if the name of the fragment doesn't match any in the application
     */
    public static BaseFragment create(final String name, final BaseApplication.FragmentFactory fragmentFactory) throws Exception {
        return fragmentFactory.createFragment(name);
    }

    /**
     * Constructs a new RXFragment.
     */
    protected BaseFragment() { }

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
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

        initialiseData(savedInstanceState);
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

    protected <T> void subscribe(final Observable<? extends T> observable, Subscriber<? extends T> subscriber) {
       // compositeSubscription.add(observable.subscribe(subscriber));
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
            if (existingFragment != null && !existingFragment.equals(this)) {
                fragmentTransaction.detach(existingFragment);
            } else {
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
        }

        fragmentTransaction.commit();
    }

    private boolean activityExists() {
        return getActivity() != null;
    }

    // abstract methods

    protected abstract void initialiseData(Bundle savedInstanceState);

    /**
     * Should this Fragment's views be reinitialised on rotation
     *
     */
    protected abstract boolean reinitialiseOnRotate();

    /**
     * Initialises the views associated with this Fragment
     *
     */
    protected abstract void initialiseViews(View view);

    /**
     * Populates the views associated with this Fragment
     *
     */
    protected abstract void populateViews();

    /**
     * Destroy the views associated with this Fragment
     *
     */
    protected abstract void destroyViews();
}