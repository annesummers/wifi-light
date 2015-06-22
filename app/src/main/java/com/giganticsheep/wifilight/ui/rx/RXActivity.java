package com.giganticsheep.wifilight.ui.rx;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by anne on 22/06/15.
 */
public abstract class RXActivity extends ActionBarActivity {

    private static final String ATTACHED_FRAGMENTS_EXTRA = "attached_fragments_extra";

    private final Map<Integer, FragmentAttachmentDetails> mAttachedFragments = new HashMap<>();
    private ActivityLayout mActivityLayout;
    private boolean mFragmentsResumed = true;
    private final List<RXFragment> mFragmentAttachmentQueue = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityLayout = createActivityLayout();

        if(savedInstanceState != null) {
            for (int i = 0; i < activityLayout().fragmentContainerCount(); i++) {
                if (mAttachedFragments.containsKey(i)) {
                    FragmentAttachmentDetails fragmentAttachmentDetails = mAttachedFragments.get(i);

                    attachNewFragment(fragmentAttachmentDetails.name(),
                            fragmentAttachmentDetails.position(),
                            fragmentAttachmentDetails.addToBackStack());
                }
            }
        }

        initialiseViews();
    }

    private RXApplication getRXApplication() {
        return (RXApplication) getApplication();
    }

    protected void attachNewFragment(final String name, final int position, final boolean addToBackStack) {
        RXFragment.create(name, getRXApplication())
                .flatMap(new Func1<RXFragment, Observable<?>>() {
                    @Override
                    public Observable<RXFragment> call(RXFragment fragment) {
                        return fragment.attachToActivity(RXActivity.this, name, position, addToBackStack);
                    }
                })
                .subscribe();
    }

    protected abstract void initialiseViews();

    @Override
    public final void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        final FragmentAttachmentDetails[] fragments = mAttachedFragments.values().toArray(
                                                            new FragmentAttachmentDetails[mAttachedFragments.size()]);

        outState.putParcelableArray(ATTACHED_FRAGMENTS_EXTRA, fragments);

        mFragmentsResumed = false;
    }

    private ActivityLayout activityLayout() {
        return mActivityLayout;
    }

    protected abstract ActivityLayout createActivityLayout();

    /**
     * Adds a fragment to the hash of attached fragments.  This does not actually attach the fragment.
     *
     * @param name the name of the attached Fragment
     * @param position the position of the attached Fragment
     */
    void addFragment(String name, int position, boolean addToBackStack) {
        if(mAttachedFragments.containsKey(position)) {
            String oldName = mAttachedFragments.get(position).name();
            if(oldName.equals(name)) {
                return;
            } else {
                mAttachedFragments.remove(position);
            }
        }

        mAttachedFragments.put(position, new FragmentAttachmentDetails(position, name, addToBackStack));
    }

    @Override
     final public void onResumeFragments() {
        super.onResumeFragments();

        mFragmentsResumed = true;

        for(RXFragment fragment : mFragmentAttachmentQueue) {
            fragment.attachToActivity(this);
            mFragmentAttachmentQueue.remove(fragment);
        }
    }

    public boolean fragmentsResumed() {
        return mFragmentsResumed;
    }

    int attachIdFromPosition(int position) {
        return mActivityLayout.fragmentContainer(position);
    }

    /**
     * Returns a fragment at a given position.
     *
     * @param position the position the fragment is attached at
     * @return the found fragment
     */
    protected synchronized RXFragment findFragment(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        RXFragment fragment = (RXFragment) fragmentManager.findFragmentById(attachIdFromPosition(position));

        return fragment;
    }

    /**
     * Returns a fragment with the given fragment attach information.
     *
     * @param fragmentDetails the attach information associated with the fragment
     * @return the found fragment
     */
    protected synchronized RXFragment findFragment(RXFragment.AttachDetails fragmentDetails) {
        return findFragment(fragmentDetails.position());
    }

    /**
     * Pops the backstack.
     */
    public void popBackStack() {
        if (fragmentsResumed()) {
            getFragmentManager().popBackStackImmediate();
        }

        // TODO what if the fragments haven't been resumed?
    }

    public void showToast(String string) {

    }

    public void queueFragmentForAttachment(RXFragment attachDetails) {
        mFragmentAttachmentQueue.add(attachDetails);
    }

    private static class FragmentAttachmentDetails implements Parcelable {
        private final int mPosition;
        private final String mName;
        private boolean mAddToBackStack;

        public FragmentAttachmentDetails(int position, String name, boolean addToBackStack) {
            mPosition = position;
            mName = name;
            mAddToBackStack = addToBackStack;
        }

        protected FragmentAttachmentDetails(Parcel in) {
            mPosition = in.readInt();
            mName = in.readString();
            mAddToBackStack = in.readByte() != 0;
        }

        public static final Parcelable.Creator<FragmentAttachmentDetails> CREATOR = new Parcelable.Creator<FragmentAttachmentDetails>() {
            @Override
            public FragmentAttachmentDetails createFromParcel(Parcel source) {
                return new FragmentAttachmentDetails(source);
            }

            @Override
            public FragmentAttachmentDetails[] newArray(int size) {
                return new FragmentAttachmentDetails[size];
            }
        };

        public String name() {
            return mName;
        }

        public int position() {
            return mPosition;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeString(mName);
            parcel.writeInt(mPosition);
            parcel.writeByte((byte) (mAddToBackStack ? 1 : 0));
        }

        public boolean addToBackStack() {
            return mAddToBackStack;
        }
    }
}
