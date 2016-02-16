package com.giganticsheep.nofragmentbase.ui.base;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
/**
 * Created by anne on 24/11/15.
 */
public abstract class GridRecyclerViewRelativeLayoutContainer extends RelativeLayoutContainer {

    public GridRecyclerViewRelativeLayoutContainer(Context context) {
        super(context);
    }

    public GridRecyclerViewRelativeLayoutContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void showData() {
        getRecyclerView().getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void setupViews() {
        getRecyclerView().setLayoutManager(createLayoutManager());
    }

    protected abstract RecyclerView.LayoutManager createLayoutManager();
    protected abstract RecyclerView getRecyclerView();

    static class SavedState extends BaseSavedState {
        private Parcelable recyclerState;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);

            this.recyclerState = in.readParcelable(GridLayoutManager.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);

            out.writeParcelable(this.recyclerState, 0);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }


    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);

        ss.recyclerState = getRecyclerView().getLayoutManager().onSaveInstanceState();

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState)state;
        super.onRestoreInstanceState(ss.getSuperState());

        getRecyclerView().getLayoutManager().onRestoreInstanceState(ss.recyclerState);
    }
}
