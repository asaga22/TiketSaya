package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    Animation app_splash, btt;
    ImageView app_logo;
    TextView app_slogan;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //load element
        app_logo = findViewById(R.id.app_logo);
        app_slogan =findViewById(R.id.app_slogan);

        //run animation
        app_logo.startAnimation(app_splash);
        app_slogan.startAnimation(btt);

        getUsernameLocal();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        if(username_key_new.isEmpty()){
            // Splash for 2 second to go to get started activity
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent toGetStarted = new Intent(SplashActivity.this, GetStartedActivity.class);
                    startActivity(toGetStarted);
                    finish();
                }
            }, 2000);
        } else{
            // Splash for 2 second to go to get started activity
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent toHome = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(toHome);
                    finish();
                }
            }, 2000);
        }
    }
}
