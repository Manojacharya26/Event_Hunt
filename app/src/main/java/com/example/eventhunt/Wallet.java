package com.example.eventhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Wallet extends AppCompatActivity {
    Button add,addMoney;
    EditText name,number,cvv,expiry,amount;
    TextView balanceText;
    String UserID;
    FirebaseDatabase database;
    FirebaseAuth auth;
   int v1,v2,result;
    final Calendar myCalendar=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
         UserID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        add=findViewById(R.id.add);
        name=findViewById(R.id.holderName);
        number=findViewById(R.id.holderNumber);
        cvv=findViewById(R.id.holderCvv);
        expiry=findViewById(R.id.holderExpiry);
        balanceText=findViewById(R.id.balance);
        amount=findViewById(R.id.amount);
        addMoney=findViewById(R.id.addMoney);
        invisible();
        balance();

       // add.setVisibility(View.INVISIBLE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visible();
            }
        });
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(name.getText().toString().equals("Anirudh") && number.getText().toString().equals("0987654321098765")&& expiry.getText().toString().equals("07/23")&& cvv.getText().toString().equals("321"))) {
                    Toast.makeText(getApplicationContext(), "Invalid credential", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Wallet.this,Wallet.class);
                    startActivity(intent);


                }else{
                v1= Integer.parseInt(balanceText.getText().toString());
                v2= Integer.parseInt(amount.getText().toString());

                result=(v1+v2);
                database.getReference().child("Wallet").child(UserID).setValue(result);
                Toast.makeText(getApplicationContext(),amount.getText()+" Rupees add to your wallet",Toast.LENGTH_SHORT).show();
                clear();
                invisible();}
            }
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker view, int year, int month, int day) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);

                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                updateDate();

            }

        };

        expiry.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                DatePickerDialog datePickerDialog=new DatePickerDialog(Wallet.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();

            }

        });


    }
    private void updateDate() {

        String myFormat = "MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        expiry.setText(dateFormat.format(myCalendar.getTime()));

    }

    private void balance(){
        database.getReference("Wallet").child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Long accBalance = snapshot.getValue(Long.class);
                    if (accBalance != null){
                        balanceText.setText(String.valueOf(accBalance));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase","Failed to retrive data",error.toException());
            }
        });

    }
    private void clear()
    {
        name.getText().clear();
        number.getText().clear();
        cvv.getText().clear();
        amount.getText().clear();
    }
    private void invisible()
    {
        name.setVisibility(View.INVISIBLE);
        number.setVisibility(View.INVISIBLE);
        expiry.setVisibility(View.INVISIBLE);
        cvv.setVisibility(View.INVISIBLE);
        amount.setVisibility(View.INVISIBLE);
        addMoney.setVisibility(View.INVISIBLE);

    }

    private void visible()
    {
        expiry.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        number.setVisibility(View.VISIBLE);
        cvv.setVisibility(View.VISIBLE);
        amount.setVisibility(View.VISIBLE);
        addMoney.setVisibility(View.VISIBLE);

    }

}