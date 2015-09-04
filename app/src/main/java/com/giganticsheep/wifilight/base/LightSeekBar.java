package com.giganticsheep.wifilight.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.giganticsheep.wifilight.R;

import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 31/08/15. <p>
 * (*_*)
 */
public abstract class LightSeekBar extends View {
    protected Paint trackPaint;
    private Paint thumbPaint;

    protected RectF drawRect;

    protected static int strokeWidth = 40;

    protected float maxValue;

    private Bitmap thumb;
    private Bitmap thumbPressed;
    private Bitmap thumbDisabled;
    private Bitmap thumbFocused;
    private Bitmap thumbNormal;

    protected float thumbX;
    protected float thumbY;
    protected int thumbMaxWidth;
    protected int thumbMaxHeight;
    protected float touchTolerance = 100F;

    private int progress;

    private OnLightSeekBarChangeListener listener;
    private boolean trackPathInitialised = false;

    public LightSeekBar(Context context) {
        super(context);

        init();
    }

    private void init() {
        initTrack();

        thumbPaint = new Paint();

        thumbNormal = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.scrubber_control_normal);
        thumbPressed = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.scrubber_control_pressed);
        thumbFocused = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.scrubber_control_focused);
        thumbDisabled = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.scrubber_control_disabled);

        thumb = thumbNormal;

        thumbMaxWidth = calculateThumbMaxWidth();
        thumbMaxHeight = calculateThumbMaxHeight();
    }

    private int calculateThumbMaxHeight() {
        return thumb.getHeight();
    }

    private int calculateThumbMaxWidth() {
        return thumb.getWidth();
    }

    public LightSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public LightSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.translate(this.getWidth() / 2, this.getHeight() / 2);

        if(drawRect != null) {
            drawTrack(canvas);
            canvas.drawBitmap(thumb, thumbX(), thumbY(), thumbPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(listener != null) {
                    listener.onStartTrackingTouch(this);
                }
            case MotionEvent.ACTION_MOVE:
                if(touchIsOnTrack(x, y)) {
                    int progress = progressFromThumbPosition(setThumbPosition(x, y));
                    setProgress(progress, true);
                    calculateThumbXYPosition();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(listener != null) {
                    listener.onStopTrackingTouch(this);
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();

        if(width > 0 && height > 0) {
            Timber.d("%s :: Width : %d, Height : %d", toString(), width, height);

           // if(!trackPathInitialised) {

         //   }

           //

                initTrackPath(width, height);
           //     trackPathInitialised = true;

                calculateThumbXYPosition();

            setMeasuredDimensionIfNeeded(width, height);
           // }
        }
    }

    protected abstract void setMeasuredDimensionIfNeeded(int width, int height);

    // protected abstract float viewHeight(float height);

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        setProgress(progress, false);
    }

    private void setProgress(int progress, boolean fromUser) {
        if (this.progress != progress) {
            this.progress = progress;
            if (!fromUser) {
                setThumbPosition(progress);

                invalidate();
            }

            if(listener != null) {
                listener.onProgressChanged(this, progress, fromUser);
            }
        }
    }

    public void setOnLightSeekBarChangeListener(OnLightSeekBarChangeListener listener) {
        this.listener = listener;
    }

    private float thumbX() {
        int thumbWidth = thumb.getWidth();
        float x = thumbX - (thumbWidth / 2);
        return x;
    }

    private float thumbY() {
        int thumbHeight = thumb.getHeight();
        float y = thumbY - (thumbHeight / 2);
        return y;
    }

    protected abstract void initTrack();

    protected abstract void initTrackPath(int width, int height);

    protected abstract void drawTrack(Canvas canvas);

    protected abstract void setThumbPosition(int progress);

    protected abstract void calculateThumbXYPosition();

    protected abstract boolean touchIsOnTrack(float x, float y);

    protected abstract float setThumbPosition(float x, float y);

    protected abstract int progressFromThumbPosition(float thumbPosition);

    public class RGB {
        public double red;
        public double green;
        public double blue;
    }

    /**
     * A callback that notifies clients when the progress level has been
     * changed. This includes changes that were initiated by the user through a
     * touch gesture or arrow key/trackball as well as changes that were initiated
     * programmatically.
     */
    public interface OnLightSeekBarChangeListener {

        /**
         * Notification that the progress level has changed. Clients can use the fromUser parameter
         * to distinguish user-initiated changes from those that occurred programmatically.
         *
         * @param seekBar The SeekBar whose progress has changed
         * @param progress The current progress level. This will be in the range 0..max where max
         *        was set by {@link ProgressBar#setMax(int)}. (The default value for max is 100.)
         * @param fromUser True if the progress change was initiated by the user.
         */
        void onProgressChanged(LightSeekBar seekBar, int progress, boolean fromUser);

        /**
         * Notification that the user has started a touch gesture. Clients may want to use this
         * to disable advancing the seekbar.
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStartTrackingTouch(LightSeekBar seekBar);

        /**
         * Notification that the user has finished a touch gesture. Clients may want to use this
         * to re-enable advancing the seekbar.
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStopTrackingTouch(LightSeekBar seekBar);
    }
}
