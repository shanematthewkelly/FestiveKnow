package com.shanekelly.example.festiveknow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.shanekelly.example.festiveknow.list.ListActivity;

public class LoginActivity extends AppCompatActivity {

    //Variables
    private EditText userEmail, userPassword;
    private Button loginButton, goToRegisterBtn, readMoreExample;
    private FirebaseAuth myAuth;
    private Intent homeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init views
        userEmail = findViewById(R.id.login_mail);
        userPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.loginButton);
        goToRegisterBtn = findViewById(R.id.joinUsButton);
        readMoreExample = findViewById(R.id.readMoreButton);
        myAuth = FirebaseAuth.getInstance();
        homeScreen = new Intent(this, ListActivity.class);

        //simply goes to the Register Activity if the user clicks on the "Sign Up Now" button, if the user does not have an existing account
        goToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(goToRegister);
                finish();
            }
        });

        //Read More button - only displays a toast for example purposes
        readMoreExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "I'm just an example", Toast.LENGTH_LONG).show();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //makes the button invisible
                loginButton.setVisibility(View.INVISIBLE);

                //gets the email and password and makes it a string
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();

                //if else statement where if the email and password is empty then display an error message and set the button's visibility to visible.
                //Else sign the authenticated user in using FireBase's built in signIn method, and pass in the email and password
                if (email.isEmpty() || password.isEmpty()) {
                    showMessage("Please fill in the details above");
                    loginButton.setVisibility(View.VISIBLE);


                } else {
                    signIn(email, password);
                }

            }
        });
    }

    private void signIn(final String email, final String password) {

        //signs the user in with the made email and password that they used
        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                        //if else statement where if the task was successful then create a toast that lets the user know they have been logged in and run a method called updateUI() that updates the user interface when they logged in
                        //Else spit out an error message
                        if (task.isSuccessful()) {

                            loginButton.setVisibility(View.VISIBLE);
                            updateUI();
                            Toast.makeText(LoginActivity.this, "Logged In!", Toast.LENGTH_SHORT).show();

                        } else {
                            showMessage(task.getException().getMessage());
                            loginButton.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Incorrect email or password.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    //simple method that starts the Main Activity
    private void updateUI() {

    startActivity(homeScreen);
    finish();

    }

    @Override
    protected void onStart() {
        super.onStart();

        //gets the currently authenticated user
        FirebaseUser user = myAuth.getCurrentUser();


        if (user != null) {

            //user is already connected/signed in so we redirect them to the home screen (ListActivity)
            //runs the updatedUI function
            updateUI();

        }
    }


    //simple method that shows toast message
    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
