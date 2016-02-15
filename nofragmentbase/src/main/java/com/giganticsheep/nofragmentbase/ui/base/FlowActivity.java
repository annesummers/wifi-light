package com.giganticsheep.nofragmentbase.ui.base;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.NoSubscriberEvent;
import flow.Flow;
import flow.FlowDelegate;
import flow.History;

/**
 * Created by anne on 14/11/15.
 */
public abstract class FlowActivity extends AppCompatActivity {

    private FlowDelegate flowDelegate;
   // private Flow flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        setContentView(layoutId());

        ButterKnife.bind(this);

        final LayoutTransition layoutTransition = new LayoutTransition();

        initialiseViews();

        flowDelegate = FlowDelegate.onCreate((FlowDelegate.NonConfigurationInstance) getLastCustomNonConfigurationInstance(),
                getIntent(),
                savedInstanceState,
                new Parceler(),
                History.single(getInitialScreen()),
                new Flow.Dispatcher() {
                    @Override
                    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
                        final Screen screen = traversal.destination.top();
                        final Screen lastScreen = traversal.origin.top();

                        if (screen != lastScreen) {
                            final View view = screen.inflateView(FlowActivity.this, getMainContainer());

                            final Animator inAnimation = AnimatorInflater.loadAnimator(FlowActivity.this, screen.getInAnimation());
                            final Animator outAnimation = AnimatorInflater.loadAnimator(FlowActivity.this, lastScreen.getOutAnimation());

                            /*layoutTransition.disableTransitionType(LayoutTransition.APPEARING);
                            layoutTransition.disableTransitionType(LayoutTransition.DISAPPEARING);
                            layoutTransition.disableTransitionType(LayoutTransition.CHANGE_APPEARING);
                            layoutTransition.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
                            getMainContainer().setLayoutTransition(layoutTransition);*/

                            inAnimation.setTarget(view);

                            if (getMainContainer().getChildCount() == additionalScreenCount() + 1) {
                                outAnimation.setTarget(getMainContainer().getChildAt(additionalScreenCount()));

                                outAnimation.addListener(new Animator.AnimatorListener() {

                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        inAnimation.start();
                                        getMainContainer().addView(view);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                });

                                getMainContainer().removeViewAt(1);

                                //getMainContainer().addView(view);
                               // inAnimation.start();
                                outAnimation.start();
                            }
                        } else {
                            if (getMainContainer().getChildCount() == additionalScreenCount()) {
                                final View view = screen.inflateView(FlowActivity.this, getMainContainer());

                                final Animator inAnimation = AnimatorInflater.loadAnimator(FlowActivity.this, screen.getInAnimation());
                              /*  layoutTransition.disableTransitionType(LayoutTransition.DISAPPEARING);
                                layoutTransition.disableTransitionType(LayoutTransition.APPEARING);
                                layoutTransition.disableTransitionType(LayoutTransition.CHANGE_APPEARING);
                                layoutTransition.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
                                getMainContainer().setLayoutTransition(layoutTransition);*/

                                inAnimation.setTarget(view);

                                getMainContainer().addView(view);
                                inAnimation.start();
                            }
                        }

                        callback.onTraversalCompleted();
                    }
                });

        //flow = Flow.get(this);
    }

    protected abstract void initialiseViews();

    protected abstract int additionalScreenCount();

    protected abstract ViewGroup getMainContainer();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    protected abstract int layoutId();

    protected abstract Screen getInitialScreen();

    @Override
    protected void onResume() {
        super.onResume();

        flowDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        flowDelegate.onPause();
    }

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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onEvent(NoSubscriberEvent unused) { }

    public static class FullScreenLoadingEvent {
        private final boolean show;

        public FullScreenLoadingEvent(boolean show) {
            this.show = show;
        }

        public boolean isShow() {
            return show;
        }
    }
}
