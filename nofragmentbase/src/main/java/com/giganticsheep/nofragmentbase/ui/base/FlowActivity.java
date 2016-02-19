package com.giganticsheep.nofragmentbase.ui.base;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.giganticsheep.nofragmentbase.R;

import butterknife.ButterKnife;
import de.greenrobot.event.NoSubscriberEvent;
import flow.Flow;
import flow.FlowDelegate;
import flow.History;

/**
 * Created by anne on 14/11/15.
 */
public abstract class FlowActivity<G extends ScreenGroup> extends AppCompatActivity {

    private FlowDelegate flowDelegate;
    private G screenGroup;

    private AlertDialog alertDialog;
    private AlertDialog errorDialog;

    private boolean errorShowing;
    private boolean isShowing;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //EventBus.getDefault().register(this);

        setContentView(layoutId());

        createComponent();

        //ButterKnife.bind(this);
        ButterKnife.inject(this);

        final LayoutTransition layoutTransition = new LayoutTransition();

        initialiseViews();

        screenGroup = createScreenGroup();
        screenGroup.registerForEvents(this);

        flowDelegate = FlowDelegate.onCreate(
                (FlowDelegate.NonConfigurationInstance) getLastCustomNonConfigurationInstance(),
                getIntent(),
                savedInstanceState,
                new Parceler(),
                History.single(getInitialScreen()),
                new Flow.Dispatcher() {
                    @Override
                    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
                        final Screen screen = traversal.destination.top();
                        final Screen lastScreen = traversal.origin.top();
                        // while(lastScreen.notInHistory()) {
                        ////       lastScreen = traversal.origin.top();
                        //  }

                        if (screen != lastScreen) {
                            final View view = screen.inflateView(FlowActivity.this, getMainContainer());

                            int inAnimation = screen.getInAnimation();
                            int outAnimation = lastScreen.getOutAnimation();
                            Animator inAnimator = null;
                            Animator outAnimator = null;

                            if(inAnimation > 0) {
                                inAnimator = AnimatorInflater.loadAnimator(FlowActivity.this, inAnimation);
                            }

                            if(outAnimation > 0) {
                                outAnimator = AnimatorInflater.loadAnimator(FlowActivity.this, outAnimation);
                            }

                            final Animator finalInAnimator = inAnimator;
                            final Animator finalOutAnimator = outAnimator;

                            /*layoutTransition.disableTransitionType(LayoutTransition.APPEARING);
                            layoutTransition.disableTransitionType(LayoutTransition.DISAPPEARING);
                            layoutTransition.disableTransitionType(LayoutTransition.CHANGE_APPEARING);
                            layoutTransition.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
                            getMainContainer().setLayoutTransition(layoutTransition);*/

                            int childCount = getMainContainer().getChildCount();
                            int additionalScreenCount = additionalScreenCount();
                            if (childCount == additionalScreenCount + 1) {
                                if(finalOutAnimator != null) {
                                    finalOutAnimator.setTarget(getMainContainer().getChildAt(additionalScreenCount()));
                                    finalOutAnimator.addListener(new Animator.AnimatorListener() {

                                        @Override
                                        public void onAnimationStart(Animator animation) { }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            lastScreen.hide();

                                            addNewView(view, finalInAnimator);
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) { }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) { }
                                    });
                                }

                                getMainContainer().removeViewAt(additionalScreenCount());

                                if(finalOutAnimator != null) {
                                    finalOutAnimator.start();
                                } else {
                                    lastScreen.hide();

                                    addNewView(view, finalInAnimator);
                                }
                            }
                        } else {
                            int childCount = getMainContainer().getChildCount();
                            int additionalScreenCount = additionalScreenCount();
                            if (childCount == additionalScreenCount) {
                                final View view = screen.inflateView(FlowActivity.this, getMainContainer());

                                int inAnimation = screen.getInAnimation();
                                Animator inAnimator = null;

                                if (inAnimation > 0) {
                                    inAnimator = AnimatorInflater.loadAnimator(FlowActivity.this, inAnimation);
                                }

                              /*  layoutTransition.disableTransitionType(LayoutTransition.DISAPPEARING);
                                layoutTransition.disableTransitionType(LayoutTransition.APPEARING);
                                layoutTransition.disableTransitionType(LayoutTransition.CHANGE_APPEARING);
                                layoutTransition.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
                                getMainContainer().setLayoutTransition(layoutTransition);*/

                                addNewView(view, inAnimator);
                            }
                        }

                        callback.onTraversalCompleted();
                    }
                });

        onCreated();
    }

    private void addNewView(View view, Animator inAnimator) {
        getMainContainer().addView(view);

        if(inAnimator != null) {
            inAnimator.setTarget(view);
            inAnimator.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //EventBus.getDefault().unregister(this);
        screenGroup.unRegisterForEvents(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        flowDelegate.onResume();

        isShowing = true;

        if (errorShowing && errorDialog != null) {
            errorDialog.show();
        }

        if (alertDialog != null) {
            alertDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        flowDelegate.onPause();

        isShowing = false;

        if (errorDialog != null) {
            errorDialog.dismiss();
        }

        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    // Flow

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        super.onRetainCustomNonConfigurationInstance();

        return flowDelegate.onRetainNonConfigurationInstance();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        flowDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if(!flowDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public Object getSystemService(String name) {
        if(flowDelegate == null) {
            return super.getSystemService(name);
        }
        Object service = flowDelegate.getSystemService(name);
        return service != null ? service : super.getSystemService(name);
    }

    // Getters

    public boolean isOnScreen() {
        return isShowing;
    }

    protected G getScreenGroup() {
        return screenGroup;
    }

    // Event handling

    public void onEvent(NoSubscriberEvent unused) { }

    public void onEvent(ShowErrorEvent errorEvent) {
        showErrorDialog(errorEvent.getErrorString(this), errorEvent.getClickListener());
    }

    private void showErrorDialog(String errorMessage) {
        showErrorDialog(errorMessage, null);
    }

    private void showErrorDialog(String errorMessage, final DialogInterface.OnClickListener clickListener) {
        errorShowing = true;

        if (errorDialog != null) {
            errorDialog.hide();
            errorDialog.setMessage(errorMessage);
        } else {
            AlertDialog.Builder alertBuilder = getThemedDialogBuilder();
            alertBuilder.setMessage(errorMessage);
            alertBuilder.setTitle(getString(R.string.error));
            alertBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    screenGroup.postControlEvent(new DismissErrorDialogEvent());
                    dialog.dismiss();
                    errorShowing = false;

                    if (clickListener != null) {
                        clickListener.onClick(dialog, which);
                    }
                }
            });

            errorDialog = alertBuilder.create();
            errorDialog.setOnShowListener(new DialogOnShowListener(this, errorDialog));
        }

        if (isOnScreen()) {
            errorDialog.show();
        }
    }

    protected void showDialog(int title, int message, DialogInterface.OnClickListener clickListener) {
        showDialog(getString(title), getString(message), 0, clickListener, 0, null);
    }

    protected void showDialog(int title, int message,
                              DialogInterface.OnClickListener positiveClickListener,
                              DialogInterface.OnClickListener negativeClickListener) {
        showDialog(getString(title), getString(message), 0, positiveClickListener, 0, negativeClickListener);
    }

    private void showDialog(String title, String message, int positiveLabel, final DialogInterface.OnClickListener positiveClickListener, int negativeLabel, final DialogInterface.OnClickListener negativeClickListener) {
        AlertDialog.Builder alertBuilder = getThemedDialogBuilder();
        alertBuilder.setMessage(message);
        alertBuilder.setTitle(title);
        alertBuilder.setPositiveButton(positiveLabel == 0 ? android.R.string.ok : positiveLabel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        alertDialog = null;

                        if (positiveClickListener != null) {
                            positiveClickListener.onClick(dialog, which);
                        }
                    }
                });

        if (negativeClickListener != null || negativeLabel != 0) {
            alertBuilder.setNegativeButton(negativeLabel == 0 ? android.R.string.cancel : negativeLabel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            alertDialog = null;

                            if (negativeClickListener != null) {
                                negativeClickListener.onClick(dialog, which);
                            }
                        }
                    });
        }

        alertDialog = alertBuilder.create();
        alertDialog.setOnShowListener(new DialogOnShowListener(this, alertDialog));

        if (isOnScreen()) {
            alertDialog.show();
        }

        //Refresh message view to hyperlink URLs.
        TextView messageTextView = (TextView) alertDialog.getWindow().findViewById(android.R.id.message);
        messageTextView.setAutoLinkMask(Linkify.WEB_URLS);
        messageTextView.setText(message);
    }

    protected AlertDialog.Builder getThemedDialogBuilder() {
        ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.Base_Theme_AppCompat_Light_Dialog);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctw);
        alertBuilder.setCancelable(true);

        return alertBuilder;
    }

    // Abstract methods

    protected abstract int layoutId();

    protected abstract void createComponent();
    protected abstract G createScreenGroup();
    protected abstract void initialiseViews();
    protected abstract Screen getInitialScreen();

    protected abstract void onCreated();

    protected abstract int additionalScreenCount();
    protected abstract ViewGroup getMainContainer();

    // Events

    public static class ShowErrorEvent {
        private final Throwable error;
        private final String errorString;
        private final int messageId;

        private final DialogInterface.OnClickListener clickListener;

        public ShowErrorEvent(Throwable error) {
            this.error = error;
            this.errorString = "";
            this.messageId = 0;

            this.clickListener = null;
        }

        public ShowErrorEvent(String error) {
            this.error = null;
            this.errorString = error;
            this.messageId = 0;

            this.clickListener = null;
        }

        public ShowErrorEvent(String error, DialogInterface.OnClickListener clickListener) {
            this.error = null;
            this.errorString = error;
            this.messageId = 0;

            this.clickListener = clickListener;
        }

        public ShowErrorEvent(int message) {
            this.error = null;
            this.errorString = null;
            this.messageId = message;

            this.clickListener = null;
        }

        private String getErrorString(Context context) {
            if (!StringUtils.isNull(errorString)) {
                return errorString;
            } else if (error != null) {
                return error.getMessage();
            } else if (messageId != 0) {
                return context.getResources().getString(messageId);
            } else {
                return context.getResources().getString(R.string.error);
            }
        }

        public DialogInterface.OnClickListener getClickListener() {
            return clickListener;
        }
    }

    public static class DismissErrorDialogEvent { }

    public static class ShowDialogEvent {
        private final int title;
        private final int message;

        private final int positiveLabel;
        private final int negativeLabel;

        private final DialogInterface.OnClickListener positiveClickListener;
        private final DialogInterface.OnClickListener negativeClickListener;

        public ShowDialogEvent(int title, int message,
                               DialogInterface.OnClickListener positiveClickListener) {
            this.title = title;
            this.message = message;

            this.positiveLabel = 0;
            this.negativeLabel = 0;

            this.positiveClickListener = positiveClickListener;
            this.negativeClickListener = null;
        }

        public ShowDialogEvent(int title, int message,
                               DialogInterface.OnClickListener positiveClickListener,
                               DialogInterface.OnClickListener negativeClickListener) {
            this.title = title;
            this.message = message;

            this.positiveLabel = 0;
            this.negativeLabel = 0;

            this.positiveClickListener = positiveClickListener;
            this.negativeClickListener = negativeClickListener;
        }

        private ShowDialogEvent(int title, int message) {
            this.title = title;
            this.message = message;

            this.positiveLabel = 0;
            this.negativeLabel = 0;

            this.positiveClickListener = null;
            this.negativeClickListener = null;
        }

        public ShowDialogEvent(int title, int message,
                               int positiveLabel, DialogInterface.OnClickListener positiveClickListener,
                               int negativeLabel, DialogInterface.OnClickListener negativeClickListener) {
            this.title = title;
            this.message = message;

            this.positiveLabel = positiveLabel;
            this.negativeLabel = negativeLabel;

            this.positiveClickListener = positiveClickListener;
            this.negativeClickListener = negativeClickListener;
        }

        public int getTitle() {
            return title;
        }

        public int getMessage() {
            return message;
        }

        public DialogInterface.OnClickListener getPositiveClickListener() {
            return positiveClickListener;
        }

        public DialogInterface.OnClickListener getNegativeClickListener() {
            return negativeClickListener;
        }

        public int getPositiveLabel() {
            return positiveLabel;
        }

        public int getNegativeLabel() {
            return negativeLabel;
        }
    }

    public static class LoadingEvent {
        private final boolean show;

        public LoadingEvent(boolean showProgress) {
            this.show = showProgress;
        }

        public boolean isShow() {
            return show;
        }
    }

    public static class FullScreenLoadingEvent extends LoadingEvent {
        public FullScreenLoadingEvent(boolean show) {
            super(show);
        }
    }
}
