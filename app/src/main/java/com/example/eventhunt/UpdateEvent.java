package com.example.eventhunt;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateEvent extends AppCompatActivity {
        ImageView updateImage;
        Button updateButton;
        EditText updateDesc, updateTitle, updateLoc,updateDate,updateEntry,updateTime;
        String title, desc, loc,date,time,fee;
        String imageUrl;
        String key, oldImageURL;
        Uri uri;
        DatabaseReference databaseReference;
        StorageReference storageReference;
        final Calendar myCalendar=Calendar.getInstance();

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_event);
            updateImage = findViewById(R.id.uploadImage);
            updateTitle = findViewById(R.id.uploadTitle);
            updateLoc = findViewById(R.id.updateLoc);
            updateTime = findViewById(R.id.updateTime);
            updateDesc = findViewById(R.id.uploadDesc);
            updateDate = findViewById(R.id.updateDate);
            updateEntry = findViewById(R.id.updateEntry);
            updateButton = findViewById(R.id.uploadButton);
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override

                public void onDateSet(DatePicker view, int year, int month, int day) {

                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);

                    myCalendar.set(Calendar.DAY_OF_MONTH, day);

                    updateDate();

                }

            };

            updateDate.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {

                    DatePickerDialog datePickerDialog=new DatePickerDialog(UpdateEvent.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                    datePickerDialog.show();

                }

            });

            TimePickerDialog.OnTimeSetListener time= new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int mintues) {
                    myCalendar.set(Calendar.HOUR, hour);
                    myCalendar.set(Calendar.MINUTE, mintues);

                    updateTime();
                }
            };

            updateTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new TimePickerDialog(UpdateEvent.this,time,myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),false).show();
                }
            });
            ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK){
                                Intent data = result.getData();
                                uri = data.getData();
                                updateImage.setImageURI(uri);
                            } else {
                                Toast.makeText(UpdateEvent.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                Glide.with(UpdateEvent.this).load(bundle.getString("Image")).into(updateImage);
                updateTitle.setText(bundle.getString("Title"));
                updateDesc.setText(bundle.getString("Description"));
                updateLoc.setText(bundle.getString("Location"));
                updateDate.setText(bundle.getString("Date"));
                updateTime.setText(bundle.getString("Time"));
                updateEntry.setText(bundle.getString("Entry fee"));
                key = bundle.getString("Key");
                oldImageURL = bundle.getString("Image");
            }
            databaseReference = FirebaseDatabase.getInstance().getReference("Event").child(key);
            updateImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPicker = new Intent(Intent.ACTION_PICK);
                    photoPicker.setType("image/*");
                    activityResultLauncher.launch(photoPicker);
                }
            });
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveData();
                    Intent intent = new Intent(UpdateEvent.this, EventSetting.class);
                    startActivity(intent);
                }
            });
        }
    private void updateDate() {

        String myFormat = "E , LLL dd, yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        updateDate.setText(dateFormat.format(myCalendar.getTime()));

    }
    private void updateTime() {

        String myFormat = "hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        updateTime.setText(dateFormat.format(myCalendar.getTime()));

    }
        public void saveData(){
            storageReference = FirebaseStorage.getInstance().getReference().child("Event Images").child(uri.getLastPathSegment());
            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateEvent.this);
            builder.setCancelable(false);
            builder.setView(R.layout.progress_layout);
            AlertDialog dialog = builder.create();
            dialog.show();
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    updateData();
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
        }
        public void updateData(){
            title = updateTitle.getText().toString();
            desc = updateDesc.getText().toString();

             loc = updateLoc.getText().toString();
             date = updateDate.getText().toString();
             time = updateTime.getText().toString();
             fee = updateEntry.getText().toString();


            EventDataClass eventDataClass = new EventDataClass(title,loc,date,time,fee,desc,imageUrl);
            databaseReference.setValue(eventDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                        reference.delete();
                        Toast.makeText(UpdateEvent.this, "Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }