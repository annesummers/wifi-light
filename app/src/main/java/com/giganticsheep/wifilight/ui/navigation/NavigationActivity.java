package com.giganticsheep.wifilight.ui.navigation;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.nofragmentbase.ui.base.FlowActivity;
import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;
import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.ui.base.ActivityModule;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.giganticsheep.wifilight.ui.locations.LightNetworkViewState;
import com.giganticsheep.wifilight.ui.navigation.room.RoomScreen;
import com.giganticsheep.wifilight.ui.navigation.location.LocationScreen;
import com.giganticsheep.wifilight.ui.preferences.WifiPreferenceActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.mikepenz.aboutlibraries.LibsBuilder;

import javax.inject.Inject;

import butterknife.InjectView;
import flow.Flow;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class NavigationActivity extends FlowActivity<NavigationScreenGroup>
                                implements HasComponent<NavigationActivityComponent> {

    private NavigationActivityComponent component;

    @InjectView(R.id.actionToolbar) Toolbar toolbar;

    private TextView title;

    private ActionBarDrawerToggle drawerToggle;
    private LightNetworkViewState drawerViewState;

    @InjectView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @InjectView(R.id.container_drawer) FrameLayout drawerContainerLayout;

    @InjectView(R.id.loadingLayout) FrameLayout loadingLayout;
    @InjectView(R.id.errorLayout) FrameLayout errorLayout;
    @InjectView(R.id.maskLayout) FrameLayout maskLayout;

    @InjectView(R.id.lightNetworkLayout) RelativeLayout lightNetworkLayout;

    @InjectView(R.id.error_textview) TextView errorTextView;

    private String currentLocationId;
    private String currentGroupId;

    @Inject FragmentFactory fragmentFactory;
    @Inject EventBus eventBus;
    @Inject ErrorStrings errorStrings;

    private boolean drawStateEnabled;

    @Override
    protected int layoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void createComponentAndInject() {
        WifiLightApplication application = ((WifiLightApplication)getApplication());

        component = DaggerNavigationActivityComponent.builder()
                .wifiLightAppComponent(application.getComponent())
                .activityModule(new ActivityModule(this))
                .build();

        component.inject(this);
    }

    @Override
    protected NavigationScreenGroup createScreenGroup() {
        return new NavigationScreenGroup(component);
    }

    @Override
    protected void initialiseViews() {
        drawerViewState = new LightNetworkViewState();
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        title = (TextView) toolbar.findViewById(R.id.titleTextView);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        try {
            transaction.add(R.id.container_drawer, FragmentBase.create(getString(R.string.fragment_name_drawer), fragmentFactory));
        } catch(Exception e) { }

        transaction.commit();

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
        drawerToggle.setToolbarNavigationClickListener(v -> {
            Flow.get(this).goBack();

            actionBar.setDisplayHomeAsUpEnabled(false);
            setDrawerState(true);
        });

        drawerLayout.post(() -> drawerToggle.syncState());
    }

    @Override
    protected Screen getInitialScreen() {
        return new LocationScreen(getScreenGroup());
    }

    @Override
    protected void onCreated() { }

    @Override
    protected int additionalScreenCount() {
        return 3;
    }

    @Override
    protected ViewGroup getMainContainer() {
        return lightNetworkLayout;
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

        if(!drawerToggle.onOptionsItemSelected(item)) {
            final int id = item.getItemId();

            switch (id) {
                case R.id.action_settings:
                    Intent intent = new Intent(this, WifiPreferenceActivity.class);
                    startActivity(intent);
                    return true;

                case R.id.action_refresh:
                    getScreenGroup().fetchLightNetwork();
                    return true;

                case R.id.action_about:
                    new LibsBuilder()
                            .withFields(R.string.class.getFields())
                            .withActivityTheme(R.style.Theme_WifiLight)
                            .start(this);
                    return true;

                case android.R.id.home:
                    Flow.get(this).goBack();

                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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

    //Dagger

    @Override
    public NavigationActivityComponent getComponent() {
        return component;
    }

    // Event handling

    public void onEventMainThread(LoadingEvent loadingEvent) {
        loadingLayout.setVisibility(loadingEvent.isLoading() ? View.VISIBLE : View.GONE);
    }

    public void onEventMainThread(ErrorEvent errorEvent) { }

    public void onEventMainThread(final SetTitleEvent event) {
        title.setText(event.getTitle());
    }

    public void onEventMainThread(final ShowGroupFragmentEvent event) {
        maskLayout.setVisibility(View.VISIBLE);

        Animation zoomInAnimation = zoomInAnimation(event, 500);
        zoomInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                RoomScreen roomScreen = new RoomScreen(getScreenGroup());
                Flow.get(NavigationActivity.this).set(roomScreen);

                roomScreen.fetchGroup(event.groupId);

                setDrawerState(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                supportInvalidateOptionsMenu();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        maskLayout.startAnimation(zoomInAnimation);
    }

    public void onEventMainThread(final ShowLightControlActivityEvent event) {
        maskLayout.setVisibility(View.VISIBLE);

        Animation zoomInAnimation = zoomInAnimation(event, 500);
        zoomInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                //presenter.lightChanged(event.lightId);

                Intent intent = new Intent();
                intent.setClass(NavigationActivity.this, LightControlActivity.class);

                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        maskLayout.startAnimation(zoomInAnimation);
    }

    public void onEventMainThread(final ViewShownEvent event) {
        maskLayout.removeAllViews();
        maskLayout.setVisibility(View.GONE);
    }

    private Animation zoomInAnimation(@NonNull final AnimateEvent event,
                                      final int zoomDuration) {
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

        RelativeLayout.LayoutParams targetLayoutParams = (RelativeLayout.LayoutParams) lightNetworkLayout.getLayoutParams();

        int dLeft = left - lightNetworkLayout.getPaddingLeft() - targetLayoutParams.leftMargin;
        int dRight = right - lightNetworkLayout.getPaddingRight() - targetLayoutParams.rightMargin;
        int dTop = top - toolbar.getHeight() - lightNetworkLayout.getPaddingTop() - targetLayoutParams.topMargin;
        int dBottom = bottom - lightNetworkLayout.getPaddingBottom() - targetLayoutParams.bottomMargin;

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

    // Events

    public static class CloseDrawerEvent { }

    public static class ShowGroupFragmentEvent extends AnimateEvent {
        final String groupId;

        public ShowGroupFragmentEvent(final Rect startRect,
                                      final RelativeLayout startLayout,
                                      final String groupId) {
            super(startRect, startLayout);

            this.groupId = groupId;
        }
    }

    public static class ShowLightControlActivityEvent extends AnimateEvent {

        final String lightId;

        public ShowLightControlActivityEvent(final Rect startRect,
                                             final RelativeLayout startLayout,
                                             final String lightId) {
            super(startRect, startLayout);

            this.lightId = lightId;
        }
    }

    private static class AnimateEvent {
        final int locationX;
        final int locationY;
        final int width;
        final int height;
        final RelativeLayout startLayout;

        public AnimateEvent(final Rect startRect,
                            final RelativeLayout startLayout) {
            this.locationX = startRect.left;
            this.locationY = startRect.top;
            this.width = startRect.width();
            this.height = startRect.height();

            this.startLayout = startLayout;
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
