package com.example.eventhunt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class DetailArtist extends AppCompatActivity {


    TextView detailDesc, detailTitle, detailEntry;
    ImageView detailImage;
    Button book;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artist);

        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailEntry = findViewById(R.id.detailEntry);
        book = findViewById(R.id.book);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailEntry.setText(bundle.getString("Fees for a hour"));


            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),BookArtist.class);
                String str=detailEntry.getText().toString();
                String str2=detailTitle.getText().toString();

                intent.putExtra("value1",str);
                intent.putExtra("value2",str2);

                startActivity(intent);

            }
        });

    }
}

