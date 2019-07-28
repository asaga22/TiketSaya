package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckoutActivity extends AppCompatActivity {

    LinearLayout btn_back;
    ImageView icon_warning;
    Button btn_buy_ticket, btn_minus, btn_plus;
    TextView jumlah_tiket, text_mybalance, text_total_harga, nama_wisata, lokasi, ketentuan;
    Integer banyak_tiket = 1;
    Integer total_harga = 0, harga_tiket = 0, mybalance = 0, sisa_balance = 0;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata="";
    String time_wisata="";

    //generate nomor tiket secara random
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");
        Toast.makeText(getApplicationContext(), "Jenis Tiket : "+jenis_tiket_baru, Toast.LENGTH_SHORT).show();

        btn_back = findViewById(R.id.btn_back);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);
        jumlah_tiket = findViewById(R.id.jumlah_tiket);
        text_mybalance = findViewById(R.id.text_mybalance);
        text_total_harga = findViewById(R.id.text_total_harga);
        icon_warning = findViewById(R.id.icon_warning);
        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        //set value baru beberapa komponen
        text_total_harga.setText(total_harga.toString());

        //Secara deafault nilai banyaknya tiket 1
        //karena minimal beli 1 jadi,
        //secara default button minus disabled
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);

        icon_warning.setVisibility(View.GONE);

        //mengambil data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                text_mybalance.setText("IDR "+ mybalance+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                // menimpa data yang ada dengan data yang baru
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                harga_tiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                total_harga = harga_tiket * banyak_tiket;
                text_total_harga.setText("IDR "+ total_harga+"");
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banyak_tiket-=1;
                jumlah_tiket.setText(banyak_tiket.toString());
                if(banyak_tiket < 2 ){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                total_harga = harga_tiket * banyak_tiket;
                text_total_harga.setText("IDR "+ total_harga+"");
                if (total_harga < mybalance){
                    btn_buy_ticket.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_buy_ticket.setEnabled(true);
                    text_mybalance.setTextColor(Color.parseColor("#203DD1"));
                    icon_warning.setVisibility(View.GONE);
                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banyak_tiket+=1;
                jumlah_tiket.setText(banyak_tiket.toString());
                if (banyak_tiket > 1 ){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                total_harga = harga_tiket * banyak_tiket;
                text_total_harga.setText("IDR "+ total_harga+"");
                if (total_harga > mybalance){
                    btn_buy_ticket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    text_mybalance.setTextColor(Color.parseColor("#D1206B"));
                    icon_warning.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menyimpan data user pada firebase dan membuat table baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets").
                        child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(banyak_tiket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent toSuccessBuyTicket = new Intent(TicketCheckoutActivity.this, SuccessBuyTicketActivity.class);
                        startActivity(toSuccessBuyTicket);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //update balance user setelah beli tiket
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        sisa_balance = mybalance - total_harga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDetailsActivity = new Intent(TicketCheckoutActivity.this, TicketDetailsActivity.class);
                toDetailsActivity.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(toDetailsActivity);
            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
