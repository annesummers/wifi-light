package com.giganticsheep.wifilight.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by anne on 23/06/15.
 * (*_*)
 */
public class LightsList extends ArrayList<Light> {

    Light findLight(String id) {
        Iterator<Light> iterator = iterator();
        while (iterator.hasNext()) {
            Light light = iterator.next();
            if(light.id().equals( id)) {
                return light;
            }
        }

        return null;
    }

    LightsList lightSubList(String label) {
        LightsList subList = new LightsList();
        Iterator<Light> iterator = iterator();

        while (iterator.hasNext()) {
            Light light = iterator.next();
            if(light.labels().contains(label)) {
                subList.add(light);
            }
        }

        return subList;
    }

   /* LightsList locationSubList(long groupId) {

    }

    LightsList locationSubList(String label) {

    }*/
}
