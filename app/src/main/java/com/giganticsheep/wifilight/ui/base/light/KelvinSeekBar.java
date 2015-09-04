package com.giganticsheep.wifilight.ui.base.light;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.base.LightSeekBar;

import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 02/09/15. <p>
 * (*_*)
 */
public class KelvinSeekBar extends LightSeekBar {

    private float arcAngle = 300F;
    private float startAngle = 120F;
    private float endAngle;
    private float centreX;
    private float centreY;
    private float outerRadius;
    private float innerRadius;
    private float progressDegrees;
    private float thumbPositionDegrees;
    private float[] pointerPositionXY;

    private Path circlePath;
    private Path circleProgressPath;
    private RectF circleDrawRect;

    public KelvinSeekBar(Context context) {
        super(context);
    }

    public KelvinSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KelvinSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setMeasuredDimensionIfNeeded(int width, int height) {

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

        circlePath = new Path();
        circleProgressPath = new Path();

        endAngle = arcAngle - startAngle;

        maxValue = getContext().getResources().getInteger(R.integer.light_slider_kelvin_max);
    }

    @Override
    protected void initTrackPath(int width, int height) {
        drawRect = new RectF(getPaddingLeft(),
                getPaddingTop(),
                width,
                height);
        int extraThumbWidth = (thumbMaxWidth - strokeWidth) / 2;
        if (extraThumbWidth < 0) {
            extraThumbWidth = 0;
        }

        int extraThumbHeight = (thumbMaxHeight - strokeWidth) / 2;
        if (extraThumbHeight < 0) {
            extraThumbHeight = 0;
        }
        if (circleDrawRect == null ||
                        (circleDrawRect.right != width + extraThumbWidth + getPaddingLeft() ||
                                circleDrawRect.bottom != height + extraThumbHeight + getPaddingTop())) {
            circleDrawRect = new RectF(
                    getPaddingLeft(),
                    getPaddingTop(),
                    width + extraThumbWidth + getPaddingLeft(),
                    height + extraThumbHeight + getPaddingTop());
            Timber.d("%s :: Set the rect left : %f, top : %f, right : %f, bottom : %f",
                    toString(), drawRect.left, circleDrawRect.top, circleDrawRect.right, circleDrawRect.bottom);

            int size = Math.min(width, height);

            circlePath.addArc(circleDrawRect, startAngle, arcAngle);
            circleProgressPath.addArc(circleDrawRect, startAngle, progressDegrees);

            centreX = (circleDrawRect.right - circleDrawRect.left) / 2; // Center X for circle
            centreY = (circleDrawRect.bottom - circleDrawRect.top) / 2; // Center Y for circle

            Timber.d("Centre x : %f, Centre y : %f", centreX, centreY);

            outerRadius = size / 2; // Radius of the outer circle
            innerRadius = outerRadius - strokeWidth; // Radius of the inner circle

            Timber.d("Inner radius : %f, Outer radius : %f", innerRadius, outerRadius);
        }
    }

    @Override
    protected void drawTrack(Canvas canvas) {
        canvas.drawPath(circlePath, trackPaint);
    }

    @Override
    protected boolean touchIsOnTrack(float x, float y) {
        float distance = (float) Math.sqrt(Math.pow((x - centreX), 2) + Math.pow((y - centreY), 2));
        return distance < outerRadius + touchTolerance && distance > innerRadius - touchTolerance;
    }

    @Override
    protected float setThumbPosition(float x, float y) {
        float touchAngle;
        float xFromCentre = x - centreX;
        float yFromCentre = centreY - y;

        touchAngle = (float) (360 - (Math.toDegrees(java.lang.Math.atan2(yFromCentre, xFromCentre)) % 360));
        Timber.d("touch angle : %f", touchAngle);

        thumbPositionDegrees = touchAngle;
        thumbPositionDegrees = (thumbPositionDegrees < 0 ? 360f + thumbPositionDegrees : thumbPositionDegrees);
        thumbPositionDegrees = thumbPositionDegrees % 360f;

        Timber.d("calculateProgressFromThumbProgressDegrees() Thumb Progress degrees : %f", thumbPositionDegrees);

        return thumbPositionDegrees;
    }

    @Override
    protected int progressFromThumbPosition(float thumbPosition) {
        progressDegrees = thumbPosition - startAngle;
        progressDegrees = (progressDegrees < 0 ? 360f + progressDegrees : progressDegrees); // Verified

        Timber.d("calculateProgressFromThumbProgressDegrees() Progress degrees : %f", progressDegrees);

        circleProgressPath = new Path();
        circleProgressPath.addArc(circleDrawRect, startAngle, progressDegrees);

        int progress = Math.round(maxValue * progressDegrees / arcAngle);
        Timber.d("calculateProgressFromThumbProgressDegrees() Progress : %d", progress);

        return progress;
    }

    @Override
    protected void setThumbPosition(int progress) {
        Timber.d("setProgress() Progress : %d", progress);

        progressDegrees = arcAngle * (progress / maxValue);
        progressDegrees = progressDegrees % 360f;

        Timber.d("setProgress() Progress degrees : %f", progressDegrees);
        thumbPositionDegrees = progressDegrees + startAngle;
        thumbPositionDegrees = thumbPositionDegrees % 360f;

        Timber.d("setProgress() Thumb Progress degrees : %f", thumbPositionDegrees);
    }

    @Override
    protected void calculateThumbXYPosition() {
        pointerPositionXY = new float[2];
        PathMeasure pm = new PathMeasure(circleProgressPath, false);
        boolean returnValue = pm.getPosTan(pm.getLength(), pointerPositionXY, null);
        if(!returnValue) {
            pm = new PathMeasure(circlePath, false);
            pm.getPosTan(0, pointerPositionXY, null);
        }

        thumbX = pointerPositionXY[0];
        thumbY = pointerPositionXY[1];

        Timber.d("Calculated thumb x y : %f, %f", thumbX, thumbY);
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
}
