package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    CircleView btn_to_profile;
    TextView bio, nama_lengkap, user_balance;
    ImageView pic_home_user;
    LinearLayout btn_ticket_pisa, btn_ticket_torri, btn_ticket_pagoda, btn_ticket_sphinx, btn_ticket_monas, btn_ticket_candi;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_to_profile = findViewById(R.id.btn_to_profile);
        bio = findViewById(R.id.bio);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        user_balance = findViewById(R.id.user_balance);
        pic_home_user = findViewById(R.id.pic_home_user);

        btn_ticket_pisa = findViewById(R.id.btn_ticket_pisa);
        btn_ticket_torri = findViewById(R.id.btn_ticket_torri);
        btn_ticket_pagoda = findViewById(R.id.btn_ticket_pagoda);
        btn_ticket_candi = findViewById(R.id.btn_ticket_candi);
        btn_ticket_sphinx = findViewById(R.id.btn_ticket_sphinx);
        btn_ticket_monas = findViewById(R.id.btn_ticket_monas);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("IDR " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeActivity.this).load(dataSnapshot.child("url_photo_profile").getValue().toString())
                        .centerCrop().fit().into(pic_home_user);
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });

        btn_ticket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTicketDeatils = new Intent(HomeActivity.this, TicketDetailsActivity.class);
                //meletakkan nilai atau data kepada intent
                toTicketDeatils.putExtra("jenis_tiket", "Pisa");
                startActivity(toTicketDeatils);
            }
        });

        btn_ticket_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTicketDeatils = new Intent(HomeActivity.this, TicketDetailsActivity.class);
                //meletakkan nilai atau data kepada intent
                toTicketDeatils.putExtra("jenis_tiket", "Torri");
                startActivity(toTicketDeatils);
            }
        });

        btn_ticket_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTicketDeatils = new Intent(HomeActivity.this, TicketDetailsActivity.class);
                //meletakkan nilai atau data kepada intent
                toTicketDeatils.putExtra("jenis_tiket", "Pagoda");
                startActivity(toTicketDeatils);
            }
        });

        btn_ticket_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTicketDeatils = new Intent(HomeActivity.this, TicketDetailsActivity.class);
                //meletakkan nilai atau data kepada intent
                toTicketDeatils.putExtra("jenis_tiket", "Candi");
                startActivity(toTicketDeatils);
            }
        });

        btn_ticket_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTicketDeatils = new Intent(HomeActivity.this, TicketDetailsActivity.class);
                //meletakkan nilai atau data kepada intent
                toTicketDeatils.putExtra("jenis_tiket", "Sphinx");
                startActivity(toTicketDeatils);
            }
        });

        btn_ticket_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTicketDeatils = new Intent(HomeActivity.this, TicketDetailsActivity.class);
                //meletakkan nilai atau data kepada intent
                toTicketDeatils.putExtra("jenis_tiket", "Monas");
                startActivity(toTicketDeatils);
            }
        });


        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfile = new Intent(HomeActivity.this, MyProfileActivity.class);
                startActivity(toProfile);
            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
