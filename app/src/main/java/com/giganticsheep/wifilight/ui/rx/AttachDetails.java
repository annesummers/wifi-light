package com.giganticsheep.wifilight.ui.rx;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
class AttachDetails {
    private final RXFragment mFragment;
    private final int mPosition;
    private final boolean mAddToBackStack;
    private final String mName;

    AttachDetails(final RXFragment fragment, final String name, final int position, final boolean addtoBackStack) {
        mFragment = fragment;
        mName = name;
        mPosition = position;
        mAddToBackStack = addtoBackStack;
    }

    public final RXFragment fragment() {
        return mFragment;
    }

    public final int position() {
        return mPosition;
    }

    public final boolean addToBackStack() {
        return mAddToBackStack;
    }

    public final String name() {
        return mName;
    }

    @Override
    public final String toString() {
        return "AttachDetails{" +
                "mFragment=" + mFragment +
                ", mPosition=" + mPosition +
                ", mAddToBackStack=" + mAddToBackStack +
                ", mName='" + mName + '\'' +
                '}';
    }
}
