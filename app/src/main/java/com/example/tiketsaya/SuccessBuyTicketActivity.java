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

public class SuccessBuyTicketActivity extends AppCompatActivity {

    Button btn_my_dasboard, btn_view_ticket;
    ImageView icon_success;
    TextView app_title, app_subtitle;
    Animation app_splash, btt, ttb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        btn_my_dasboard = findViewById(R.id.btn_my_dashboard);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        icon_success = findViewById(R.id.icon_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        //Load Animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        //Run Animation
        icon_success.startAnimation(app_splash);
        btn_my_dasboard.startAnimation(btt);
        btn_view_ticket.startAnimation(btt);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);

        btn_my_dasboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMyDashboard = new Intent(SuccessBuyTicketActivity.this, HomeActivity.class);
                startActivity(toMyDashboard);
            }
        });

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMyProfile = new Intent(SuccessBuyTicketActivity.this, MyProfileActivity.class);
                startActivity(toMyProfile);
            }
        });

    }
}
