package com.example.eventhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminOption extends AppCompatActivity {
    Button adminLogin,adminRegistration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_option);
        adminLogin=findViewById(R.id.adminLogin);
        adminRegistration=findViewById(R.id.adminRegistration);

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminOption.this, AdminLogin.class);
                startActivity(intent);
            }
        });
        adminRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminOption.this, AdminRegistration.class);
                startActivity(intent);
            }
        });
    }
}