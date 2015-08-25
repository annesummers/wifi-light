package com.giganticsheep.wifilight.api;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.api.model.LightNetwork;

import rx.Observable;

/**
 * Created by anne on 12/07/15.
 * (*_*)
 */
public interface LightControl {

    enum Power {
        ON ("on"),
        OFF ("off");

        private final String name;

        public static Power parse(String power) {
            if (power.equals(ON.name)) {
                return ON;
            } else if (power.equals(OFF.name)) {
                return OFF;
            }

            return null;
        }

        Power(String name) {
            this.name = name;
        }

        public String getPowerString() {
            return name;
        }
    }

    enum Status {
        OK("ok"),
        OFF("offline"),
        ERROR("error");

        private final String statusString;

        public static Status parse(String status) {
            if (status.equals(OK.statusString)) {
                return OK;
            } else if (status.equals(OFF.statusString)) {
                return OFF;
            }

            return null;
        }

        public String getStatusString() {
            return statusString;
        }

        Status(String statusString) {
            this.statusString = statusString;
        }
    }

    /**
     * Sets the hue of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param hue the hue to set; an int between 0 and 360.
     * @param duration the duration to fade into the new hue.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<LightStatus> setHue(final int hue, float duration);

    /**
     * Sets the saturation of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param saturation the saturation to set; an int between 0 and 100.
     * @param duration the duration to fade into the new saturation.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<LightStatus> setSaturation(final int saturation, float duration);

    /**
     * Sets the brightness of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param brightness the brightness to set; an int between 0 and 100.
     * @param duration the duration to fade into the new brightness.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<LightStatus> setBrightness(final int brightness, float duration);

    /**
     * Sets the kelvin (warmth) of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param kelvin the kelvin to set; an int between 2500 and 9000.
     * @param duration the duration to fade into the new kelvin.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<LightStatus> setKelvin(final int kelvin, float duration);

    /**
     * Toggles the power of the selected {@link com.giganticsheep.wifilight.api.model.Light}s
     *
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<LightStatus> togglePower();

    /**
     * Sets the power of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param power ON or OFF.
     * @param duration how long to set the power change.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<LightStatus> setPower(final Power power, final float duration);

    /**
     * Fetches the {@link com.giganticsheep.wifilight.api.model.Light} with the specified id.
     *
     * @param id a String representing the id of the {@link com.giganticsheep.wifilight.api.model.Light} to fetch.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<Light> fetchLight(final String id);

    /**
     * Fetches the {@link com.giganticsheep.wifilight.api.model.Group} with the specified id.
     *
     * @param groupId a String representing the id of the {@link com.giganticsheep.wifilight.api.model.Group} to fetch.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<Group> fetchGroup(String groupId);

    /**
     * Fetches the {@link com.giganticsheep.wifilight.api.model.Location} with the specified id.
     *
     * @param locationId a String representing the id of the {@link com.giganticsheep.wifilight.api.model.Location} to fetch.
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<Location> fetchLocation(String locationId);

    /**
     * Fetches all the Locations, Groups and Lights from the network.
     *
     * @return the Observable to subscribe to.
     */
    @NonNull
    Observable<LightNetwork> fetchLightNetwork();

    class LightSelectorChangedEvent {
        private final LightSelector selector;

        public LightSelectorChangedEvent(final LightSelector selector) {
            this.selector = selector;
        }

        public final LightSelector selector() {
            return selector;
        }
    }

    class FetchLightNetworkEvent {
        private final LightNetwork lightNetwork;

        public FetchLightNetworkEvent(final LightNetwork lightNetwork) {
            this.lightNetwork = lightNetwork;
        }

        public final LightNetwork lightNetwork() {
            return lightNetwork;
        }
    }

}
