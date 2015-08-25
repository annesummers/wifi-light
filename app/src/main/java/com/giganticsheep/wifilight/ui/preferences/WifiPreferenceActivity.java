package com.giganticsheep.wifilight.ui.preferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WifiPreferenceActivity extends PreferenceActivity {

    private AppCompatDelegate delegate;

    private WifiPreferenceActivityComponent component;

    @InjectView(R.id.action_toolbar) Toolbar toolbar;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

        WifiLightApplication application = ((WifiLightApplication)getApplication());

        component = DaggerWifiPreferenceActivityComponent.builder()
                .wifiLightAppComponent(application.getComponent())
                .wifiPreferenceActivityModule(new WifiPreferenceActivityModule())
                .build();

        setContentView(R.layout.activity_preference);

        ButterKnife.inject(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        title = (TextView) toolbar.findViewById(R.id.title_textview);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    private AppCompatDelegate getDelegate() {
        if (delegate == null) {
            delegate = AppCompatDelegate.create(this, null);
        }
        return delegate;
    }

    /**
     * Populate the activity with the top-level headers.
     *
     * @param target
     */
  //  @Override
  //  public void onBuildHeaders(List<Header> target) {
   //     loadHeadersFromResource(R.xml.preference_headers, target);
  //  }

    public WifiPreferenceActivityComponent getComponent() {
        return component;
    }

    /**
     * This fragment shows the preferences for the first header.
     */
    public static class ServerPreferencesFragment extends PreferenceFragment {

        @Inject SharedPreferences preferences;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences_server);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            getPreferenceActivity().getComponent().inject(this);

         //   String preference_api_key = getString(R.string.preference_key_api_key);

            //EditTextPreference editTextPreference = (EditTextPreference) findPreference(preference_api_key);
          //  if(editTextPreference.getEditText() == null) {
          //      String key = preferences.getString(preference_api_key, getString(R.string.default_api_key));
          //      editTextPreference.setText(key);
          //  }
        }

        private WifiPreferenceActivity getPreferenceActivity() {
            return (WifiPreferenceActivity) getActivity();
        }

        /**
         * The Injector interface is implemented by a Component that provides the injected
         * class members, enabling a ServerPreferencesFragment class to inject itself
         * into the Component.
         */
        public interface Injector {
            void inject(ServerPreferencesFragment fragment);
        }
    }
}
