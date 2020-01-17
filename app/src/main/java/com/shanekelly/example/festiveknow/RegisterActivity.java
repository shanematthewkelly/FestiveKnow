package com.shanekelly.example.festiveknow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    //Variables
    private EditText userEmail, userPassword, userPasswordConfirm, userName;
    private Button registerButton;
    private FirebaseAuth myAuth;
    private TextView alreadyRegistered;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);



        //init views
        alreadyRegistered = findViewById(R.id.alreadyRegistered);
        userEmail = findViewById(R.id.regEmail);
        userName = findViewById(R.id.regName);
        userPassword = findViewById(R.id.regPassword);
        userPasswordConfirm = findViewById(R.id.regPasswordConfirm);
        registerButton = findViewById(R.id.registerButton);

        myAuth = FirebaseAuth.getInstance();

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alreadyReg = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(alreadyReg);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sets the register button to be invisible when clicked
                registerButton.setVisibility(View.INVISIBLE);

                //gets the EditTexts and converts them to a string
                final String email = userEmail.getText().toString();
                final String username = userName.getText().toString();
                final String password = userPassword.getText().toString();
                final String passwordconfirm = userPasswordConfirm.getText().toString();

                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || passwordconfirm.isEmpty() || !password.equals(passwordconfirm)) {

                    //if something goes wrong: all fields must be filled, we handle the error message in here
                    showMessage("Please fill in the required fields above");
                    registerButton.setVisibility(View.VISIBLE);


                } else {
                    //everything is okay and all fields are filled, we can proceed with making an account

                    //CreateUserAccount method will create the user if the email is valid
                    CreateUserAccount(email, username, password, passwordconfirm);
                }




            }
        });
    }

    //this method creates user accounts with specific email and password
    private void CreateUserAccount(String email,final String username, String password, String passwordconfirm) {

        myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //user account created successfully
                    showMessage("Account Created!");
                    //after we created the user account we need to update their name
                    updateUserInfo(username,myAuth.getCurrentUser());

                } else {

                    //account creation failed
                    showMessage("Account Creation Failed..." + task.getException().getMessage());
                    registerButton.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    //updates the user's name
    private void updateUserInfo(String username, FirebaseUser currentUser) {


        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(username).build() ;

        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    //user info updated successfully
                    showMessage("Registration Complete!");
                    updateUI();

                } else {
                    //user info failed to update
                    showMessage("Registration failed...");
                }


            }
        });

    }

    private void updateUI() {


         Intent loginpage = new Intent(getApplicationContext(), LoginActivity.class);
         startActivity(loginpage);
         finish();

    }

    //simple method that shows toast message
    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}
