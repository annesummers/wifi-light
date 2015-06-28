package com.giganticsheep.wifilight.ui.rx;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.giganticsheep.wifilight.di.modules.BaseActivityModule;
import com.giganticsheep.wifilight.Logger;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.hannesdorfmann.mosby.MosbyActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import icepick.Icicle;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public abstract class BaseActivity extends MosbyActivity {

    private static final String ATTACHED_FRAGMENTS_EXTRA = "attached_fragments_extra";

    @SuppressWarnings("FieldNotUsedInToString")
    protected Logger logger;

    @Icicle
    private final Map<Integer, FragmentAttachmentDetails> attachedFragments = new HashMap<>();
    private ActivityLayout activityLayout;
    private boolean fragmentsResumed = true;
    private final Map<BaseFragment, FragmentAttachmentDetails> fragmentAttachmentQueue = new HashMap<>();
    private Handler mainThreadHandler;

    @Inject protected BaseApplication.FragmentFactory fragmentFactory;
    @Inject BaseLogger baseLogger;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logger = new Logger(getClass().getName(), baseLogger);

        activityLayout = createActivityLayout();

        mainThreadHandler = new Handler(Looper.getMainLooper());

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

        final int containerCount = activityLayout().fragmentContainerCount();
        for (int i = 0; i < containerCount; i++) {
            if (attachedFragments.containsKey(i)) {
                final FragmentAttachmentDetails fragmentAttachmentDetails = attachedFragments.get(i);

                attachFragment(fragmentAttachmentDetails);
            }
        }
    }

    @Override
    protected void injectDependencies() {
        getWifiLightApplication().getApplicationComponent().inject(this);
    }

    private WifiLightApplication getWifiLightApplication() {
        return (WifiLightApplication)getApplication();
    }

    protected BaseActivityModule getBaseActivityModule() {
        return new BaseActivityModule(this);
    }

    BaseApplication getBaseApplication() {
        return (BaseApplication)getApplication();
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

        for(final BaseFragment fragment : fragmentAttachmentQueue.keySet()) {
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
            getSupportFragmentManager().popBackStackImmediate();
        }

        // TODO what if the fragments haven't been resumed?
    }

    /**
     * @param message the message to show in the toast
     */
    public final void showToast(final String message) {
        //mainThreadHandler.post(new Runnable() {
          // @Override
           // public void run() {
                Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
           /// }
       // });
    }

    /**
     * Queues a fragment for attachment to this Activity once the Activity is
     * in a state to have Fragments attached.
     *
     * @param fragment the Fragment to queue for attachment
     */
    public final void queueFragmentForAttachment(final BaseFragment fragment, FragmentAttachmentDetails details) {
        fragmentAttachmentQueue.put(fragment, details);
    }

    /**
     * Creates a new fragment and attached it to this Activity.
     *
     * @param attachmentDetails the details of the fragment to attach
     */
    protected final void attachNewFragment(final FragmentAttachmentDetails attachmentDetails) {
        BaseFragment fragment = null;

        try {
            fragment = BaseFragment.create(attachmentDetails.name(), fragmentFactory);

            addFragment(attachmentDetails);

            if (fragmentsResumed()) {
                fragment.attachToActivity(BaseActivity.this, attachmentDetails);
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
    protected final void attachFragment(final FragmentAttachmentDetails attachmentDetails) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        BaseFragment fragment = (BaseFragment) fragmentManager.findFragmentByTag(attachmentDetails.name());

        if(fragment == null) {
            attachNewFragment(attachmentDetails);
        } else {
            fragment.attachToActivity(this, attachmentDetails);
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
    protected BaseFragment findFragment(final int position) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        return (BaseFragment) fragmentManager.findFragmentById(containerIdFromPosition(position));
    }

    /**
     * Returns a fragment with the given fragment attach information.
     *
     * @param fragmentDetails the attach information associated with the fragment
     * @return the found fragment
     */
    protected BaseFragment findFragment(final AttachDetails fragmentDetails) {
        return findFragment(fragmentDetails.position());
    }

 //   protected <T> Observable<? extends T> bind(final Observable<? extends T> observable) {
  //      return AndroidObservable.bindActivity(this, observable);
  //  }

    private ActivityLayout activityLayout() {
        return activityLayout;
    }

    private BaseApplication getRXApplication() {
        return (BaseApplication) getApplication();
    }

    // abstract methods

    protected abstract ActivityLayout createActivityLayout();

}