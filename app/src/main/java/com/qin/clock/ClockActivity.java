package com.qin.clock;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lewis.ClockView;

/**
 * @author wenqin 2017-07-26 11:46
 */

public class ClockActivity extends AppCompatActivity {

    private ClockView mHourClock, mMinuteClock, mSecondClock;
    private TextView mTimeText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        mHourClock = (ClockView) findViewById(R.id.hour_clock);
        mMinuteClock = (ClockView) findViewById(R.id.minute_clock);
        mSecondClock = (ClockView) findViewById(R.id.second_clock);
        mTimeText = (TextView) findViewById(R.id.time_text);

        mMinuteClock.setDialColor(ContextCompat.getColor(this, R.color.blue)).setMinuteColor(Color.WHITE)
                .setClockBorderColor(Color.WHITE)
                .draw();

        mSecondClock.setDialColor(ContextCompat.getColor(this, R.color.blue));
    }

    public void onStartClick(View view) {
        double d1 = Math.random();
        int hour = (int) (d1 * 24);
        double d2 = Math.random();
        int minute = (int) (d2 * 60);
        double d3 = Math.random();
        int second = (int) (d3 * 60);

        mHourClock.startHour(hour);
        mMinuteClock.startMinute(minute);
        mSecondClock.startMinute(second);

        mTimeText.setText(getTimeStr(hour, minute, second));
    }

    private String getTimeStr(int hour, int minute, int second) {
        String result = hour + "";
        if (hour < 10) {
            result = "0" + hour;
        }
        if (minute < 10) {
            result += " : " + "0" + minute;
        } else {
            result += " : " + minute;
        }
        if (second < 10) {
            result += " : " + "0" + second;
        } else {
            result += " : " + second;
        }
        return result;
    }
}
