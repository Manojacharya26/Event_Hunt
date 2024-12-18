package com.example.eventhunt;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadTitle, uploadDesc, uploadLoc,uploadDate,uploadTime,uploadEntry;
    String imageURL;
    Uri uri;

    final Calendar myCalendar=Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadImage = findViewById(R.id.uploadImage);
        uploadTitle = findViewById(R.id.uploadTitle);
        uploadLoc = findViewById(R.id.updateLoc);
        uploadTime = findViewById(R.id.updateTime);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadDate = findViewById(R.id.updateDate);
        uploadEntry = findViewById(R.id.updateEntry);
        saveButton = findViewById(R.id.uploadButton);


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker view, int year, int month, int day) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);

                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                updateDate();

            }

        };

        uploadDate.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                 DatePickerDialog datePickerDialog=new DatePickerDialog(UploadActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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

        uploadTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(UploadActivity.this,time,myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),false).show();
            }
        });

        /* uploadTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour=myCalendar.get(Calendar.HOUR);
                int minutes=myCalendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(UploadActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfday, int minutes) {
                        String amPm;
                        if(hourOfday>=12)
                        {
                            amPm="PM";
                        }else {
                            amPm="AM";

                        }
                        uploadTime.setText(hourOfday +":"+ minutes +" " +amPm);


                    }
                },hour,minutes,false);
                timePickerDialog.show();
            }
        });*/




        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    private void updateDate() {

        String myFormat = "E , LLL dd, yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        uploadDate.setText(dateFormat.format(myCalendar.getTime()));

    }
    private void updateTime() {

        String myFormat = "hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        uploadTime.setText(dateFormat.format(myCalendar.getTime()));

    }



    public void saveData(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Event Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
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
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData(){

        String title = uploadTitle.getText().toString();
        String location = uploadLoc.getText().toString();
        String date = uploadDate.getText().toString();
        String time = uploadTime.getText().toString();
        String fee = uploadEntry.getText().toString();
        String desc = uploadDesc.getText().toString();


        EventDataClass eventDataClass = new EventDataClass(title,location,date,time,fee,desc,imageURL);

        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Event").child(currentDate)
                .setValue(eventDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}