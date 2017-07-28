package com.qin.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 *
 * @author wenqin 2017-07-26 11:45
 */

public class MenuActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClickBtnClick(View view) {
        Intent intent = new Intent(this, ClockActivity.class);
        startActivity(intent);
    }

    public void onNormalClick(View view) {
        Intent intent = new Intent(this, NormalActivity.class);
        startActivity(intent);
    }
}
