package com.example.eventhunt;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class UserLogin extends AppCompatActivity {

     EditText loginEmail, loginPassword;
     TextView signupRedirectText;
     CheckBox show;
     Button loginButton;
     FirebaseAuth auth;
    FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        show = findViewById(R.id.show);


        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);

        database=FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> {
            if (!validateEmail() | !validatePass()) {

            } else {
                logUser();
            }

        });

        signupRedirectText.setOnClickListener(v -> startActivity(new Intent(UserLogin.this, UserSignUp.class))
        );

        show.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                loginPassword.setTransformationMethod(null);
            }
            else{
                loginPassword.setTransformationMethod(new PasswordTransformationMethod());

            }
        });

    }

        public void logUser(){
            String email = loginEmail.getText().toString();
            String pass = loginPassword.getText().toString();


                auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {

                                Toast.makeText(UserLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserLogin.this, HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(UserLogin.this, "Invalid login credential" , Toast.LENGTH_SHORT).show();
                            }
                        });
            }
    public Boolean validateEmail() {
        String email = loginEmail.getText().toString();
        if (email.isEmpty()) {
            loginEmail.setError("Email cannot be empty");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }
    public Boolean validatePass() {
        String pass = loginPassword.getText().toString();
        if (pass.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }



}