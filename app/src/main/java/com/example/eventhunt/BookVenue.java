package com.example.eventhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookVenue extends AppCompatActivity {
    TextView totAmount,hourCharge,uploadTitle,walletAmt;
    EditText uploadDuration,uploadOcc,uploadDate,uploadTime;
    Button calculate,pay,cancel;
    int v1,v2,result;
    final Calendar myCalendar=Calendar.getInstance();
    FirebaseDatabase database;
    FirebaseAuth auth;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_venue);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        UserID=FirebaseAuth.getInstance().getCurrentUser().getUid();

        walletAmt=findViewById(R.id.walletAmt);
        totAmount=findViewById(R.id.totAmount);
        uploadTitle=findViewById(R.id.title);
        uploadOcc=findViewById(R.id.location);
        uploadDate=findViewById(R.id.date);
        uploadTime=findViewById(R.id.time);
        hourCharge=findViewById(R.id.entryFee);
        uploadDuration=findViewById(R.id.noTicket);
        calculate=findViewById(R.id.calculate);
        pay=findViewById(R.id.pay);
        cancel=findViewById(R.id.cancel);

        Intent intent=getIntent();
        String i1=intent.getExtras().getString("value1");
        String i2=intent.getExtras().getString("value2");

        hourCharge.setText(i1);
        uploadTitle.setText(i2);


        balance();


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

                DatePickerDialog datePickerDialog=new DatePickerDialog(BookVenue.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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
                new TimePickerDialog(BookVenue.this,time,myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),false).show();
            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadDuration.getText().toString().isEmpty()){
                    uploadDuration.setError("duration cannot be empty");
                    uploadDuration.requestFocus();
                }  else if(Integer.parseInt(uploadDuration.getText().toString())==0){

                    uploadDuration.setError("duration cannot be zero");
                    uploadDuration.requestFocus();
                }else {
                    v1 = Integer.parseInt(hourCharge.getText().toString());
                    v2 = Integer.parseInt(uploadDuration.getText().toString());
                    result = v1 * v2;
                    totAmount.setText(String.valueOf(result));
                }
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v1=Integer.parseInt(totAmount.getText().toString());
                v2=Integer.parseInt(walletAmt.getText().toString());

                String date = uploadDate.getText().toString();
                String time = uploadTime.getText().toString();
                String location = uploadOcc.getText().toString();

                if (date.isEmpty()){
                    uploadDate.setError("Date cannot be empty");
                }
                if (time.isEmpty()) {
                    uploadTime.setError("Time cannot be empty");
                }
                if (location.isEmpty()) {
                    uploadOcc.setError("Location cannot be empty");
                }

                if( v1==0){
                    Toast.makeText(getApplicationContext(), "Total amount cannot be zero", Toast.LENGTH_SHORT).show();

                } else if (v1>v2) {
                    Toast.makeText(getApplicationContext(), "Insufficient balance in wallet to make payment", Toast.LENGTH_SHORT).show();

                } else {
                    uploadData();
                }
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookVenue.this,HomeActivity.class);
                startActivity(intent);
                finish();
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
    public void uploadData(){

        String name = uploadTitle.getText().toString();
        String occasion = uploadOcc.getText().toString();
        String date = uploadDate.getText().toString();
        String time = uploadTime.getText().toString();
        String fee = hourCharge.getText().toString();
        String duration = uploadDuration.getText().toString();
        String total = totAmount.getText().toString();


        BookVenueDataClass bookVenueDataClass = new BookVenueDataClass(name,occasion,time,date,fee,duration,total);
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Booked Venue").child(UserID).child(currentDate)
                .setValue(bookVenueDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(BookVenue.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookVenue.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        FirebaseDatabase.getInstance().getReference("BookedVenue").child(currentDate)
                .setValue(bookVenueDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(BookVenue.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookVenue.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        v1=Integer.parseInt(walletAmt.getText().toString());
        v2=Integer.parseInt(totAmount.getText().toString());
        result=v1-v2;
        database.getReference().child("Wallet").child(UserID).setValue(result);

    }
    private void balance(){
        database.getReference("Wallet").child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Long accBalance = snapshot.getValue(Long.class);
                    if (accBalance != null){
                        walletAmt.setText(String.valueOf(accBalance));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase","Failed to retrieve data",error.toException());
            }
        });

    }



}