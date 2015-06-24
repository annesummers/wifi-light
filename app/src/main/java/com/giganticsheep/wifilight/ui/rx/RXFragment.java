package com.giganticsheep.wifilight.ui.rx;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giganticsheep.wifilight.Logger;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.model.LightNetwork;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public abstract class RXFragment extends Fragment {

    private static final int INVALID = -1;

    public static final String FRAGMENT_ARGS_NAME = "name";
    private static final String FRAGMENT_ARGS_SHOW_AS_DIALOG = "show_as_dialog";
    private static final String FRAGMENT_ARGS_ATTACH_TO_ROOT = "attach_to_root";

    @SuppressWarnings("FieldNotUsedInToString")
    protected final Logger logger = new Logger(Integer.toHexString(System.identityHashCode(this)) +
            " " + getClass().getName());

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    private String name;

    private LayoutInflater layoutInflater;
    RXActivity.FragmentAttachmentDetails attachmentDetails;

    private RXActivity activity;

    private boolean showAsDialog;
    private boolean attachToRoot;
    private int orientation;

    private Handler mainThreadHandler;

    /**
     * Creates the named Fragment
     *
     * @param name the name of the Fragment to create
     * @return the Observable to subscribe to
     */
    public static Observable<? extends RXFragment> create(final String name, final RXApplication application) {
        return application.createFragment(name);
    }

    /**
     * Constructs a new RXFragment.
     */
    protected RXFragment() { }

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();

        if(args != null) {
            if(name == null) {
                name = args.getString(FRAGMENT_ARGS_NAME);
            }

            showAsDialog = args.getBoolean(FRAGMENT_ARGS_SHOW_AS_DIALOG, false);
            attachToRoot = args.getBoolean(FRAGMENT_ARGS_ATTACH_TO_ROOT, false);
        }

        mainThreadHandler = new Handler(Looper.getMainLooper());

       /* if(showAsDialog) {
            setStyle(PureSoloUIEngine.engine().dialogStyle(),
                    PureSoloUIEngine.engine().dialogTheme());
        }*/

        orientation = getResources().getConfiguration().orientation;

       // setupScreen(getResources().getConfiguration());

       /* if(savedInstanceState != null) {
            handleRestoreInstanceState(savedInstanceState);
        }*/
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater,
                                   final ViewGroup container,
                                   final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        layoutInflater = inflater;

        final View view = layoutInflater.inflate(layoutId(), container, attachToRoot);

        initialiseViews(view);

        return view;
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();

        destroyViews();

        compositeSubscription.unsubscribe();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public final void onConfigurationChanged(final Configuration config) {
        super.onConfigurationChanged(config);

        if(reinitialiseOnRotate() && config.orientation != orientation) {
            orientation = config.orientation;
            reinitialiseViews();
        }
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
    public final void attachToActivity(final RXActivity activity,
                                       final RXActivity.FragmentAttachmentDetails attachmentDetails) {
        this.attachmentDetails = attachmentDetails;

        doAttachToActivity(activity);
    }

    /**
     * Hides this fragment.
     *
     */
    public final void hide() {
        if(activityExists() && activity().fragmentsResumed()) {
            final FragmentManager fragmentManager = activity.getFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(this);
            fragmentTransaction.commit();
        }
    }

    /**
     * Show toast.
     *
     * @param message the message
     */
    public final void showToast(final String message) {
        activity().showToast(message);
    }

    /**
     * Show toast.
     *
     * @param message the message
     */
    public final void showToast(final int message) {
        activity().showToast(getString(message));
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
    protected final RXActivity activity() {
        return activity;
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

        layoutInflater.inflate(layoutId(), rootView);

        initialiseViews(rootView);
    }

    protected <T> Observable<? extends T> bind(final Observable<? extends T> observable) {
        return AndroidObservable.bindFragment(this, observable);
    }

    private void doAttachToActivity(final RXActivity activity) {
        this.activity = activity;

        final int position = attachmentDetails.position();
        final boolean addToBackStack = attachmentDetails.addToBackStack();

        final FragmentManager fragmentManager = this.activity.getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int attachId = 0;

        if(position != INVALID) {
            attachId = activity().containerIdFromPosition(position);
        }

        if(attachId != 0) {
            final RXFragment existingFragment = (RXFragment) fragmentManager.findFragmentById(attachId);
            if (existingFragment != null) {
               // fragmentTransaction.detach(existingFragment);
                fragmentTransaction.remove(existingFragment);
            }

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            //if(fragmentManager.findFragmentByTag(name()).equals(this)) {
            //    fragmentTransaction.attach(this);
            //} else {
                fragmentTransaction.add(attachId, this, name());
            //}
        }

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    private boolean activityExists() {
        return activity() != null;
    }

    // abstract methods

    /**
     * @return the resource id of the layout for this Fragment
     */
    protected abstract int layoutId();

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
     * Destroy the views associated with this Fragment
     *
     */
    protected abstract void destroyViews();
}
