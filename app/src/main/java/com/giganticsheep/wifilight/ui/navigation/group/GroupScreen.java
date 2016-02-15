package com.giganticsheep.wifilight.ui.navigation.group;

import android.os.Parcel;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.api.model.Group;

/**
 * Created by anne on 24/11/15.
 */
public class GroupScreen extends Screen<GroupPresenter> {

    protected GroupScreen(ScreenGroup screenGroup) {
        super(screenGroup);
    }

    protected GroupScreen(Parcel in) {
        super(in);
    }

    @Override
    public GroupPresenter getPresenter() {
        return null;
    }

    @Override
    protected void injectDependencies() {

    }

    @Override
    protected int layoutId() {
        return 0;
    }

    public interface GroupViewInterface extends ViewInterfaceBase {
        void showGroup(Group group);
    }
}
