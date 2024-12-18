package com.example.eventhunt;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class DetailEventAdmin extends AppCompatActivity {

    TextView detailDesc, detailTitle, detailLoc,detailEntry,detailDate,detailTime;
        ImageView detailImage;
        FloatingActionButton deleteButton, editButton;
        String key = "";
        String imageUrl = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_event_admin);


            detailDesc = findViewById(R.id.detailDesc);
            detailImage = findViewById(R.id.detailImage);
            detailTitle = findViewById(R.id.detailTitle);
            detailTime = findViewById(R.id.detailTime);
            detailDate = findViewById(R.id.detailDate);
            detailEntry=findViewById(R.id.detailEntry);
            detailLoc = findViewById(R.id.detailLoc);
            deleteButton = findViewById(R.id.deleteButton);
            editButton = findViewById(R.id.editButton);


            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
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
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Event");
                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            reference.child(key).removeValue();
                            Toast.makeText(DetailEventAdmin.this, "Deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), EventSetting.class));
                            finish();
                        }
                    });
                }
            });
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetailEventAdmin.this, UpdateEvent.class)
                            .putExtra("Title", detailTitle.getText().toString())
                            .putExtra("Description", detailDesc.getText().toString())
                            .putExtra("Location", detailLoc.getText().toString())
                            .putExtra("Date",detailDate.getText().toString())
                            .putExtra("Time",detailTime.getText().toString())
                            .putExtra("Entry fee",detailEntry.getText().toString())
                            .putExtra("Image", imageUrl)
                            .putExtra("Key", key);
                    startActivity(intent);
                }
            });
        }
    }