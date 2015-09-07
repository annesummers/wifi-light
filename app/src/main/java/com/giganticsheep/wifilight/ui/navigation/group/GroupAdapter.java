package com.giganticsheep.wifilight.ui.navigation.group;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.LightViewHolder> {

    private Group group;

    public GroupAdapter(@NonNull final Injector injector) {
        injector.inject(this);
    }

    @Override
    public LightViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup,
                                                      final int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_light_item, null);

        return new LightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LightViewHolder holder,
                                 final int position) {
        if(group != null) {
            holder.lightNameTextView.setText(group.getLight(position).getLabel());
        }
    }

    @Override
    public int getItemCount() {
        if(group == null) {
            return 0;
        }

        return group.lightCount();
    }

    void setGroup(@NonNull final Group group) {
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

    static class LightViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ViewGroup viewGroup;
        private final RelativeLayout lightLayout;
        private TextView lightNameTextView;
        private View lightView;

        public LightViewHolder(@NonNull final View view) {
            super(view);

            this.viewGroup = (ViewGroup) view;

            this.lightLayout = (RelativeLayout) view.findViewById(R.id.light_layout);

            this.lightNameTextView = (TextView) view.findViewById(R.id.light_name_textview);

            lightLayout.setOnClickListener(this);
            lightNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
