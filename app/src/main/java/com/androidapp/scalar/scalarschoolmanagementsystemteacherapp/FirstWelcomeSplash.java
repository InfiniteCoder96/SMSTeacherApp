package com.androidapp.scalar.scalarschoolmanagementsystemteacherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstWelcomeSplash extends AppCompatActivity {

    private TextView mTvwlbk,mTvmih,mTvfooter;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_splash);

        mTvwlbk = (TextView) findViewById(R.id.wlbk);
        mTvmih = (TextView) findViewById(R.id.mih);
        mTvfooter = (TextView) findViewById(R.id.footer);

        mIv = (ImageView) findViewById(R.id.scllogo);

        Animation welcomeSplash = AnimationUtils.loadAnimation(this, R.anim.welcomesplachtransition);

        mTvwlbk.startAnimation(welcomeSplash);
        mTvmih.startAnimation(welcomeSplash);
        mTvfooter.startAnimation(welcomeSplash);
        mIv.startAnimation(welcomeSplash);

        final Intent intent = new Intent(this, ControlPanel.class);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000);

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.start();
    }
}
