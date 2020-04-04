package com.example.calorietracker.ui.login.google;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.helper.StudentWriter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


import java.io.*;
import java.util.ArrayList;

public class NewGoogleAccount extends AppCompatActivity {
    EditText heightEditText, weightEditText, ageEditText, nameEditText;
    RadioButton gender_female,gender_male, frequency_rarely, frequency_sometimes, frequency_medium, frequency_often, frequency_always;
    Button submit_button;
    Boolean gender = true;
    TextView view1, view2;
    String frequency;
    int weight, age;
    float height;
    String name,password;

    @SuppressLint("WrongViewCast")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Intent intent = getIntent();

        //if a student create an account using Gmail, the new path for the JSON file should be "emailname.JSON"(without @gmail.com)
        String file_email = intent.getStringExtra("path");

        setContentView(R.layout.activity_create_user_google);
        setTitle("Create Google User");
        view1 = findViewById(R.id.text_view_gender);
        view2 = findViewById(R.id.text_view_frequency);
        heightEditText = findViewById(R.id.new_height);
        weightEditText = findViewById(R.id.new_weight);
        ageEditText = findViewById(R.id.new_age);
        nameEditText = findViewById(R.id.new_user_name);
        gender_female = findViewById(R.id.radio_female);
        gender_male = findViewById(R.id.radio_male);
        frequency_rarely = findViewById(R.id.frequency_rarely);
        frequency_sometimes = findViewById(R.id.frequency_sometimes);
        frequency_medium = findViewById(R.id.frequency_medium);
        frequency_often = findViewById(R.id.frequency_often);
        frequency_always = findViewById(R.id.frequency_always);
        submit_button = findViewById(R.id.button_submit);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender_female.isChecked()) {
                    gender = true;
                } else if (gender_male.isChecked()) {
                    gender = false;
                }
                if (!gender_female.isChecked() && !gender_male.isChecked()) {
                    Toast.makeText(NewGoogleAccount.this, "Please choose gender", Toast.LENGTH_LONG).show();
                }
                if (frequency_rarely.isChecked()) {
                    frequency = "rarely";
                } else if (frequency_sometimes.isChecked()) {
                    frequency = "sometimes";
                } else if (frequency_medium.isChecked()) {
                    frequency = "medium";
                } else if (frequency_often.isChecked()) {
                    frequency = "often";
                } else {
                    frequency = "always";
                }
                if (frequency == "") {
                    frequency = "medium";
                }
                checkInput(heightEditText, weightEditText, nameEditText,gender, ageEditText, frequency, file_email);

            }
        });
    }



public void checkInput (EditText heightEditText, EditText weightEditText, EditText nameEditText, boolean gender,
                        EditText ageEditText, String frequency, String file_email){

            String  name,password,sex;
            int age, weight;
            float height;

            //create a new file for the user
            File file_file = new File(FileHelper.getFileDir(NewGoogleAccount.this) + file_email);

            StudentWriter student_writer = new StudentWriter(file_file);
            try{
                name = nameEditText.getText().toString();
                age = Integer.parseInt(ageEditText.getText().toString());
                password="";
                height = Float.parseFloat(heightEditText.getText().toString());
                weight = Integer.parseInt(weightEditText.getText().toString());
                 if(frequency == ""){
                    frequency = "medium";
                   }


                Intent intent = new Intent(NewGoogleAccount.this, GoogleAccountInfo.class);

                if(gender == true){sex = "Female";}
                else{sex = "Male";}


                Toast.makeText(NewGoogleAccount.this, "Switching to Home Page", Toast.LENGTH_LONG).show();
                ArrayList<Report> report = new ArrayList<Report>();
                Student student = new Student(name, sex, age, frequency, height, weight, password);
                student_writer.writeStudent(student, report);

                intent.putExtra("path",file_email);
                startActivity(intent);
                //switch to next activity if the user input is valid and written to database
                }
            catch(Exception e){
                Toast.makeText(NewGoogleAccount.this, "Error input!", Toast.LENGTH_LONG).show();
            }
}

    private String transName(String name){
        String str = name.replace(' ','_');
        return str;
    }

        }

