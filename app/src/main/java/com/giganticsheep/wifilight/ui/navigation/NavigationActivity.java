package com.giganticsheep.wifilight.ui.navigation;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.ui.base.ActivityBase;
import com.giganticsheep.wifilight.ui.base.ActivityLayout;
import com.giganticsheep.wifilight.ui.base.ActivityModule;
import com.giganticsheep.wifilight.ui.base.FragmentAttachmentDetails;
import com.giganticsheep.wifilight.ui.control.network.LightNetworkViewState;
import com.giganticsheep.wifilight.ui.preferences.WifiPreferenceActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.mikepenz.aboutlibraries.LibsBuilder;

import butterknife.InjectView;
import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class NavigationActivity extends ActivityBase<NavigationView,
                                                    NavigationPresenter,
                                                    NavigationActivityComponent>
                                implements NavigationView {

    private NavigationActivityComponent component;

    @InjectView(R.id.action_toolbar) Toolbar toolbar;

    private TextView title;

    private ActionBarDrawerToggle drawerToggle;
    private LightNetworkViewState drawerViewState;

    private String currentLocationId;
    private String currentGroupId;

    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @InjectView(R.id.container_drawer) FrameLayout drawerContainerLayout;

    @InjectView(R.id.loading_layout) FrameLayout loadingLayout;
    @InjectView(R.id.error_layout) FrameLayout errorLayout;
    @InjectView(R.id.light_network_layout) FrameLayout lightNetworkLayout;
    @InjectView(R.id.mask_layout) FrameLayout maskLayout;

    @InjectView(R.id.error_textview) TextView errorTextView;

    private boolean drawStateEnabled;

    @DebugLog
    @Override
    protected final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            //
        }
    }

    @DebugLog
    @Override
    protected void attachInitialFragments() {
        drawerViewState = new LightNetworkViewState();

        attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_location), 0));
        attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_drawer), 1));
    }

    @DebugLog
    @Override
    protected void initialiseViews() {
        super.initialiseViews();

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        title = (TextView) toolbar.findViewById(R.id.title_textview);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.action_drawer_open,
                R.string.action_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                drawerToggle.setDrawerIndicatorEnabled(true);
                drawerToggle.syncState();

                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                supportInvalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        drawerLayout.post(() -> drawerToggle.syncState());
    }

    @Override
    public final void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getPresenter().fetchLightNetwork();
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public final boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(!drawStateEnabled || !drawerToggle.onOptionsItemSelected(item)) {
            final int id = item.getItemId();

            switch (id) {
                case R.id.action_settings:
                    Intent intent = new Intent(this, WifiPreferenceActivity.class);
                    startActivity(intent);
                    return true;

                case R.id.action_refresh:
                    getPresenter().fetchLightNetwork();
                    return true;

                case R.id.action_about:
                    new LibsBuilder()
                            .withFields(R.string.class.getFields())
                            .withActivityTheme(R.style.Theme_WifiLight)
                            .start(this);
                    return true;

                case R.id.homeAsUp:
                    attachFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_location), 0));

                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        return true;
    }

    @Override
    public final void onConfigurationChanged(@NonNull final Configuration config) {
        super.onConfigurationChanged(config);

        drawerToggle.onConfigurationChanged(config);
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    protected ActivityLayout createActivityLayout() {
        return new ActivityLayout() {
            @Override
            public int fragmentContainer(int position) {
                switch (position) {
                    case 0:
                        return R.id.container;
                    case 1:
                        return R.id.container_drawer;
                    default:
                        return 0;

                }
            }

            @Override
            public int fragmentContainerCount() {
                return 2;
            }

            @Override
            public int layoutId() {
                return R.layout.activity_navigation;
            }
        };
    }

    public void onEventMainThread(final CloseDrawerEvent event) {
        drawerLayout.post(() -> drawerLayout.closeDrawers());
    }

    public ViewState getDrawerViewState() {
        return drawerViewState;
    }

    private void setDrawerState(boolean isEnabled) {
        if ( isEnabled ) {
            drawStateEnabled = true;
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawerToggle.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
            drawerToggle.setDrawerIndicatorEnabled(true);

            drawerLayout.post(() -> drawerToggle.syncState());

        } else {
            drawStateEnabled = false;
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            drawerToggle.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
            drawerToggle.setDrawerIndicatorEnabled(false);

            drawerLayout.post(() -> drawerToggle.syncState());
        }
    }

    // MVP

    @Override
    public NavigationPresenter createPresenter() {
        return new NavigationPresenter(getComponent());
    }

    @Override
    public RestoreableViewState createViewState() {
        return new NavigationViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        getViewState().apply(this, true);
    }

    @NonNull
    @Override
    public NavigationViewState getViewState() {
        return (NavigationViewState) super.getViewState();
    }

    @Override
    public void showLoading() {
        getViewState().setShowLoading();

        errorLayout.setVisibility(View.GONE);
        lightNetworkLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);

        setDrawerState(false);
    }

    @Override
    public void showGroup(final String groupId) {
        getViewState().showGroup(groupId);

        //this.currentGroupId = groupId;

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        lightNetworkLayout.setVisibility(View.VISIBLE);

        setDrawerState(true);
    }

    @Override
    public void showLocation(final String locationId) {
        getViewState().showLocation(locationId);

       // this.currentLocationId = locationId;

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        lightNetworkLayout.setVisibility(View.VISIBLE);

        setDrawerState(true);
    }

    @Override
    public void showError() {
        showError(new Exception("Unknown error"));
    }

    @Override
    public void showError(Throwable throwable) {
        getViewState().setShowError(throwable);

        errorTextView.setText(throwable.getMessage());

        loadingLayout.setVisibility(View.GONE);
        lightNetworkLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.VISIBLE);

        setDrawerState(false);
    }

    public void onEventMainThread(final ShowGroupFragmentEvent event) {
        maskLayout.setVisibility(View.VISIBLE);

        Animation zoomInAnimation = zoomInAnimation(event, 500);
        zoomInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                FragmentAttachmentDetails attachDetails = new FragmentAttachmentDetails(
                        getString(R.string.fragment_name_group), 0);
                attachDetails.addStringArg(WifiLightApplication.KEY_GROUP_ID, event.groupId);
                attachNewFragment(attachDetails);

                presenter.groupChanged(event.groupId);

                setDrawerState(false);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        maskLayout.startAnimation(zoomInAnimation);
    }

    public void onEventMainThread(final FragmentShownEvent event) {
        maskLayout.setVisibility(View.GONE);
    }

    // Dagger

    @Override
    protected void injectDependencies() {
        super.injectDependencies();

        WifiLightApplication application = ((WifiLightApplication)getApplication());

        component = DaggerNavigationActivityComponent.builder()
                .wifiLightAppComponent(application.getComponent())
                .activityModule(new ActivityModule(this))
                .build();

        component.inject(this);
    }

    @Override
    public NavigationActivityComponent getComponent() {
        return component;
    }

    private Animation zoomInAnimation(@NonNull final ShowGroupFragmentEvent event, final int zoomDuration) {
        int marginRight = lightNetworkLayout.getWidth() -  event.width - event.locationX;
        int marginBottom = lightNetworkLayout.getHeight() - event.height -  event.locationY;

        int left = event.locationX;
        int right = lightNetworkLayout.getWidth() -  event.width - left;
        int top = event.locationY - lightNetworkLayout.getPaddingTop() - 30;
        int bottom = lightNetworkLayout.getHeight() - top - event.height + toolbar.getHeight();

        DrawerLayout.LayoutParams layoutParams = new DrawerLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(left, top, right, bottom);
        maskLayout.setLayoutParams(layoutParams);

        FrameLayout.LayoutParams groupLayoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        event.startLayout.setLayoutParams(groupLayoutParams);

        maskLayout.addView(event.startLayout);

        int dLeft = left - lightNetworkLayout.getPaddingLeft();
        int dRight = right - lightNetworkLayout.getPaddingRight();
        int dTop = top - toolbar.getHeight() - lightNetworkLayout.getPaddingTop();
        int dBottom = bottom - lightNetworkLayout.getPaddingBottom();

        Animation a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) maskLayout.getLayoutParams();
                params.leftMargin = (int) (left - dLeft * interpolatedTime);
                params.topMargin = (int) (top - dTop * interpolatedTime);
                params.rightMargin= (int) (right - dRight * interpolatedTime);
                params.bottomMargin= (int) (bottom - dBottom * interpolatedTime);
                maskLayout.setLayoutParams(params);
            }
        };

        a.setDuration(zoomDuration);

        return a;
    }

    public static class CloseDrawerEvent { }

    public static class ShowGroupFragmentEvent {
        final int locationX;
        final int locationY;
        final int width;
        final int height;
        final RelativeLayout startLayout;

        final String groupId;

        public ShowGroupFragmentEvent(final Rect startRect,
                                      final RelativeLayout startLayout,
                                      final String groupId) {
            this.locationX = startRect.left;
            this.locationY = startRect.top;
            this.width = startRect.width();
            this.height = startRect.height();

            this.startLayout = startLayout;
            this.groupId = groupId;
        }
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightPresenterBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        /**
         * Injects the lightPresenter class into the Component implementing this interface.
         *
         * @param navigationActivity the class to inject.
         */
        void inject(final NavigationActivity navigationActivity);
    }
}
