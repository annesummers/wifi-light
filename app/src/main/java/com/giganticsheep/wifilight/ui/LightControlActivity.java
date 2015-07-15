package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.mvp.presenter.LightControlPresenter;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.mvp.view.LightViewState;
import com.giganticsheep.wifilight.ui.base.ActivityBase;
import com.giganticsheep.wifilight.ui.base.ActivityLayout;
import com.giganticsheep.wifilight.ui.base.FragmentAttachmentDetails;
import com.giganticsheep.wifilight.util.Constants;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

import butterknife.InjectView;

/**
 * The Activity containing the Fragments to control a light and also to show the
 * details of a Light as fetched from the server. <p>
 *
 * Created by anne on 09/07/15.<p>
 *
 * (*_*)
 */
public class LightControlActivity extends ActivityBase<LightView, LightControlPresenter>
                            implements LightView,
                            HasComponent<LightControlActivityComponent> {

    // TODO javadoc links

    public static final float DEFAULT_DURATION = 1.0F;

    private ViewPager viewPager;

    @InjectView(R.id.loading_layout) FrameLayout loadingLayout;
    @InjectView(R.id.error_layout) FrameLayout errorLayout;
    @InjectView(R.id.light_layout) LinearLayout lightLayout;
    @InjectView(R.id.disconnected_layout) FrameLayout disconnectedLayout;

    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @InjectView(R.id.left_drawer) ListView drawerListView;

    private LightControlActivityComponent component;
    private DrawerAdapter drawerAdapter;

    // Views

    @Override
    protected final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // we are attaching the details fragment at position 0 which is under the view pager
            attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_light_details), 0, true));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(toolbar);

        drawerAdapter = new DrawerAdapter(component);

        drawerListView.setAdapter(drawerAdapter);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
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
                    default:
                        return 0;

                }
            }

            @Override
            public int fragmentContainerCount() {
                return 1;
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

        PagerAdapter pagerAdapter = null;

        if(viewPager != null) {
            pagerAdapter = viewPager.getAdapter();
            pagerAdapter.notifyDataSetChanged();
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter != null ? pagerAdapter : new LightFragmentPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
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
        getViewState().apply(this, false);
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

        getViewState().setShowConnected();

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConnecting() {
        logger.debug("showConnecting()");

        getViewState().setShowConnecting();

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        disconnectedLayout.setVisibility(View.VISIBLE);

        // TODO onFinish is never called

        new CountDownTimer(Constants.LAST_SEEN_TIMEOUT_SECONDS * Constants.MILLISECONDS_IN_SECOND, 0) {

            public void onTick(long millisUntilFinished) { }

            public void onFinish() {
                getPresenter().fetchLight(getCurrentLightId());
            }
        }.start();
    }

    @Override
    public void showDisconnected() {
        logger.debug("showDisconnected()");

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

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_alert_error)
                .setMessage(throwable.getMessage())
                .show();
    }

    @Override
    public void setLight(@NonNull Light light) {
        logger.debug("setLight() " + light.id());
    }

    /**
     * @return the id of the current displayed Light.
     */
    public String getCurrentLightId() {
        return getPresenter().getCurrentLightId();
    }

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
                        return fragmentFactory.createFragment(getString(R.string.fragment_name_light_colour));
                    case 1:
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
                    return getString(R.string.fragment_name_light_effects);
                default:
                    return null;
            }
        }
    }

    private class DrawerItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            logger.debug("onItemClick() drawer list");

            getPresenter().fetchLight(drawerAdapter.getItem(position));

            drawerListView.setItemChecked(position, true);
            drawerLayout.closeDrawer(drawerListView);
        }
    }
}
