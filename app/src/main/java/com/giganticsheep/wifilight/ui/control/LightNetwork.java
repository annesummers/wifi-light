package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetwork {

    private final List<GroupViewData> groupDataList = new ArrayList<>();
    private final HashMap<String, List<LightViewData>> groupsDataMap = new HashMap<>();

    public void clear() {
        groupDataList.clear();
        groupsDataMap.clear();
    }

    public void add(@NonNull final GroupViewData group) throws IllegalArgumentException {
        if(groupsDataMap.containsKey(group.getId())) {
            throw new IllegalArgumentException("A group with that id already exists");
        }

        groupDataList.add(group);
        groupsDataMap.put(group.getId(), new ArrayList<>());
    }

    public void add(@NonNull final LightViewData light) throws IllegalArgumentException {
        List<LightViewData> subList;

        if(groupsDataMap.containsKey(light.getGroupId())) {
            subList = groupsDataMap.get(light.getGroupId());

            for(LightViewData l : subList) {
                if(l.getId().equals(light.getId())) {
                    throw new IllegalArgumentException("A light with that id already exists");
                }
            }

            subList.add(light);
            groupsDataMap.put(light.getGroupId(), subList);
        } else {
            throw new IllegalArgumentException("Light's group does not exist");
        }
    }

    public int groupCount() {
        return groupDataList.size();
    }

    public LightViewData get(int groupPosition, int childPosition) throws IndexOutOfBoundsException {
        String groupId = get(groupPosition).getId();
        List<LightViewData> lightList = groupsDataMap.get(groupId);
        return lightList.get(childPosition);
    }

    public int lightCount(int groupPosition)  throws IndexOutOfBoundsException {
        String groupId = get(groupPosition).getId();
        List<LightViewData> lightList = groupsDataMap.get(groupId);
        return lightList.size();
    }

    public GroupViewData get(int groupPosition) throws IndexOutOfBoundsException {
        return groupDataList.get(groupPosition);
    }

    public boolean lightExists(String groupId, String lightId) throws IllegalArgumentException {
        if(!groupsDataMap.containsKey(groupId)) {
            throw new IllegalArgumentException("Group does not exist");
        }

        List<LightViewData> lights = groupsDataMap.get(groupId);
        for(LightViewData light : lights) {
            if(light.getId().equals(lightId)) {
                return true;
            }
        }

        return false;
    }

    public void remove(String groupId, String lightId) {
        if(!groupsDataMap.containsKey(groupId)) {
            throw new IllegalArgumentException("Group does not exist");
        }

        List<LightViewData> lights = groupsDataMap.get(groupId);
        for(LightViewData light : lights) {
            if(light.getId().equals(lightId)) {
                lights.remove(light);
                break;
            }
        }
    }
}
