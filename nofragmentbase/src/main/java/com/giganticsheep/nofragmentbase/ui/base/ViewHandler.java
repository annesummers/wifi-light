package com.giganticsheep.nofragmentbase.ui.base;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 11/11/15.
 */

public abstract class ViewHandler<V extends Screen.ViewInterfaceBase> implements Parcelable {

    protected List<V> views = new ArrayList<>();

    boolean hasData = false;

    void addView(V view) {
        views.add(view);

        apply();
    }

    void removeView(V view) {
        if (views.contains(view)) {
            views.remove(view);
        }

        apply();
    }

    private void apply() {
        if(hasData) {
            showData();
        }
    }

    protected void setHasData() {
        hasData = true;

        apply();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected abstract void showData();
    protected abstract V getHandlerView();
}
