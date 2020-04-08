package com.example.calorietracker.ui.login.google;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.calorietracker.R;
import com.example.calorietracker.ui.home.HomeActivity;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;



@SuppressLint("Registered")
    public class GoogleAccountInfo extends AppCompatActivity {
        CircularImageView imageView;
        TextView name, email;
        Button sign_out;
        Button launch_home;
        GoogleSignInClient mGoogleSignInClient;

        @SuppressLint("ResourceType")
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Intent intent = getIntent();
            String file_name = intent.getStringExtra("path");
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();

            setContentView(R.layout.activity_person_google);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            imageView = findViewById(R.id.imageView);
            name = findViewById(R.id.textName);
            email = findViewById(R.id.textEmail);
            launch_home = findViewById(R.id.button_launch);

            //switch to home activity
            launch_home.setOnClickListener(v -> {
                switch (v.getId()){

                    case R.id.button_launch:
                        Intent intent_new = new Intent(GoogleAccountInfo.this, HomeActivity.class);
                        intent_new.putExtra("path",file_name);
                        startActivity(intent_new);
                }
            });
            sign_out = findViewById(R.id.button_sign_out);
            sign_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.button_sign_out:
                            signOut();
                            break;
                    }
                }
            });

                //renders log in page view
                Uri personPhoto = acct.getPhotoUrl();
                name.setText("Welcome,"+personName);
                email.setText("Logged in using:"+personEmail);
                //shows google icon
                Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);

        }

        // signout option for google account
        private void signOut() {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(GoogleAccountInfo.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }

        private String transName(String name){
            String str = name.replace(' ','_');
            return str;
        }
    }


