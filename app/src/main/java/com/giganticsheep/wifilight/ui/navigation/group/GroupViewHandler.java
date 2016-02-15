package com.giganticsheep.wifilight.ui.navigation.group;

import android.os.Parcel;

import com.giganticsheep.nofragmentbase.ui.base.ViewHandler;
import com.giganticsheep.wifilight.api.model.Group;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class GroupViewHandler extends ViewHandler<GroupScreen.GroupViewInterface>
                            implements GroupScreen.GroupViewInterface {

    private Group group;

    public GroupViewHandler(Parcel in) {
        in.readParcelable(Group.class.getClassLoader());
    }

    @Override
    public void showGroup(Group group) {
        this.group = group;
    }

    @Override
    protected void showData() {
        for(GroupScreen.GroupViewInterface view : views) {
            view.showGroup(group);
        }
    }

    @Override
    protected GroupScreen.GroupViewInterface getHandlerView() {
        return this;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(group, flags);
    }

    public static final Creator<GroupViewHandler> CREATOR = new Creator<GroupViewHandler>() {
        public GroupViewHandler createFromParcel(Parcel source) {
            GroupViewHandler target = new GroupViewHandler(source);

            if(target.group != null) {
                target.setHasData();
            }

            return target;
        }

        public GroupViewHandler[] newArray(int size) {
            return new GroupViewHandler[size];
        }
    };
}
