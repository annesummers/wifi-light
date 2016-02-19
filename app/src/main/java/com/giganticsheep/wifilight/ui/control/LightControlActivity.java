package com.giganticsheep.wifilight.ui.control;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.giganticsheep.nofragmentbase.ui.base.FlowActivity;
import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.dagger.HasComponent;
import com.giganticsheep.wifilight.ui.base.ActivityModule;
import com.giganticsheep.wifilight.ui.preferences.WifiPreferenceActivity;
import com.mikepenz.aboutlibraries.LibsBuilder;

import butterknife.InjectView;

/**
 * The Activity containing the Fragments to control a {@link com.giganticsheep.wifilight.api.model.Light} and also to show the
 * details of a {@link com.giganticsheep.wifilight.api.model.Light} as fetched from the server. <p>
 *
 * Created by anne on 09/07/15.<p>
 *
 * (*_*)
 */
public class LightControlActivity extends FlowActivity<LightControlScreenGroup>
                                    implements HasComponent<LightControlActivityComponent> {

    public static final float DEFAULT_DURATION = 1.0F;

    private LightControlActivityComponent component;

    @InjectView(R.id.loadingLayout) FrameLayout loadingLayout;
    @InjectView(R.id.errorLayout) FrameLayout errorLayout;
    @InjectView(R.id.disconnectedLayout) FrameLayout disconnectedLayout;

    @InjectView(R.id.controlLayout) LinearLayout controlLayout;

    @InjectView(R.id.actionToolbar) Toolbar toolbar;

    @InjectView(R.id.error_textview) TextView errorTextView;

    private TextView title;

    private boolean showDetailsFragment;

    @Override
    protected int layoutId() {
        return R.layout.activity_light_control;
    }

    @Override
    protected void createComponentAndInject() {
        WifiLightApplication application = ((WifiLightApplication)getApplication());

        component = DaggerLightControlActivityComponent.builder()
                .wifiLightAppComponent(application.getComponent())
                .activityModule(new ActivityModule(this))
                .build();

        component.inject(this);
    }

    @Override
    protected LightControlScreenGroup createScreenGroup() {
        return new LightControlScreenGroup(component);
    }

    @Override
    protected void initialiseViews() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);

        title = (TextView) toolbar.findViewById(R.id.titleTextView);

      //  showDetailsFragment = sharedPreferences.getBoolean(getString(R.string.preference_key_show_details), false);

      //  if(showDetailsFragment) {
      //   //   addFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_light_details), 1));
       // }
    }

    @Override
    protected Screen getInitialScreen() {
        return new LightScreen(getScreenGroup());
    }

    @Override
    protected void onCreated() { }

    @Override
    protected int additionalScreenCount() {
        return 3;
    }

    @Override
    protected ViewGroup getMainContainer() {
        return controlLayout;
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public final boolean onOptionsItemSelected(@NonNull final MenuItem item) {
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onEventMainThread(LoadingEvent loadingEvent) {
        loadingLayout.setVisibility(loadingEvent.isLoading() ? View.VISIBLE : View.GONE);
    }

    public void onEventMainThread(ConnectingEvent loadingEvent) {
        disconnectedLayout.setVisibility(loadingEvent.isConnecting() ? View.VISIBLE : View.GONE);
    }

    public void onEventMainThread(DisconnectedEvent loadingEvent) {
        disconnectedLayout.setVisibility(loadingEvent.isDisconnected() ? View.VISIBLE : View.GONE);
    }

    public void onEventMainThread(final ViewShownEvent event) { }

    public void onEventMainThread(final SetTitleEvent event) {
        title.setText(event.getTitle());
    }

    // Dagger

    @Override
    public LightControlActivityComponent getComponent() {
        return component;
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightControlActivity activity);
    }

    public static class ConnectingEvent {
        private final boolean connecting;

        public ConnectingEvent(boolean connecting) {
            this.connecting = connecting;
        }

        public boolean isConnecting() {
            return connecting;
        }
    }

    public static class DisconnectedEvent {
        private final boolean disconnected;

        public DisconnectedEvent(boolean disconnected) {
            this.disconnected = disconnected;
        }

        public boolean isDisconnected() {
            return disconnected;
        }

    }
/*
    @Override
    public void showLoading() {
        getViewState().setShowLoading();

        errorLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        controlLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    //@DebugLog
    @Override
    public void showConnected(@NonNull final Light light) {
        getViewState().setShowConnected(light);

        title.setText(light.getLabel());

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        controlLayout.setVisibility(View.VISIBLE);
    }

    //@DebugLog
    @Override
    public void showConnecting(@NonNull final Light light) {
        getViewState().setShowConnecting(light);

        title.setText(light.getLabel());

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        controlLayout.setVisibility(View.VISIBLE);
        disconnectedLayout.setVisibility(View.VISIBLE);

        // TODO pull the info from the server again

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getPresenter().lightChanged(light.getId());
            }
        }, Constants.LAST_SEEN_TIMEOUT_SECONDS * Constants.MILLISECONDS_IN_SECOND);
    }

    //@DebugLog
    @Override
    public void showDisconnected(@NonNull final Light light) {
        getViewState().setShowDisconnected(light);

        title.setText(light.getLabel());

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        controlLayout.setVisibility(View.VISIBLE);
        disconnectedLayout.setVisibility(View.VISIBLE);
    }

    //@DebugLog
    @Override
    public void showError() {
        showError(new Exception("Unknown error"));
    }

    //@DebugLog
    @Override
    public void showError(@NonNull final Throwable throwable) {
        getViewState().setShowError(throwable);

        errorTextView.setText(throwable.getMessage());

        loadingLayout.setVisibility(View.GONE);
        disconnectedLayout.setVisibility(View.GONE);
        controlLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.VISIBLE);
    }
*/
/*
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
    }*/
}
