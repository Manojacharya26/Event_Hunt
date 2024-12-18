package com.example.eventhunt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventhunt.models.UserModels;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    TextView editEmail,  editPassword;
    EditText editName,editContact;
    Button saveButton;
    String userID;
DatabaseReference reference;
FirebaseUser user;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editContact = findViewById(R.id.editContact);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  if ( !validateName() | !validateContact()) {

            } else {

                String name = editName.getText().toString();
                String contact = editContact.getText().toString();
                database.getReference("users").child(userID).child("name").setValue(name);
                database.getReference("users").child(userID).child("contact").setValue(contact);
                Toast.makeText(getApplicationContext(), " Profile updated", Toast.LENGTH_SHORT).show();
            }
            }
        });
        database.getReference("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // user UserProfile=snapshot.getValue(EditProfileActivity.this);
                UserModels userModels = snapshot.getValue(UserModels.class);

                if (userModels != null) {
                    String name = userModels.getName().toString();
                    String contact = userModels.getContact();
                    String email = userModels.getEmail();
                    String password = userModels.getPassword();

                    editName.setText(name);
                    editContact.setText(contact);
                    editEmail.setText(email);
                    editPassword.setText(password);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

        public Boolean validateName() {
            String name = editName.getText().toString();
            if (name.isEmpty()) {
                editName.setError("Name cannot be empty");
                editName.requestFocus();
                return false;
            }
            else {
                editName.setError(null);
                return true;
            }
        }

        public Boolean validateContact() {
            String contact = editContact.getText().toString();
            if (contact.isEmpty()) {
                editContact.setError("Contact cannot be empty");
                editContact.requestFocus();
                return false;
            }
            else if (!Patterns.PHONE.matcher(contact).matches()) {
                editContact.setError("Enter a valid contact");
                editContact.requestFocus();
                return false;

            }
            else if (contact.length()<10) {
                editContact.setError("Contact must have 10 digits");
                editContact.requestFocus();
                return false;

            }
            else {
                editContact.setError(null);
                return true;
            }
        }
    }





