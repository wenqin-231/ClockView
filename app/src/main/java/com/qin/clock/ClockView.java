package com.qin.clock;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author wenqin 2017-07-20 09:40
 */

public class ClockView extends View {

    private static final String TAG = "ClockView";

    private Paint mDialPaint;
    private Paint mCirclePaint;
    private Paint mHourPaint;
    private Paint mMinutePaint;

    private int mCircleRadius = 100;
    private int mStrokeWidth = 10;

    private int mHourLength;
    private int mMinuteLength;

    private int mHourWidth;
    private int mMinuteWith;

    @ColorInt
    private int mHourColor = 0xfff5f5f5;
    @ColorInt
    private int mMinuteColor = 0xfff5f5f5;
    @ColorInt
    private int mClockBorderColor = 0xff616161;
    @ColorInt
    private int mDialColor = 0xff81d4fa;

    private long mTimeMills = 0;

    private int centerX;
    private int centerY;

    private float oldMinuteRote = 0;
    private float oldHourRote = 0;

    private float minuteRote = 0;
    private float hourRote = 0;

    private ValueAnimator mMinuteAnimator;
    private ValueAnimator mHourAnimator;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setBackgroundColor(Color.TRANSPARENT);
        initDrawTool();
    }

    private void initDrawTool() {
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setStyle(Paint.Style.FILL);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mMinutePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMinutePaint.setStyle(Paint.Style.FILL);

        mHourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHourPaint.setStyle(Paint.Style.FILL);

//        centerX = mCircleRadius + mStrokeWidth / 2 + getPaddingLeft();
//        centerY = mCircleRadius + mStrokeWidth / 2 + getPaddingRight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (minuteRote >= 360f) {
            minuteRote %= 360f;
        }

        if (hourRote >= 360f) {
            hourRote %= 360f;
        }

        canvas.save();
        mDialPaint.setColor(mDialColor);
        mCirclePaint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(centerX, centerY, mCircleRadius, mDialPaint);
        canvas.restore();

        canvas.save();
        mCirclePaint.setColor(mClockBorderColor);
        canvas.drawCircle(centerX, centerY, mCircleRadius, mCirclePaint);
        canvas.restore();

        // Draw hour
        canvas.save();
        canvas.rotate(hourRote, centerX, centerY);
        mHourPaint.setStrokeWidth(mHourWidth);
        mHourPaint.setColor(mHourColor);
        canvas.drawLine(centerX, centerY, centerX, centerY - mHourLength, mHourPaint);
        canvas.drawCircle(centerX, centerY, 2, mHourPaint);
        canvas.restore();

        // Draw minute
        canvas.save();
        canvas.rotate(minuteRote, centerX, centerY);
        mMinutePaint.setStrokeWidth(mMinuteWith);
        mMinutePaint.setColor(mMinuteColor);
        canvas.drawLine(centerX, centerY, centerX, centerY - mMinuteLength, mMinutePaint);
        canvas.drawCircle(centerX, centerY, 2, mMinutePaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // fix warp_content not work
        int desireWidth = mCircleRadius * 2 + mStrokeWidth + getPaddingLeft() + getPaddingRight();
        int desireHeight = mCircleRadius * 2 + mStrokeWidth + getPaddingTop() + getPaddingBottom();

        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (withMode == MeasureSpec.EXACTLY) {
            width = withSize;
        } else if (withMode == MeasureSpec.AT_MOST) {
            width = Math.min(desireWidth, withSize);
        } else {
            width = desireWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightMeasureSpec;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desireHeight, heightSize);
        } else {
            height = desireHeight;
        }

        mCircleRadius = (int) ((width - getPaddingRight() - getPaddingLeft() - mStrokeWidth) / 2.0);
        mHourWidth = (int) (mCircleRadius * 0.08);
        mMinuteWith = (int) (mCircleRadius * 0.06);
        mHourLength = (int) (mCircleRadius * 0.6);
        mMinuteLength = (int) (mCircleRadius * 0.8);
        centerX = mCircleRadius + mStrokeWidth / 2 + getPaddingLeft();
        centerY = mCircleRadius + mStrokeWidth / 2 + getPaddingRight();

        setMeasuredDimension(width, height);
    }

    private void startMinuteAnim(int minute) {
        float startDegree = oldMinuteRote;
        Log.d(TAG, "startDegree-->" + startDegree);

        float roteDiff = getMinuteRote(minute) - startDegree;
        float endDegree = getMinuteRote(minute);
        if (roteDiff < 0) {
            endDegree = 360f + getMinuteRote(minute);
        }
        if (endDegree < startDegree) {
            endDegree = getMinuteRote(minute);
        }
        Log.d(TAG, "endDegree-->" + endDegree);
        final float floatDegree = endDegree;

        mMinuteAnimator = ValueAnimator.ofFloat(startDegree, endDegree);
        minuteRote = 0f;

        mMinuteAnimator.setDuration(500);
        mMinuteAnimator.setInterpolator(new LinearInterpolator());
        mMinuteAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private float lastDrawValue = 0f;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float newValue = (float) animation.getAnimatedValue();

                Log.d(TAG, "newValue-->" + newValue);
                float increasedDrawValue = newValue - lastDrawValue;
                Log.d(TAG, "increasedDrawValue-->" + increasedDrawValue);
                if (increasedDrawValue < 0) {
                    increasedDrawValue += floatDegree;
                }
                if (increasedDrawValue >= 0.1f) {
                    lastDrawValue = newValue;
                    minuteRote += increasedDrawValue;
                    Log.d(TAG, "minuteRote-->" + minuteRote);
                    invalidate();
                }
            }
        });
        mMinuteAnimator.start();
    }

    public void startHorAnimator(int hour) {
        float startDegree = oldHourRote;
        Log.d(TAG, "startDegree-->" + startDegree);

        float endDegree = getHourRote(hour);
        float roteDiff = getHourRote(hour) - startDegree;
        if (roteDiff < 0) {
            endDegree = 360f + getHourRote(hour);
        }
        if (endDegree < startDegree) {
            endDegree = getHourRote(hour);
        }
        Log.d(TAG, "endDegree-->" + endDegree);
        final float floatDegree = endDegree;
        mHourAnimator = ValueAnimator.ofFloat(startDegree, endDegree);
        hourRote = 0f;

        mHourAnimator.setDuration(500);
        mHourAnimator.setInterpolator(new LinearInterpolator());
        mHourAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private float lastDrawValue = 0f;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float newValue = (float) animation.getAnimatedValue();

                Log.d(TAG, "newValue-->" + newValue);
                float increasedDrawValue = newValue - lastDrawValue;
                Log.d(TAG, "increasedDrawValue-->" + increasedDrawValue);
                if (increasedDrawValue < 0) {
                    increasedDrawValue += floatDegree;
                }
                if (increasedDrawValue >= 0.1f) {
                    lastDrawValue = newValue;
                    hourRote += increasedDrawValue;
                    Log.d(TAG, "hourRote-->" + hourRote);
                    invalidate();
                }
            }
        });
        mHourAnimator.start();
    }

    private void clearAnim() {
        if (mMinuteAnimator != null && (mMinuteAnimator.isStarted() || mMinuteAnimator.isRunning())) {
            mMinuteAnimator.cancel();
        }

        if (mHourAnimator != null && (mHourAnimator.isStarted() || mHourAnimator.isRunning())) {
            mHourAnimator.cancel();
        }
    }

    private int getHourRote(int hour) {
        if (hour >= 12) {
            hour -= 12;
        }
        return hour * 30;
    }

    private int getMinuteRote(int minute) {
        return minute * 6;
    }

    /* public method*/

    public void start(long timeMills) {
        int hour = com.qin.clock.utils.TimeUtils.Mills2Hour(timeMills);
        int minute = com.qin.clock.utils.TimeUtils.Mills2Minute(timeMills);
        mTimeMills = timeMills;

        Log.d(TAG, "hour-->" + hour);
        Log.d(TAG, "minute-->" + minute);

        oldHourRote = hourRote;
        oldMinuteRote = minuteRote;

        clearAnim();
        startMinuteAnim(minute);
        startHorAnimator(hour);
    }

    public ClockView setCircleRadius(int circleRadius) {
        mCircleRadius = circleRadius;
        return this;
    }

    public ClockView setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        return this;
    }

    public ClockView setHourLength(int hourLength) {
        mHourLength = hourLength;
        return this;
    }

    public ClockView setMinuteLength(int minuteLength) {
        mMinuteLength = minuteLength;
        return this;
    }

    public ClockView setHourWidth(int hourWidth) {
        mHourWidth = hourWidth;
        return this;
    }

    public ClockView setMinuteWith(int minuteWith) {
        mMinuteWith = minuteWith;
        return this;
    }

    public ClockView setHourColor(int hourColor) {
        mHourColor = hourColor;
        return this;
    }

    public ClockView setMinuteColor(int minuteColor) {
        mMinuteColor = minuteColor;
        return this;
    }

    public ClockView setClockBorderColor(int clockBorderColor) {
        mClockBorderColor = clockBorderColor;
        return this;
    }

    public ClockView setDialColor(int dialColor) {
        mDialColor = dialColor;
        return this;
    }

    public void draw() {
        invalidate();
    }
}
