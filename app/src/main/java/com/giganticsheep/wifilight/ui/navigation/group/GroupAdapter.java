package com.giganticsheep.wifilight.ui.navigation.group;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private Location location = null;
    private boolean locationChanged = true;
    private Group group;

    public GroupAdapter(@NonNull final Injector injector) {
        injector.inject(this);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup,
                                                      final int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_list_item, null);

        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GroupViewHolder holder,
                                 final int position) {
        if(location != null) {
            holder.groupNameTextView.setText(location.getGroup(position).getName());

            if (locationChanged) {
                for (int i = 0; i < location.groupCount(); i++) {
                    holder.lightsTextView.add((TextView) holder.lightView.findViewById(R.id.light_name_textview));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if(location == null) {
            return 0;
        }

        return location.groupCount();
    }

    synchronized void setLocation(@NonNull final Location location) {
        locationChanged = true;
        this.location = location;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling the LocationAdapter to inject itself
     * into the Component.
     */
    public interface Injector {
        /**
         * Injects the LocationAdapter class into the Component implementing this interface.
         *
         * @param adapter the class to inject.
         */
        void inject(final GroupAdapter adapter);
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        //private LinearLayout lightsLayout;
        private TextView groupNameTextView;
        private View lightView;

        private List<TextView> lightsTextView = new ArrayList();

        public GroupViewHolder(@NonNull final View view) {
            super(view);

            //lightsLayout = (LinearLayout) view.findViewById(R.id.light_group_layout);
            groupNameTextView = (TextView) view.findViewById(R.id.group_name_textview);
            lightView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_light, null);
        }
    }
}
