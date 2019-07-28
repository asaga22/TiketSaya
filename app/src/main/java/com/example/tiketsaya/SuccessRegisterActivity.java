package com.example.tiketsaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegisterActivity extends AppCompatActivity {

    Button btn_explore;
    ImageView icon_success;
    TextView app_title, app_subtitle;
    Animation btt, ttb, app_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        btn_explore = findViewById(R.id.btn_explore);
        icon_success =findViewById(R.id.icon_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);


        //load animation
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        //Run Animation
        btn_explore.startAnimation(btt);
        icon_success.startAnimation(app_splash);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(SuccessRegisterActivity.this, HomeActivity.class);
                startActivity(toHome);
            }
        });

    }
}
