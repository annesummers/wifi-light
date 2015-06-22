package com.giganticsheep.wifilight.ui.rx;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giganticsheep.wifilight.WifiLightApplication;

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

    private String mName;

    private final RXSubscriptionManager mSubscriptions;
    private AttachDetails mAttachDetails;
    private LayoutInflater mLayoutInflater;

    private RXActivity mActivity;

    private boolean mShowAsDialog;
    private boolean mAttachToRoot;
    private int mOrientation;

    /**
     * Constructs a new RXFragment.
     */
    protected RXFragment() {
        mSubscriptions = new RXSubscriptionManager(this);
    }

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logDebug(" onCreate()");

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


        mLayoutInflater = inflater;

        View view = mLayoutInflater.inflate(layoutId(), container, isAttachedToRoot());

        //setupViews(view);
        initialiseViews(view);

        return view;
    }

    protected abstract int layoutId();

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
     * Attaches this Fragment to the specified Activity
     *
     * @param activity the Activity to attach to
     * @param position the position to attach to
     * @param addtoBackStack whether to add the fragment to the Activity's backstack
     * @return the Observable to subscribe to
     */
    public Observable<RXFragment> attachToActivity(final RXActivity activity, final String name, final int position, final boolean addtoBackStack) {
        return Observable.just(doAttachToActivity(activity, name, position, addtoBackStack))
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Attaches this Fragment to the specified Activity
     *
     * @param activity the Activity to attach to@return the Observable to subscribe to
     */
    public Observable<RXFragment> attachToActivity(final RXActivity activity) {
        return Observable.just(doAttachToActivity(activity))
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Lays out the Fragment UI elements
     *
     * @param configuration the current Configuration
     * @return the Observable to subscribe to
     */
    public abstract Observable<RXFragment> layoutScreen(Configuration configuration);

    /**
     * Hides this fragment.
     *
     */
    public void hide() {
        if(activityExists() && activity().fragmentsResumed()) {
            FragmentManager fragmentManager = mActivity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(this);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);

        if(reinitialiseOnRotate() && config.orientation != mOrientation) {
            mOrientation = config.orientation;
            reinitialiseViews();
        }
    }

    protected abstract boolean reinitialiseOnRotate();

    /**
     * Reinitialise views.
     */
    protected void reinitialiseViews() {
        destroyViews();

        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();

        mLayoutInflater.inflate(layoutId(), rootView);

        initialiseViews(rootView);
    }

    /**
     * Initialises the views associated with this Fragment
     *
     */
    protected abstract void initialiseViews(View view);

    /**
     * Destroy views.
     */
    protected void destroyViews() { }

    /**
     * Sets screen.
     *
     * @param newConfig the new config
     */
    protected void setupScreen(Configuration newConfig) {
    }

    /**
     * Show toast.
     *
     * @param message the message
     */
    public final void showToast(String message) {
        activity().showToast(message);
    }

    /**
     * Show toast.
     *
     * @param message the message
     */
    final public void showToast(int message) {
        activity().showToast(getString(message));
    }

    /**
     * Is activity valid.
     *
     * @return the boolean
     */
    final protected boolean isActivityValid() {
        return getActivity() != null && !getActivity().isFinishing();
    }

    private RXFragment doAttachToActivity(final RXActivity activity, String name, int position, boolean addToBackStack) {
        mActivity = activity;
        mAttachDetails = new AttachDetails(this, name, position, addToBackStack);

        activity().addFragment(name, position, addToBackStack);

        if(activity.fragmentsResumed()) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            /*if (oldFragment != null) {
                if (newFragment == null) {
                    newFragment = oldFragment;
                }

                fragmentTransaction.remove(oldFragment);
            }*/

            int attachId = 0;

            if(position != INVALID) {
                attachId = activity().attachIdFromPosition(position);
            }

            if(attachId != 0) {
                RXFragment existingFragment = (RXFragment) fragmentManager.findFragmentById(attachId);
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

    public String name() {
        return mName;
    }

    private RXFragment doAttachToActivity(RXActivity activity) {
        return doAttachToActivity(activity,
                mAttachDetails.name(),
                mAttachDetails.position(),
                mAttachDetails.addToBackStack());
    }

    private RXFragment doAttachToActivity() {
        return doAttachToActivity(activity(),
                mAttachDetails.name(),
                mAttachDetails.position(),
                mAttachDetails.addToBackStack());
    }

    private boolean activityExists() {
        return activity() != null;
    }

    /**
     * @return the Activity associated with this Fragment
     */
    protected final RXActivity activity() {
        return mActivity;
    }

    /**
     * @return Whether this Fragment is attached to the Activity root or not
     */
    protected final boolean isAttachedToRoot() {
        return mAttachToRoot;
    }

    public void logWarn(final String message) {
        WifiLightApplication.application().logWarn(toString() + " " + message);
    }

    public void logError(String message) {
        WifiLightApplication.application().logError(toString() + " " + message);
    }

    public void logInfo(String message) {
        WifiLightApplication.application().logInfo(toString() + " " + message);
    }

    public void logDebug(String message) {
        WifiLightApplication.application().logDebug(toString() + " " + message);
    }

    class AttachDetails {
        private RXFragment mFragment;
        private int mPosition;
        private boolean mAddToBackStack;
        private String mName;

        public AttachDetails(final RXFragment fragment, final String name, final int position, final boolean addtoBackStack) {
            mFragment = fragment;
            mName = name;
            mPosition = position;
            mAddToBackStack = addtoBackStack;
        }

        public RXFragment fragment() {
            return mFragment;
        }

        public int position() {
            return mPosition;
        }

        public boolean addToBackStack() {
            return mAddToBackStack;
        }

        public String name() {
            return mName;
        }
    }

    @Override
    public String toString() {
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
