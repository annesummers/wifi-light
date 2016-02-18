package com.giganticsheep.wifilight.ui.navigation;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 08/09/15. <p>
 * (*_*)
 */
public abstract class LightContainerAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    
    protected RelativeLayout placeholderLightContainerLayout;
    protected ViewGroup placeholderViewGroup;
    
    public class LightContainerViewHolder extends RecyclerView.ViewHolder {

        protected ViewGroup viewGroup;
        public RelativeLayout lightContainerLayout;

        public LightContainerViewHolder(@NonNull final View view) {
            super(view);

            this.viewGroup = (ViewGroup) view;
        }

        protected final void detachLayoutAndAttachPaddedPlaceholder() {
            placeholderLightContainerLayout.setPadding(
                    lightContainerLayout.getWidth()/2 - lightContainerLayout.getPaddingLeft(),
                    lightContainerLayout.getHeight()/2 - lightContainerLayout.getPaddingTop(),
                    lightContainerLayout.getWidth()/2 - lightContainerLayout.getPaddingRight(),
                    lightContainerLayout.getHeight()/2 - lightContainerLayout.getPaddingBottom() - 30);

            placeholderViewGroup.removeView(placeholderLightContainerLayout);
            viewGroup.removeView(lightContainerLayout);
            viewGroup.addView(placeholderLightContainerLayout);
        }

        protected final Rect calculateRectOnScreen() {
            // get XY coordingates for the group layout
            int[] location = new int[2];
            lightContainerLayout.getLocationOnScreen(location);

            int width = lightContainerLayout.getWidth();
            int height = lightContainerLayout.getHeight();

            Rect groupRect = new Rect(location[0],
                    location[1],
                    location[0] + width,
                    location[1] + height);

            return groupRect;
        }
    }
    
    public class LightViewHolder extends LightContainerViewHolder {

        public final TextView lightNameTextView;
        protected final ImageView lightImageView;

        public LightViewHolder(@NonNull final View view) {
            super(view);

            this.lightContainerLayout = (RelativeLayout) view.findViewById(R.id.light_layout);
            this.lightNameTextView = (TextView) view.findViewById(R.id.light_name_textview);
            this.lightImageView = (ImageView) view.findViewById(R.id.light_small_imageview);

            lightImageView.setVisibility(View.VISIBLE);
        }

        public void setVisibility(int visibility) {
            viewGroup.setVisibility(visibility);
        }
    }
}
