package com.giganticsheep.wifilight.ui.rx;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giganticsheep.wifilight.Logger;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


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
    protected final Logger mLogger = new Logger(getClass().getName());

    private String mName;

    private final RXSubscriptionManager mSubscriptions;
    private AttachDetails mAttachDetails;
    private LayoutInflater mLayoutInflater;

    private RXActivity mActivity;

    private boolean mShowAsDialog;
    private boolean mAttachToRoot;
    private int mOrientation;

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
    protected RXFragment() {
        mSubscriptions = new RXSubscriptionManager(this);
    }

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mLogger.debug(" onCreate()");

        final Bundle args = getArguments();

        if(args != null) {
            if(mName == null) {
                mName = args.getString(FRAGMENT_ARGS_NAME);
            }

            mShowAsDialog = args.getBoolean(FRAGMENT_ARGS_SHOW_AS_DIALOG, false);
            mAttachToRoot = args.getBoolean(FRAGMENT_ARGS_ATTACH_TO_ROOT, false);
        }

       /* if(mShowAsDialog) {
            setStyle(PureSoloUIEngine.engine().dialogStyle(),
                    PureSoloUIEngine.engine().dialogTheme());
        }*/

        mOrientation = getResources().getConfiguration().orientation;

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

        mLayoutInflater = inflater;

        final View view = mLayoutInflater.inflate(layoutId(), container, mAttachToRoot);

        initialiseViews(view);

        return view;
    }

    @Override
    public final void onConfigurationChanged(final Configuration config) {
        super.onConfigurationChanged(config);

        if(reinitialiseOnRotate() && config.orientation != mOrientation) {
            mOrientation = config.orientation;
            reinitialiseViews();
        }
    }

    /**
     * @return the name of this Fragment
     */
    public final String name() {
        return mName;
    }

    /**
     * Attaches this Fragment to the specified Activity
     *
     * @param activity the Activity to attach to
     * @param position the position to attach to
     * @param addToBackStack whether to add the fragment to the Activity's backstack
     * @return the Observable to subscribe to
     */
    public final Observable<RXFragment> attachToActivity(final RXActivity activity,
                                                         final String name,
                                                         final int position,
                                                         final boolean addToBackStack) {
        mAttachDetails = new AttachDetails(this, name, position, addToBackStack);

        return Observable.just(doAttachToActivity(activity))
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Attaches this Fragment to the specified Activity
     *
     * @param activity the Activity to attach to@return the Observable to subscribe to
     */
    public final Observable<RXFragment> attachToActivity(final RXActivity activity) {
        return Observable.just(doAttachToActivity(activity))
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Hides this fragment.
     *
     */
    public final void hide() {
        if(activityExists() && activity().fragmentsResumed()) {
            final FragmentManager fragmentManager = mActivity.getFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(this);
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
        return mAttachToRoot;
    }

    /**
     * @return the Activity associated with this Fragment
     */
    protected final RXActivity activity() {
        return mActivity;
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

        mLayoutInflater.inflate(layoutId(), rootView);

        initialiseViews(rootView);
    }

    private RXFragment doAttachToActivity(final RXActivity activity) {
        mActivity = activity;

        final String name = mAttachDetails.name();
        final int position = mAttachDetails.position();
        final boolean addToBackStack = mAttachDetails.addToBackStack();

        activity().addFragment(name, position, addToBackStack);

        if(mActivity.fragmentsResumed()) {
            final FragmentManager fragmentManager = mActivity.getFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            int attachId = 0;

            if(position != INVALID) {
                attachId = activity().containerIdFromPosition(position);
            }

            if(attachId != 0) {
                final RXFragment existingFragment = (RXFragment) fragmentManager.findFragmentById(attachId);
                if (existingFragment != null) {
                    fragmentTransaction.remove(existingFragment);
                }

                fragmentTransaction.add(attachId, this);

                if(existingFragment != null) {
                    fragmentTransaction.add(existingFragment, existingFragment.name());
                }
            }

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.commit();

         //   layoutScreen(getResources().getConfiguration());
        } else {
            activity().queueFragmentForAttachment(this);
        }

        //getWindow().setTitle(activityTitle());

        return this;
    }

    private boolean activityExists() {
        return activity() != null;
    }

    // abstract methods

    /**
     * Lays out the Fragment UI elements
     *
     * @param configuration the current Configuration
     * @return the Observable to subscribe to
     */
    public abstract Observable<RXFragment> layoutScreen(Configuration configuration);

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

    @Override
    public final String toString() {
        return "RXFragment{" +
                "mSubscriptions=" + mSubscriptions +
                ", mAttachDetails=" + mAttachDetails +
                ", mActivity=" + mActivity +
                ", mName='" + mName + '\'' +
                ", mShowAsDialog=" + mShowAsDialog +
                ", mAttachToRoot=" + mAttachToRoot +
                ", mLayoutInflater=" + mLayoutInflater +
                ", mOrientation=" + mOrientation +
                '}';
    }
}
