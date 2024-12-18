package com.example.eventhunt;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventhunt.models.UserModels;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignUp extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText signupEmail, signupPassword,signupContact,signupName;
     Button signupButton;
     TextView loginRedirectText;
     CheckBox show;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        auth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupName = findViewById(R.id.signup_name);
        signupContact = findViewById(R.id.signup_contact);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        show = findViewById(R.id.show);

        signupButton.setOnClickListener(view -> {
            if (!validateEmail() | !validatePass() | !validateName() | !validateContact()) {

            } else {
                createUser();
            }
        });

        loginRedirectText.setOnClickListener(view -> startActivity(new Intent(UserSignUp.this, UserLogin.class)));
        show.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                signupPassword.setTransformationMethod(null);
            }
            else{
                signupPassword.setTransformationMethod(new PasswordTransformationMethod());

            }
        });


    }
    public void createUser(){
        String email = signupEmail.getText().toString();
        String pass = signupPassword.getText().toString();
        String contact = signupContact.getText().toString();
        String name = signupName.getText().toString();

            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            UserModels userModels = new UserModels(name, contact, email, pass);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("users").child(id).setValue(userModels);
                            Toast.makeText(UserSignUp.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserSignUp.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(UserSignUp.this, "Email already registered", Toast.LENGTH_SHORT).show();
                            signupEmail.requestFocus();
                        }
                    });
        }

    public Boolean validateName() {
        String name = signupName.getText().toString();
        if (name.isEmpty()) {
            signupName.setError("Name cannot be empty");
            signupName.requestFocus();
            return false;
        }
        else {
            signupName.setError(null);
            return true;
        }
    }

    public Boolean validateContact() {
        String contact = signupContact.getText().toString();
        if (contact.isEmpty()) {
            signupContact.setError("Contact cannot be empty");
            signupContact.requestFocus();
            return false;
        }
       else if (!Patterns.PHONE.matcher(contact).matches()) {
            signupContact.setError("Enter a valid contact");
            signupContact.requestFocus();
            return false;

        }
       else if (contact.length()<10) {
            signupContact.setError("Contact must have 10 digits");
            signupContact.requestFocus();
            return false;

        }
        else {
            signupContact.setError(null);
            return true;
        }
    }

    public Boolean validateEmail() {
        String email = signupEmail.getText().toString();
        if (email.isEmpty()){
            signupEmail.setError("Email cannot be empty");
            signupEmail.requestFocus();
            return false;

        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Enter a valid email");
            signupEmail.requestFocus();
            return false;

        } else {
            signupEmail.setError(null);
            return true;
        }
    }
    public Boolean validatePass() {
        String pass = signupPassword.getText().toString();
        if (pass.isEmpty()) {
            signupPassword.setError("Password cannot be empty");
            signupPassword.requestFocus();
            return false;

        } else if (pass.length()<8) {
            signupPassword.setError("Password must be minimum of 8 character");
            signupPassword.requestFocus();
            return false;
        }
        else {
            signupPassword.setError(null);
            return true;
        }
    }


}
