package com.example.processesdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.processesdemo.cityselecter.SelecterCityActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView btn_toMain;
    private TextView btn_toCitySelecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();

    }

    private void initView() {
        btn_toMain = (TextView) findViewById(R.id.btn_toMain);
        btn_toCitySelecter = (TextView) findViewById(R.id.btn_toCitySelecter);
        btn_toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        });
        btn_toCitySelecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, SelecterCityActivity.class));

            }
        });
    }
}
