package com.giganticsheep.wifilight.ui.base.light;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.base.LightSeekBar;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 02/09/15. <p>
 * (*_*)
 */
public class BrightnessSeekBar extends LightSeekBar {

    private Path trackPath;
    private float trackLength;
    private float thumbProgressPosition;

    public BrightnessSeekBar(Context context) {
        super(context);
    }

    public BrightnessSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrightnessSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initTrack() {
        trackPaint = new Paint();
        trackPaint.setARGB(255, 0, 0, 0);
        trackPaint.setAntiAlias(true);
        trackPaint.setDither(true);
        trackPaint.setStrokeWidth(strokeWidth);
        trackPaint.setStyle(Paint.Style.STROKE);
        trackPaint.setStrokeCap(Paint.Cap.ROUND);
        trackPaint.setShadowLayer(4, 2, 2, 0x80000000);

        trackPath = new Path();

        maxValue = getContext().getResources().getInteger(R.integer.light_slider_brightness_max);
    }

    @Override
    protected void initTrackPath(int width, int height) {
        drawRect = new RectF(getPaddingLeft(),
                getPaddingTop(),
                width,
                Math.max(strokeWidth, thumbMaxHeight));
        trackLength = width;

        trackPath.moveTo(drawRect.left, drawRect.bottom/2);
        trackPath.lineTo(drawRect.right, drawRect.bottom/2);
    }

    @Override
    protected void setMeasuredDimensionIfNeeded(int width, int height) {

    }

    @Override
    protected void drawTrack(Canvas canvas) {
        canvas.drawPath(trackPath, trackPaint);
    }

    @Override
    protected void setThumbPosition(int progress) {
        thumbProgressPosition = trackLength * (progress / maxValue);
    }

    @Override
    protected void calculateThumbXYPosition() {
        thumbX = drawRect.bottom/2;
        thumbY = thumbProgressPosition;
    }

    @Override
    protected boolean touchIsOnTrack(float x, float y) {
        if(x > drawRect.bottom/2 - strokeWidth/2 - touchTolerance &&
                x < drawRect.bottom/2 + strokeWidth/2 + touchTolerance) {
            return true;
        }

        return false;
    }

    @Override
    protected float setThumbPosition(float x, float y) {
        return thumbProgressPosition = x;
    }

    @Override
    protected int progressFromThumbPosition(float thumbPosition) {
        return (int) (thumbPosition * maxValue/trackLength);
    }
}
