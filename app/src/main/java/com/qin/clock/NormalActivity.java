package com.qin.clock;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lewis.clockview.ClockView;

import java.util.Calendar;

public class NormalActivity extends AppCompatActivity {

    private TextView mTimeText;
    private ClockView mClockView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        mClockView = (ClockView) findViewById(R.id.clock_view);
        mTimeText = (TextView) findViewById(R.id.clock_text);
        mTimeText.setText("00 : 00");
    }

    private String getTimeStr(int hour, int minute) {
        String result = hour + "";
        if (hour < 10) {
            result = "0" + hour;
        }
        if (minute < 10) {
            result += " : " + "0" + minute;
        } else {
            result += " : " + minute;
        }
        return result;
    }

    public void onStartClick(View view) {

        Calendar calendar = Calendar.getInstance();
        double d = Math.random();
        int hour = (int) (d * 24);
        int minute = (int) (d * 60);
        Log.d("NormalActivity -->", hour + "--" + minute);

        calendar.set(2017, 12, 12, hour, minute);
        mClockView.start(calendar.getTimeInMillis());
        mTimeText.setText(getTimeStr(hour, minute));
    }
}
