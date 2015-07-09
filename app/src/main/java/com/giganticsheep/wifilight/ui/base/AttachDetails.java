package com.giganticsheep.wifilight.ui.base;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
class AttachDetails {
    private final BaseFragment fragment;
    private final int position;
    private final boolean addToBackStack;
    private final String name;

    AttachDetails(final BaseFragment fragment,
                  final String name,
                  final int position,
                  final boolean addToBackStack) {
        this.fragment = fragment;
        this.name = name;
        this.position = position;
        this.addToBackStack = addToBackStack;
    }

    public final BaseFragment fragment() {
        return fragment;
    }

    public final int position() {
        return position;
    }

    public final boolean addToBackStack() {
        return addToBackStack;
    }

    public final String name() {
        return name;
    }

    @Override
    public String toString() {
        return "AttachDetails{" +
                "fragment=" + fragment +
                ", position=" + position +
                ", addToBackStack=" + addToBackStack +
                ", name='" + name + '\'' +
                '}';
    }
}
