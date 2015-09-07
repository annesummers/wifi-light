package com.giganticsheep.wifilight.base;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.ui.colour.ColourFragmentBuilder;
import com.giganticsheep.wifilight.ui.control.network.LightNetworkDrawerFragmentBuilder;
import com.giganticsheep.wifilight.ui.details.DetailsFragmentBuilder;
import com.giganticsheep.wifilight.ui.effects.EffectsFragmentBuilder;
import com.giganticsheep.wifilight.ui.navigation.group.GroupFragmentBuilder;
import com.giganticsheep.wifilight.ui.navigation.location.LocationFragmentBuilder;
import com.giganticsheep.wifilight.ui.status.StatusFragmentBuilder;
import com.giganticsheep.wifilight.ui.white.WhiteFragmentBuilder;

import java.util.Map;

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
        if (name.equals(application.getString(R.string.fragment_name_light_colour))) {
            return new ColourFragmentBuilder(name).build();
        }

        if (name.equals(application.getString(R.string.fragment_name_light_white))) {
            return new WhiteFragmentBuilder(name).build();
        }

        if (name.equals(application.getString(R.string.fragment_name_light_effects))) {
            return new EffectsFragmentBuilder(name).build();
        }

        if (name.equals(application.getString(R.string.fragment_name_light_details))) {
            return new DetailsFragmentBuilder(name).build();
        }

        if (name.equals(application.getString(R.string.fragment_name_light_status))) {
            return new StatusFragmentBuilder(name).build();
        }

        if (name.equals(application.getString(R.string.fragment_name_drawer))) {
            return new LightNetworkDrawerFragmentBuilder(name).build();
        }

        if(name.equals(application.getString(R.string.fragment_name_location))) {
            return new LocationFragmentBuilder(name).build();
        }

        throw new Exception("Fragment does not exist");
    }

    /**
     * @throws Exception if the requested fragment doesn't exist.
     * @param name the name of the fragment to create.
     * @return the Observable to subscribe to.
     */
    @NonNull
    public final FragmentBase createFragment(@NonNull final String name,
                                             final Map<String, String> args) throws Exception {

        if(name.equals(application.getString(R.string.fragment_name_group))) {
            return new GroupFragmentBuilder(name, args.get(WifiLightApplication.KEY_GROUP_ID)).build();
        }

        throw new Exception("Fragment does not exist");
    }
}
