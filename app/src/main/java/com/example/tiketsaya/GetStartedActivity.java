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

public class GetStartedActivity extends AppCompatActivity {

    Button btn_signin, btn_create_account;
    Animation app_splash, btt, ttb;
    ImageView emblem_app;
    TextView intro_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        btn_signin = findViewById(R.id.btn_signin);
        btn_create_account = findViewById(R.id.btn_create_account);
        emblem_app = findViewById(R.id.emblem_app);
        intro_app = findViewById(R.id.intro_app);

        //Start animation
        emblem_app.startAnimation(ttb);
        intro_app.startAnimation(ttb);
        btn_signin.startAnimation(btt);
        btn_create_account.startAnimation(btt);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignIn = new Intent(GetStartedActivity.this, SignInActivity.class);
                startActivity(toSignIn);
            }
        });

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(GetStartedActivity.this, RegisterOneActivity.class);
                startActivity(toRegister);
            }
        });

    }
}
