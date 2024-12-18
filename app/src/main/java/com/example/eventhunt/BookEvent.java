package com.example.eventhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Calendar;

public class BookEvent extends AppCompatActivity {
    TextView totAmount,uploadFees,uploadTitle,uploadLoc,uploadDate,uploadTime,walletAmt;
    EditText noTicket;
    Button calculate,pay,cancel;
    int v1,v2,result;
    FirebaseDatabase database;
     FirebaseAuth auth;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_event);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        UserID=FirebaseAuth.getInstance().getCurrentUser().getUid();

        walletAmt=findViewById(R.id.walletAmt);
        totAmount=findViewById(R.id.totAmount);
        uploadTitle=findViewById(R.id.title);
        uploadLoc=findViewById(R.id.location);
        uploadDate=findViewById(R.id.date);
        uploadTime=findViewById(R.id.time);
        uploadFees=findViewById(R.id.entryFee);
        noTicket=findViewById(R.id.noTicket);
        calculate=findViewById(R.id.calculate);
        pay=findViewById(R.id.pay);
        cancel=findViewById(R.id.cancel);

        Intent intent=getIntent();
        String i1=intent.getExtras().getString("value1");
        String i2=intent.getExtras().getString("value2");
        String i3=intent.getExtras().getString("value3");
        String i4=intent.getExtras().getString("value4");
        String i5=intent.getExtras().getString("value5");

        uploadFees.setText(i1);
        uploadTitle.setText(i2);
        uploadLoc.setText(i3);
        uploadDate.setText(i4);
        uploadTime.setText(i5);

        balance();
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noTicket.getText().toString().isEmpty()){
                    noTicket.setError("No.of ticket cannot be empty");
                    noTicket.requestFocus();
                }  else if(Integer.parseInt(noTicket.getText().toString())==0){
                    noTicket.setError("duration cannot be zero");
                    noTicket.requestFocus();
                }else {
                    v1 = Integer.parseInt(uploadFees.getText().toString());
                    v2 = Integer.parseInt(noTicket.getText().toString());
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
                Intent intent=new Intent(BookEvent.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void uploadData(){

        String title = uploadTitle.getText().toString();
        String location = uploadLoc.getText().toString();
        String date = uploadDate.getText().toString();
        String time = uploadTime.getText().toString();
        String fee = uploadFees.getText().toString();
        String ticket = noTicket.getText().toString();
        String total = totAmount.getText().toString();


        BookEventDataClass bookEventDataClass = new BookEventDataClass(title,location,time,date,fee,ticket,total);
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


        FirebaseDatabase.getInstance().getReference("Booked Event").child(UserID).child(currentDate)
                .setValue(bookEventDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(BookEvent.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
       FirebaseDatabase.getInstance().getReference("BookedEvent").child(currentDate)
                .setValue(bookEventDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(BookEvent.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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