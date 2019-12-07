package com.example.adminquickmed;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    Animation app_splah;
    ImageView app_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //load animation
        app_splah = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        //load element
        app_logo = findViewById(R.id.applogo);

        //run animation
        app_logo.startAnimation(app_splah);

        //membuat Timer untuk pindah  activity otomatis
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent logo = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(logo);
                finish();
            }
        }, 2000); //2000ms = 2s
    }
}
