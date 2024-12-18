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

public class BookArtist extends AppCompatActivity {
    TextView totAmount,hourCharge,uploadTitle,walletAmt;
    EditText duration,uploadLoc,uploadDate,uploadTime;
    Button calculate,pay,cancel;
    int v1,v2,result;
    final Calendar myCalendar=Calendar.getInstance();
    FirebaseDatabase database;
    FirebaseAuth auth;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_artist);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        UserID=FirebaseAuth.getInstance().getCurrentUser().getUid();

        walletAmt=findViewById(R.id.walletAmt);
        totAmount=findViewById(R.id.totAmount);
        uploadTitle=findViewById(R.id.title);
        uploadLoc=findViewById(R.id.location);
        uploadDate=findViewById(R.id.date);
        uploadTime=findViewById(R.id.time);
        hourCharge=findViewById(R.id.entryFee);
        duration=findViewById(R.id.noTicket);
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

                DatePickerDialog datePickerDialog=new DatePickerDialog(BookArtist.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
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
                new TimePickerDialog(BookArtist.this,time,myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),false).show();
            }
        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(duration.getText().toString().isEmpty()){
                    duration.setError("duration cannot be empty");
                    duration.requestFocus();
                }  else if(Integer.parseInt(duration.getText().toString())==0){

                    duration.setError("duration cannot be zero");
                    duration.requestFocus();
                }else {
                    v1 = Integer.parseInt(hourCharge.getText().toString());
                    v2 = Integer.parseInt(duration.getText().toString());
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
                String location = uploadLoc.getText().toString();

                if (date.isEmpty()){
                    uploadDate.setError("Date cannot be empty");
                }
                if (time.isEmpty()) {
                    uploadTime.setError("Time cannot be empty");
                }
                if (location.isEmpty()) {
                    uploadLoc.setError("Location cannot be empty");
                }

                if( v1==0){
                    Toast.makeText(getApplicationContext(), "Total cannot be zero", Toast.LENGTH_SHORT).show();

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
                Intent intent=new Intent(BookArtist.this,HomeActivity.class);
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
        String location = uploadLoc.getText().toString();
        String date = uploadDate.getText().toString();
        String time = uploadTime.getText().toString();
        String fee = hourCharge.getText().toString();
        String ticket = duration.getText().toString();
        String total = totAmount.getText().toString();


        BookArtistDataClass bookArtistDataClass = new BookArtistDataClass(name,location,time,date,fee,ticket,total);
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Booked Artist").child(UserID).child(currentDate)
                .setValue(bookArtistDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(BookArtist.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookArtist.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        FirebaseDatabase.getInstance().getReference("BookedArtist").child(currentDate)
                .setValue(bookArtistDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(BookArtist.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookArtist.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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