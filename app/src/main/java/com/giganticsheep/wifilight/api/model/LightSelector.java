package com.giganticsheep.wifilight.api.model;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/08/15. <p>
 * (*_*)
 */
public class LightSelector {

    public LightSelector(@NonNull final SelectorType type,
                         @Nullable final String id) {
        this.type = type;
        this.id = id;
    }

    public enum SelectorType {
        LOCATION,
        GROUP,
        LIGHT,
        ALL
    }

    @NonNull
    private final SelectorType type;

    @Nullable
    private final String id;


    public final SelectorType getType() {
        return type;
    }

    @Override
    public String toString() {
        String string = "";
        switch(type) {
            case LOCATION:
                string += "location_id";
                break;
            case GROUP:
                string += "group_id";
                break;
            case LIGHT:
                string += "id";
                break;
            case ALL:
                string += "all";
                break;
        }

        if(type != SelectorType.ALL) {
            string += ":" + id;
        }

        return string;
    }
}
