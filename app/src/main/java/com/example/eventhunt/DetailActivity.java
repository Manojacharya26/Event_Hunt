package com.example.eventhunt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTitle, detailLoc, detailEntry, detailDate, detailTime;
      Button book;
    ImageView detailImage;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDesc = findViewById(R.id.detailDesc);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailTime = findViewById(R.id.detailTime);
        detailDate = findViewById(R.id.detailDate);
        detailEntry = findViewById(R.id.detailEntry);
        detailLoc = findViewById(R.id.detailLoc);

        book=findViewById(R.id.book);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailTitle.setText(bundle.getString("Title"));
            detailLoc.setText(bundle.getString("Location"));
            detailDate.setText(bundle.getString("Date"));
            detailTime.setText(bundle.getString("Time"));
            detailEntry.setText(bundle.getString("Entry fee"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

       book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), BookEvent.class);
                String str=detailEntry.getText().toString();
                String str2=detailTitle.getText().toString();
                String str3=detailLoc.getText().toString();
                String str4=detailDate.getText().toString();
                String str5=detailTime.getText().toString();
                intent.putExtra("value1",str);
                intent.putExtra("value2",str2);
                intent.putExtra("value3",str3);
                intent.putExtra("value4",str4);
                intent.putExtra("value5",str5);

                startActivity(intent);

            }
        });




    }



}
