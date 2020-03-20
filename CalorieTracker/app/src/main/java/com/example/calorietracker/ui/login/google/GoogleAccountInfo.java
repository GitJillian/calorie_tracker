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
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.helper.StudentReader;
import com.example.calorietracker.ui.home.HomeActivity;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import org.json.JSONException;

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
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();

            String file_email = personEmail.split("@")[0];

            setContentView(R.layout.activity_person_google);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            imageView = findViewById(R.id.imageView);
            name = findViewById(R.id.textName);
            email = findViewById(R.id.textEmail);
            launch_home = findViewById(R.id.button_launch);


            launch_home.setOnClickListener(v -> {
                switch (v.getId()){

                    case R.id.button_launch:

                        String student_path = FileHelper.getFileDir(GoogleAccountInfo.this) + "/" + transName(file_email) + ".JSON";
                        Intent intent = new Intent(GoogleAccountInfo.this, HomeActivity.class);
                        intent.putExtra("path","/" + transName(file_email) + ".JSON");
                        startActivity(intent);
                      //  File file_path = new File(FileHelper.getFileDir(GoogleAccountInfo.this) + "/" + transName(file_email) + ".JSON");
                        /*JSONReaderFactory factory = new JSONReaderFactory();

                        try {
                            student_reader = factory.JSONReaderFactory(file_path);
                            Student student = (Student)student_reader.getProduct();
                            student_frequency = student.getFrequency();
                            student_age = student.getAge();
                            student_gender = student.getGender();
                            student_height = student.getHeight();
                            student_weight = student.getWeight();
                            student_name = student.getName();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(GoogleAccountInfo.this, HomeActivity.class);
                        intent.putExtra("frequency", student_frequency);
                        intent.putExtra("name", student_name);
                        intent.putExtra("gender",student_gender);
                        intent.putExtra("height", student_height);
                        intent.putExtra("weight",student_weight);
                        intent.putExtra("age",student_age);
                        startActivity(intent);
                        Toast.makeText(GoogleAccountInfo.this,"Switching to Home Page", Toast.LENGTH_LONG).show();
                        */
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


            if (acct != null) {
                Uri personPhoto = acct.getPhotoUrl();
                name.setText("Welcome,"+personName);
                email.setText("Logged in using:"+personEmail);
                Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
            }
        }

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


