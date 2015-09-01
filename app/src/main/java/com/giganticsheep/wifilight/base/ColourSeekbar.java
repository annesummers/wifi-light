package com.giganticsheep.wifilight.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
public class ColourSeekBar extends View {
    private Paint trackPaint;
    private Paint thumbPaint;
    private Path circlePath;
    private Path circleProgressPath;

    private RectF drawRect;

    private static int strokeWidth = 40;

    //private float minValue = 2500;
    private float maxValue = 6500;
    private Bitmap thumb;
    private Bitmap thumbPressed;
    private float thumbX;
    private float thumbY;

    private int progress;

    private float arcAngle = 300F;
    private float startAngle = 120F;
    private float centreX;
    private float centreY;
    private float outerRadius;
    private float innerRadius;
    private float touchTolerance = 100F;
    private float angle;
    private OnColourSeekBarChangeListener listener;
    private float thumbPositionDegrees;
    private float progressDegrees;
    private float[] pointerPositionXY;
    private float endAngle;
    private int thumbMaxWidth;
    private int thumbMaxHeight;

    public ColourSeekBar(Context context) {
        super(context);

        init();
    }

    private void init() {
        trackPaint = new Paint();
        trackPaint.setARGB(255, 0, 0, 0);
        trackPaint.setAntiAlias(true);
        trackPaint.setDither(true);
        trackPaint.setStrokeWidth(strokeWidth);
        trackPaint.setStyle(Paint.Style.STROKE);
       // trackPaint.setStrokeJoin(Paint.Join.ROUND);
        trackPaint.setStrokeCap(Paint.Cap.ROUND);

        circlePath = new Path();
        circleProgressPath = new Path();

        thumbPaint = new Paint();

        thumb = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.scrubber_control_normal);
        thumbPressed = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.scrubber_control_pressed);

        thumbMaxWidth = Math.max(thumb.getWidth(), thumbPressed.getWidth());
        thumbMaxHeight = Math.max(thumb.getHeight(), thumbPressed.getHeight());

        progress = 3000;

        calculatePointerAngle();
        calculateProgressDegrees();
    }

    public ColourSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ColourSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.translate(getWidth() / 2, getHeight());
        if(drawRect != null) {
            canvas.drawPath(circlePath, trackPaint);
            canvas.drawBitmap(thumb, getXFromAngle(), getYFromAngle(), thumbPaint);
        }
    }

    public float getXFromAngle() {
        int thumbWidth = thumb.getWidth();
        float x = thumbX - (thumbWidth / 2);
        return x;
    }

    public float getYFromAngle() {
        int thumbHeight = thumb.getHeight();
        float y = thumbY - (thumbHeight / 2);
        return y;
    }

    /*public void setAngle(float angle) {
        this.angle = angle;
        float progress = (this.angle/ arcAngle)*maxValue;

        setProgress(Math.round(progress), true);
    }*/

    public void setProgress(int progress, boolean fromUser) {
        if (this.progress != progress) {
            this.progress = progress;
            if (!fromUser) {
                //float newAngle = (this.progress) / (this.maxValue * arcAngle) ;
                //this.setAngle(newAngle);
                calculatePointerAngle();
                calculateProgressDegrees();
            }

            listener.onProgressChanged(this, progress, fromUser);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float distance = (float) Math.sqrt(Math.pow((x - centreX), 2) + Math.pow((y - centreY), 2));
                if (distance < outerRadius + touchTolerance && distance > innerRadius - touchTolerance) {
                   // thumbX = (float) (centreX + outerRadius * Math.cos(Math.atan2(x - centreX, centreY - y) - (Math.PI /2)));
                    //thumbY = (float) (centreY + outerRadius * Math.sin(Math.atan2(x - centreX, centreY - y) - (Math.PI /2)));
                    calculatePointerXYPosition();
                    float degrees = (float) ((float) ((Math.toDegrees(Math.atan2(x - centreX, centreY - y)) + 360.0)) % 360.0);
                    // and to make it count 0-360
                    if (degrees < 0) {
                        degrees += 2 * Math.PI;
                    }

                    setProgressBasedOnAngle(degrees);
                    invalidate();

                }
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return true;
    }

    protected void setProgressBasedOnAngle(float angle) {
        thumbPositionDegrees = angle;
        calculateProgressDegrees();
        progressDegrees = Math.round((float)maxValue * progressDegrees / arcAngle);
    }

    protected void calculateTotalDegrees() {
        if(arcAngle == 0) {
            arcAngle = (360f - (startAngle - endAngle)) % 360f; // Length of the entire circle/arc
            if (arcAngle <= 0f) {
                arcAngle = 360f;
            }
        }
    }

    protected void calculateProgressDegrees() {
        progressDegrees = thumbPositionDegrees - startAngle; // Verified
        progressDegrees = (progressDegrees < 0 ? 360f + progressDegrees : progressDegrees); // Verified
    }

    /**
     * Calculate the pointer position (and the end of the progress arc) in degrees.
     * Sets mPointerPosition to that value.
     */
    protected void calculatePointerAngle() {
        thumbPositionDegrees = (((float)progress / maxValue) * arcAngle) + startAngle;
        thumbPositionDegrees = thumbPositionDegrees % 360f;
    }

    protected void calculatePointerXYPosition() {
        pointerPositionXY = new float[2];
        PathMeasure pm = new PathMeasure(circleProgressPath, false);
        boolean returnValue = pm.getPosTan(pm.getLength(), pointerPositionXY, null);
        if(!returnValue) {
            pm = new PathMeasure(circlePath, false);
            pm.getPosTan(0, pointerPositionXY, null);
        }

        thumbX = pointerPositionXY[0] + centreX;
        thumbY = pointerPositionXY[1] + centreY;
    }

    public int getProgress() {
        int progress = Math.round(maxValue * progressDegrees / arcAngle);
        return progress;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.min(width, height);

        Timber.d("Width : %d, Height : %d", width, height);

        if(size > 0) {
            int extraThumbWidth = (thumbMaxWidth - strokeWidth)/2;
            if(extraThumbWidth < 0) {
                extraThumbWidth = 0;
            }

            int extraThumbHeight = (thumbMaxHeight - strokeWidth)/2;
            if(extraThumbHeight < 0) {
                extraThumbHeight = 0;
            }

            if(drawRect == null ||
                    (drawRect.right != size + extraThumbWidth + getPaddingLeft() ||
                    drawRect.bottom != size + extraThumbHeight + getPaddingTop())) {
                Timber.d("Set the rect");
                drawRect = new RectF(getPaddingLeft(), getPaddingTop(),
                        size + getPaddingLeft(), size + getPaddingTop());
                circlePath.addArc(drawRect, startAngle, arcAngle);
                circleProgressPath.addArc(drawRect, startAngle, progressDegrees);

                calculatePointerXYPosition();

                centreX = (drawRect.right - drawRect.left) / 2; // Center X for circle
                centreY = (drawRect.bottom - drawRect.top) / 2; // Center Y for circle
                outerRadius = size / 2; // Radius of the outer circle

                innerRadius = outerRadius - strokeWidth; // Radius of the inner circle
            }
        }

        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Try for a width based on our minimum
        //  int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        //  int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        // int minh = MeasureSpec.getSize(w) + getPaddingBottom() + getPaddingTop();
        // int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        // Timber.d("Width : %d, Height : %d", w, h);

      /*  int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        String widthModeString = "";
        String heightModeString = "";

       // int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
       // int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
       // if (mMaintainEqualCircle) {
            //int min = Math.min(width, height);
       // int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        //int width = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
       // int minh = MeasureSpec.getSize(width) + getPaddingBottom() + getPaddingTop();
       // int height = resolveSizeAndState(minh, heightMeasureSpec, 0);

        int width;
        int height;


        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            default:
                widthModeString = "Unspecified";
                width = 1000;
                break;
            case MeasureSpec.AT_MOST:
                widthModeString = "At Most";
                width = widthSize;
                break;
            case MeasureSpec.EXACTLY:
                widthModeString = "Exactly";
                width = widthSize;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
            default:
                heightModeString = "Unspecified";
                height = 1000;
                break;
            case MeasureSpec.AT_MOST:
                heightModeString = "At Most";
                height = heightSize;
                break;
            case MeasureSpec.EXACTLY:
                heightModeString = "Exactly";
                height = heightSize;
                break;
        }

        Timber.d("WidthMode : %s, HeightMode : %s, WidthSize : %d, HeightSize : %d",
                widthModeString, heightModeString, widthSize, heightSize);

        //int min = Math.min(width, height);

        Timber.d("Width : %d, Height : %d", width, height);

        setMeasuredDimension(width, height);
    }*/
    }

    class RGB {
        double red;
        double green;
        double blue;
    }

    private RGB kelvinToRGB(int kelvin) {
        kelvin = kelvin/100;

        RGB rgb = new RGB();

        if(kelvin <= 66) {
            rgb.red = 255;
        } else {
            rgb.red = kelvin - 60;

            rgb.red = 329.698727446 * Math.pow(rgb.red, -0.1332047592);

            if (rgb.red < 0) {
                rgb.red = 0;
            }

            if (rgb.red > 255) {
                rgb.red = 255;
            }
        }

        if(kelvin <= 66) {
            rgb.green = kelvin;
            rgb.green = 99.4708025861 * Math.log(rgb.green) - 161.1195681661;
            if (rgb.green < 0) {
                rgb.green = 0;
            }
            if (rgb.green > 255) {
                rgb.green = 255;
            }
        } else {
            rgb.green = kelvin - 60;
            rgb.green = 288.1221695283 * Math.pow(rgb.green, -0.0755148492);
            if (rgb.green < 0) {
                rgb.green = 0;
            }
            if (rgb.green > 255) {
                rgb.green = 255;
            }
        }

        if (kelvin >= 66) {
            rgb.blue = 255;
        } else if(kelvin <= 19) {
            rgb.blue = 0;
        } else {
            rgb.blue = kelvin - 10;
            rgb.blue = 138.5177312231 * Math.log(rgb.blue) - 305.0447927307;
            if (rgb.blue < 0) {
                rgb.blue = 0;
            }
            if (rgb.blue > 255) {
                rgb.blue = 255;
            }
        }

        return rgb;
    }
    /**
     * A callback that notifies clients when the progress level has been
     * changed. This includes changes that were initiated by the user through a
     * touch gesture or arrow key/trackball as well as changes that were initiated
     * programmatically.
     */
    public interface OnColourSeekBarChangeListener {

        /**
         * Notification that the progress level has changed. Clients can use the fromUser parameter
         * to distinguish user-initiated changes from those that occurred programmatically.
         *
         * @param seekBar The SeekBar whose progress has changed
         * @param progress The current progress level. This will be in the range 0..max where max
         *        was set by {@link ProgressBar#setMax(int)}. (The default value for max is 100.)
         * @param fromUser True if the progress change was initiated by the user.
         */
        void onProgressChanged(ColourSeekBar seekBar, int progress, boolean fromUser);

        /**
         * Notification that the user has started a touch gesture. Clients may want to use this
         * to disable advancing the seekbar.
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStartTrackingTouch(ColourSeekBar seekBar);

        /**
         * Notification that the user has finished a touch gesture. Clients may want to use this
         * to re-enable advancing the seekbar.
         * @param seekBar The SeekBar in which the touch gesture began
         */
        void onStopTrackingTouch(ColourSeekBar seekBar);
    }
}
