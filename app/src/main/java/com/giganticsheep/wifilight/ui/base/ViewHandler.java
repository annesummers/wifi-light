package com.giganticsheep.wifilight.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by anne on 27/10/15.
 */
public class ViewHandler<V extends ViewBase, S extends ViewStateBase<V>, P extends PresenterBase<V>> implements ViewBase {
    static final String KEY_STATE = "key_state";
    static final String KEY_ERROR = "key_error";

    final public static int STATE_SHOW_LOADING = 0;
    final public static int STATE_SHOW_ERROR = 1;

    protected static final int STATE_MAX = STATE_SHOW_ERROR;
    private static final String KEY_APPLY = "key_apply";
    private final FrameworkCreator<S, P> creator;

    protected int state = STATE_SHOW_LOADING;

    private Throwable error;

    private P presenter;
    private WeakReference<V> viewRef;
    private S viewState;
    private boolean retainingInstanceState;
    private boolean applyViewState;
    private boolean restoringViewState;

    public ViewHandler(V view, FrameworkCreator<S, P> creator) {
        setView(view);
        setPresenter(creator.createPresenter());

        this.creator = creator;
    }

    public void setView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    /**
     * Sets the state to STATE_SHOW_LOADING.
     */
    private void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    /**
     * Sets the state to STATE_SHOW_ERROR.
     */
    private void setShowError() {
        state = STATE_SHOW_ERROR;
    }

    /**
     * Sets the state to STATE_SHOW_ERROR.
     */
    private void setShowError(Throwable error) {
        this.error = error;
        state = STATE_SHOW_ERROR;
    }

    public void apply(@NonNull final ViewBase lightView,
                      final boolean retained) {
        switch (state) {
            case STATE_SHOW_LOADING:
                lightView.showLoading();
                break;

            case STATE_SHOW_ERROR:
                if(error != null) {
                    lightView.showError(error);
                } else {
                    lightView.showError();
                }
                break;

            default:
                break;
        }
    }

    public void saveInstanceState(Bundle bundle) {
        saveViewState(bundle);

        bundle.putInt(KEY_STATE, state);
        bundle.putSerializable(KEY_ERROR, error);
        bundle.putBoolean(KEY_APPLY, applyViewState);
    }

    public ViewHandler<V, S, P> restoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        state = bundle.getInt(KEY_STATE);
        error = (Throwable) bundle.getSerializable(KEY_ERROR);
        applyViewState = bundle.getBoolean(KEY_APPLY);

        createOrRestoreViewState(bundle);
        applyViewState();

        return this;
    }

    @Override
    public void showLoading() {
        setShowLoading();

        getView().showLoading();
    }

    @Override
    public void showError() {
        setShowError();

        getView().showError();
    }

    @Override
    public void showError(Throwable throwable) {
        setShowError(throwable);

        getView().showError(throwable);
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
        this.presenter.attachView((V) this);
    }

    protected V getView() {
        if(viewRef == null || viewRef.get() == null) {
            //throw new ViewNotAttachedException();
            return null;
        }
        return viewRef.get();
    }

    public P getPresenter() {
        return presenter;
    }

    private boolean createOrRestoreViewState(Bundle savedInstanceState) {
         if (getViewState() != null) {
              retainingInstanceState = true;
      //  applyViewState = true;
             return true;
         }


        // Create view state
        viewState = creator.createViewState();
        if (getViewState() == null) {
            throw new NullPointerException(
                    "ViewState is null! Do you return null in createViewState() method?");
        }

        // Try to restore data from bundle (savedInstanceState)
        if (savedInstanceState != null) {

            ViewStateBase restoredViewState =
                    getViewState().restoreInstanceState(savedInstanceState);

            boolean restoredFromBundle = restoredViewState != null;

            if (restoredFromBundle) {
                viewState = (S) restoredViewState;
                retainingInstanceState = false;
              //  applyViewState = true;
                return true;
            }
        }

        // ViewState not restored, activity / fragment starting first time
       //  applyViewState = false;
        return false;
    }

    public S getViewState() {
        return viewState;
    }

    private boolean applyViewState() {
        if (applyViewState) {
            setRestoringViewState(true);
            getViewState().apply(getView(), retainingInstanceState);
            setRestoringViewState(false);
            creator.onViewStateInstanceRestored(retainingInstanceState);
            return true;
        }

        creator.onNewViewStateInstance();
        return false;
    }

    /**
     * Called to detach the view from presenter
     */
    private void detachView() {
        P presenter = getPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        }
        presenter.detachView(creator.isRetainingInstance());
    }

    protected void setRestoringViewState(boolean restoring) {
        restoringViewState = restoring;
    }
    protected boolean isRestoringViewState() {
        return restoringViewState;
    }

    public void saveViewState(Bundle outState) {
        boolean retainingInstanceState = creator.isRetainingInstance();

        S viewState = getViewState();
        if (viewState == null) {
            throw new NullPointerException("ViewState is null! That's not allowed");
        }

        applyViewState = true;

        // Save the viewstate
        if (!retainingInstanceState) {
            ((ViewStateBase) viewState).saveInstanceState(outState);
        }
    }

    public void configurationChanged() {

        createOrRestoreViewState(null);
        applyViewState();
    }
}
