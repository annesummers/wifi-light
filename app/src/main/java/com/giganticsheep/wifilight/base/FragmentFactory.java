package com.giganticsheep.wifilight.base;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.ui.fragment.LightColourFragmentBuilder;
import com.giganticsheep.wifilight.ui.fragment.LightDetailsFragmentBuilder;
import com.giganticsheep.wifilight.ui.fragment.LightEffectsFragmentBuilder;

import rx.Observable;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */
public class FragmentFactory {

    @NonNull
    private final WifiLightApplication application;

    public FragmentFactory(@NonNull final WifiLightApplication application) {
        this.application = application;
    }

    public FragmentFactory() {
        this.application = null;
    }

    /**
     * @param name the name of the fragment to create
     * @return the Observable to subscribe to
     */
    @NonNull
    public final Observable<? extends FragmentBase> createFragmentAsync(@NonNull final String name) {
        try {
            return Observable.just(createFragment(name));
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }

    /**
     * @throws Exception if the requested fragment doesn't exist.
     * @param name the name of the fragment to create.
     * @return the Observable to subscribe to.
     */
    @NonNull
    public final FragmentBase createFragment(@NonNull final String name) throws Exception {
        if(application != null) {
            if (name.equals(application.getString(R.string.fragment_name_light_colour))) {
                return new LightColourFragmentBuilder(name).build();
            }

            if (name.equals(application.getString(R.string.fragment_name_light_effects))) {
                return new LightEffectsFragmentBuilder(name).build();
            }

            if (name.equals(application.getString(R.string.fragment_name_light_details))) {
                return new LightDetailsFragmentBuilder(name).build();
            }
        }

        throw new Exception("Fragment does not exist");
    }
}
