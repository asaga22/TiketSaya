package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneActivity extends AppCompatActivity {

    Button btn_continue;
    LinearLayout btn_back;
    EditText username, email_address, password;
    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        btn_continue = findViewById(R.id.btn_continue);
        btn_back = findViewById(R.id.btn_back);
        username = findViewById(R.id.username);
        email_address = findViewById(R.id.email_address);
        password = findViewById(R.id.password);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ubah state button continue setelah di klik
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading...");

                String zusername = username.getText().toString();
                String zemail_address = email_address.getText().toString();
                String zpassword = password.getText().toString();

                if (zusername.isEmpty()){
                    Toast.makeText(getApplicationContext(), "username wajib diisi", Toast.LENGTH_SHORT).show();
                    //ubah state button continue setelah di klik
                    btn_continue.setEnabled(true);
                    btn_continue.setText("CONTINUE");
                } else{
                    if(zemail_address.isEmpty()){
                        Toast.makeText(getApplicationContext(), "email address wajib diisi", Toast.LENGTH_SHORT).show();
                        //ubah state button continue setelah di klik
                        btn_continue.setEnabled(true);
                        btn_continue.setText("CONTINUE");
                    }
                    else if(zpassword.isEmpty()){
                        Toast.makeText(getApplicationContext(), "password wajib diisi", Toast.LENGTH_SHORT).show();
                        //ubah state button continue setelah di klik
                        btn_continue.setEnabled(true);
                        btn_continue.setText("CONTINUE");
                    }
                    else{
                        //menyimpan data kepada local storage (handphone)
                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(username_key, username.getText().toString());
                        editor.apply();

                        //simpan kepada database
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                                dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                dataSnapshot.getRef().child("user_balance").setValue(120000);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        //pindah activity
                        Intent toRegisterTwo = new Intent(RegisterOneActivity.this, RegisterTwoActivity.class);
                        startActivity(toRegisterTwo);
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toGetStarted = new Intent(RegisterOneActivity.this, GetStartedActivity.class);
                startActivity(toGetStarted);
            }
        });

    }
}
