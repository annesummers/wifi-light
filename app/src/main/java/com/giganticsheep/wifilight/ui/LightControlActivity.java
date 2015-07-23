package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.mvp.presenter.ControlPresenter;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.mvp.view.LightViewState;
import com.giganticsheep.wifilight.ui.base.ActivityBase;
import com.giganticsheep.wifilight.ui.base.ActivityLayout;
import com.giganticsheep.wifilight.ui.base.FragmentAttachmentDetails;
import com.giganticsheep.wifilight.util.Constants;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnItemClick;
import icepick.Icicle;

/**
 * The Activity containing the Fragments to control a {@link com.giganticsheep.wifilight.api.model.Light} and also to show the
 * details of a {@link com.giganticsheep.wifilight.api.model.Light} as fetched from the server. <p>
 *
 * Created by anne on 09/07/15.<p>
 *
 * (*_*)
 */
public class LightControlActivity extends ActivityBase<LightView, ControlPresenter>
                            implements LightView,
                            HasComponent<LightControlActivityComponent> {

    public static final float DEFAULT_DURATION = 1.0F;

    private ViewPager viewPager;

    @InjectView(R.id.loading_layout) FrameLayout loadingLayout;
    @InjectView(R.id.error_layout) FrameLayout errorLayout;
    @InjectView(R.id.light_layout) LinearLayout lightLayout;
    @InjectView(R.id.disconnected_layout) FrameLayout disconnectedLayout;

    @InjectView(R.id.sliding_tabs) TabLayout tabLayout;

    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @InjectView(R.id.left_drawer) ListView drawerListView;

    @InjectView(R.id.action_toolbar) Toolbar toolbar;

    private LightControlActivityComponent component;

    private DrawerAdapter drawerAdapter;

    @Icicle int drawerSelectedPosition = Constants.INVALID;

    // Views

    @Override
    protected final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_light_status), 0, false));
            attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_light_details), 1, false));
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
                return R.layout.activity_main;
            }
        };
    }

    @Override
    protected void initialiseViews() {
        super.initialiseViews();

        setSupportActionBar(toolbar);

        PagerAdapter pagerAdapter = null;

        if(viewPager != null) {
            pagerAdapter = viewPager.getAdapter();
            pagerAdapter.notifyDataSetChanged();
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter != null ? pagerAdapter : new LightFragmentPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        if(drawerAdapter == null) {
            drawerAdapter = new DrawerAdapter(component);
        }

        drawerListView.setAdapter(drawerAdapter);

        if(drawerSelectedPosition != Constants.INVALID) {
            drawerListView.setItemChecked(drawerSelectedPosition, true);
        }
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public final void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getPresenter().fetchLights(false);
    }

    @Override
    public final boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_refresh:
                getPresenter().fetchLights(true);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnItemClick (R.id.left_drawer)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        logger.debug("onItemClick() drawer list");

        getPresenter().fetchLight(drawerAdapter.getItem(position));

        drawerSelectedPosition = position;

        drawerListView.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerListView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this);
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return true;
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

    // MVP

    @NonNull
    @Override
    public ControlPresenter createPresenter() {
        return new ControlPresenter(getComponent());
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

    @Override
    public void showLoading() {
        logger.debug("showLoading()");

        getViewState().setShowLoading();

        errorLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConnected() {
        logger.debug("showConnected()");

        getViewState().setData(getPresenter().getLight());
        getViewState().setShowConnected();

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConnecting() {
        logger.debug("showConnecting()");

        getViewState().setData(getPresenter().getLight());
        getViewState().setShowConnecting();

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        disconnectedLayout.setVisibility(View.VISIBLE);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Light light = getPresenter().getLight();

                if(light != null) {
                    getPresenter().fetchLight(getPresenter().getLight().id());
                }
            }
        }, Constants.LAST_SEEN_TIMEOUT_SECONDS * Constants.MILLISECONDS_IN_SECOND);
    }

    @Override
    public void showDisconnected() {
        logger.debug("showDisconnected()");

        getViewState().setData(getPresenter().getLight());
        getViewState().setShowDisconnected();

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        disconnectedLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        showError(new Exception("Unknown error"));
    }

    @Override
    public void showError(@NonNull Throwable throwable) {
        logger.debug("showError()");

        getViewState().setShowError();

        loadingLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.VISIBLE);

        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setMessage(throwable.getMessage())
                .setPositiveButtonText(android.R.string.ok)
                .show();
    }

    private class LightFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;

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
                         return fragmentFactory.createFragment(getString(R.string.fragment_name_light_colour));
                    case 1:
                        return fragmentFactory.createFragment(getString(R.string.fragment_name_light_white));
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
                    return getString(R.string.fragment_name_light_colour);
                case 1:
                    return getString(R.string.fragment_name_light_white);
                case 2:
                    return getString(R.string.fragment_name_light_effects);
                default:
                    return null;
            }
        }
    }
}
