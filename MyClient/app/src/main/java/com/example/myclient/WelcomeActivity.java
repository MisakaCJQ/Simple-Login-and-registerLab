package com.example.myclient;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        tv1 = (TextView)findViewById(R.id.textView1);
        tv2 = (TextView)findViewById(R.id.textView2);
        tv3 = (TextView)findViewById(R.id.textView3);
        tv4 = (TextView)findViewById(R.id.textView4);

        tv1.setText(getIntent().getStringExtra("username")+"您好，以下为您的信息");
        tv2.setText("姓名："+getIntent().getStringExtra("name"));
        tv3.setText("年龄："+getIntent().getStringExtra("age"));
        tv4.setText("电话："+getIntent().getStringExtra("phone"));
    }

    public void onClickBackWel(View v){
        finish();
    }
}