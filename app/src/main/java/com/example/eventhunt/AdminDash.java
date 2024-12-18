package com.example.eventhunt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class AdminDash extends AppCompatActivity {
CardView event,logout,venue,artist,food,order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);
        event=findViewById(R.id.event);
        venue=findViewById(R.id.venue);
        logout =findViewById(R.id.logout);
        artist=findViewById(R.id.artist);
        food =findViewById(R.id.food);
        order=findViewById(R.id.order);


        event.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), EventSetting.class);
            startActivity(intent);


        });
        order.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), AdminOrder.class);
            startActivity(intent);


        });

        venue.setOnClickListener(view -> {
            Intent intent=new Intent(AdminDash.this, VenueSetting.class);
            startActivity(intent);

        });
        artist.setOnClickListener(view -> {
            Intent intent=new Intent(AdminDash.this, ArtistSetting.class);
            startActivity(intent);

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AdminDash.this);
                builder.setMessage("Are you sure you want to logout.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                AlertDialog alert=builder.create();
                alert.show();


            }
        });



    }
}