package com.giganticsheep.wifilight.ui.control;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.base.error.SilentErrorSubscriber;
import com.giganticsheep.wifilight.ui.base.ActivityBase;
import com.giganticsheep.wifilight.ui.base.ActivityLayout;
import com.giganticsheep.wifilight.ui.base.FragmentAttachmentDetails;
import com.giganticsheep.wifilight.ui.base.light.LightView;
import com.giganticsheep.wifilight.ui.base.light.LightViewState;
import com.giganticsheep.wifilight.ui.control.network.LightNetworkViewState;
import com.giganticsheep.wifilight.ui.preferences.WifiPreferenceActivity;
import com.giganticsheep.wifilight.util.Constants;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * The Activity containing the Fragments to control a {@link com.giganticsheep.wifilight.api.model.Light} and also to show the
 * details of a {@link com.giganticsheep.wifilight.api.model.Light} as fetched from the server. <p>
 *
 * Created by anne on 09/07/15.<p>
 *
 * (*_*)
 */
public class LightControlActivity extends ActivityBase<LightView, LightControlPresenter>
                            implements LightView,
                            HasComponent<LightControlActivityComponent> {

    public static final float DEFAULT_DURATION = 1.0F;

    private ViewPager viewPager;
    private ActionBarDrawerToggle drawerToggle;

    private LightControlActivityComponent component;

    private LightViewState fragmentViewState;
    private LightNetworkViewState drawerViewState;

    private DialogFragment errorDialog;

    @InjectView(R.id.loading_layout) FrameLayout loadingLayout;
    @InjectView(R.id.error_layout) FrameLayout errorLayout;
    @InjectView(R.id.light_layout) LinearLayout lightLayout;
    @InjectView(R.id.disconnected_layout) FrameLayout disconnectedLayout;

    @InjectView(R.id.sliding_tabs) TabLayout tabLayout;

    @InjectView(R.id.action_toolbar) Toolbar toolbar;

    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @InjectView(R.id.container_drawer) FrameLayout drawerContainerLayout;

    @InjectView(R.id.error_textview) TextView errorTextView;

    private TextView title;

    private boolean showDetailsFragment;

    // Views

    @DebugLog
    @Override
    protected final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentViewState = new LightViewState();
        drawerViewState = new LightNetworkViewState();

        if (savedInstanceState == null) {
            attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_light_status), 0, false));

            showDetailsFragment = sharedPreferences.getBoolean(getString(R.string.preference_key_show_details), false);

            if(showDetailsFragment) {
                attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_light_details), 1, false));
            }

            attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_drawer), 2, false));
        } else {
            Timber.d("here");
        }

        subscribe(eventBus.registerForEvents(this), new SilentErrorSubscriber());
    }

    @DebugLog
    @Override
    protected void initialiseViews() {
        super.initialiseViews();

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setElevation(2);

        title = (TextView) toolbar.findViewById(R.id.title_textview);

        PagerAdapter pagerAdapter = null;

        if(viewPager != null) {
            pagerAdapter = viewPager.getAdapter();
            pagerAdapter.notifyDataSetChanged();
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter != null ? pagerAdapter : new LightFragmentPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

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
    public final boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public final void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getPresenter().fetchLightNetwork();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(showDetailsFragment && !sharedPreferences.getBoolean(getString(R.string.preference_key_show_details), false)) {
            detachFragment(getString(R.string.fragment_name_light_details));
        }
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
                    getPresenter().fetchLightNetwork();
                    return true;

                case R.id.action_about:
                    new LibsBuilder()
                            .withFields(R.string.class.getFields())
                            .withActivityTheme(R.style.Theme_WifiLight)
                            .start(this);
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

    @DebugLog
    @Override
    protected void onDestroy() {
        super.onDestroy();

        subscribe(eventBus.unregisterForEvents(this), new SilentErrorSubscriber());

        if(errorDialog != null) {
            errorDialog.dismiss();
            errorDialog = null;
        }
    }

    @NonNull
    @Override
    protected final ActivityLayout createActivityLayout() {
        return new ActivityLayout() {
            @Override
            public int fragmentContainer(final int position) {
                switch (position) {
                    case 0:
                        return R.id.container;
                    case 1:
                        return R.id.container2;
                    case 2:
                        return R.id.container_drawer;
                    default:
                        return 0;

                }
            }

            @Override
            public int fragmentContainerCount() {
                return 3;
            }

            @Override
            public int layoutId() {
                return R.layout.activity_main;
            }
        };
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return true;
    }

    public void onEvent(final CloseDrawerEvent event) {
        drawerLayout.closeDrawers();
    }

    // MVP

    @NonNull
    @Override
    public LightControlPresenter createPresenter() {
        return new LightControlPresenter(getComponent());
    }

    @NonNull
    @Override
    public RestoreableViewState createViewState() {
        return new LightViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        getViewState().apply(this, true);
    }

    @NonNull
    @Override
    public LightViewState getViewState() {
        return (LightViewState) super.getViewState();
    }

    @NonNull
    public LightViewState getFragmentViewState() {
        if(fragmentViewState == null) {
            return getViewState();
        } else {
            return fragmentViewState;
        }
    }

    @DebugLog
    @Override
    public void showLoading() {
        getViewState().setShowLoading();

        errorLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);

        setDrawerState(false);
    }

    private void setDrawerState(boolean isEnabled) {
        if ( isEnabled ) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawerToggle.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
            drawerToggle.setDrawerIndicatorEnabled(true);

            drawerLayout.post(() -> drawerToggle.syncState());

        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            drawerToggle.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
            drawerToggle.setDrawerIndicatorEnabled(false);

            drawerLayout.post(() -> drawerToggle.syncState());
        }
    }

    @DebugLog
    @Override
    public void showConnected(@NonNull final Light light) {
        getViewState().setShowConnected(light);

        title.setText(light.getLabel());

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);

        setDrawerState(true);
    }

    @DebugLog
    @Override
    public void showConnecting(@NonNull final Light light) {
        getViewState().setShowConnecting(light);

        title.setText(light.getLabel());

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        disconnectedLayout.setVisibility(View.VISIBLE);

        setDrawerState(true);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> getPresenter().fetchLight(light.getId()));
            }
        }, Constants.LAST_SEEN_TIMEOUT_SECONDS * Constants.MILLISECONDS_IN_SECOND);
    }

    @DebugLog
    @Override
    public void showDisconnected(@NonNull final Light light) {
        getViewState().setShowDisconnected(light);

        title.setText(light.getLabel());

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        disconnectedLayout.setVisibility(View.VISIBLE);

        setDrawerState(true);
    }

    @DebugLog
    @Override
    public void showError() {
        showError(new Exception("Unknown error"));
    }

    @DebugLog
    @Override
    public void showError(@NonNull final Throwable throwable) {
        getViewState().setShowError(throwable);

        errorTextView.setText(throwable.getMessage());

        loadingLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.VISIBLE);

        setDrawerState(false);

        if(Constants.SHOW_ERROR_DIALOG) {
            if (errorDialog != null) {
                errorDialog.dismiss();
            }

            errorDialog = SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                    .setMessage(throwable.getMessage())
                    .setPositiveButtonText(android.R.string.ok)
                    .show();
        }
    }

    // Dagger

    @Override
    protected void injectDependencies() {
        super.injectDependencies();

        WifiLightApplication application = ((WifiLightApplication)getApplication());

        component = DaggerLightControlActivityComponent.builder()
                .wifiLightAppComponent(application.getComponent())
                .lightControlActivityModule(new LightControlActivityModule(this))
                .build();

        component.inject(this);
    }

    @Override
    public LightControlActivityComponent getComponent() {
        return component;
    }

    public ViewState getDrawerViewState() {
        return drawerViewState;
    }

    public static class CloseDrawerEvent { }

    private class LightFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;

        public LightFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        //this is called when notifyDataSetChanged() is called
        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed i.e. recreate them all
            return PagerAdapter.POSITION_NONE;
        }

        @Nullable
        @Override
        public Fragment getItem(int position) {
            try {
                switch(position) {
                    case 0:
                         return fragmentFactory.createFragment(getString(R.string.fragment_name_light_white));
                    case 1:
                        return fragmentFactory.createFragment(getString(R.string.fragment_name_light_colour));
                    case 2:
                        return fragmentFactory.createFragment(getString(R.string.fragment_name_light_effects));
                    default:
                        return null;
                }
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return getString(R.string.fragment_name_light_white);
                case 1:
                    return getString(R.string.fragment_name_light_colour);
                case 2:
                    return getString(R.string.fragment_name_light_effects);
                default:
                    return null;
            }
        }
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightControlActivity lightControlActivity);
    }
}
