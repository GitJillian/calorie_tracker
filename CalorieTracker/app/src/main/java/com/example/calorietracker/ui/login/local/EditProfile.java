package com.example.calorietracker.ui.login.local;
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
import com.example.calorietracker.helper.StudentReader;
import com.example.calorietracker.ui.home.HomeActivity;
import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;

public class EditProfile extends AppCompatActivity{
    EditText usernameEditText, passwordEditText, heightEditText, weightEditText, ageEditText;
    RadioButton gender_female, gender_male, frequency_rarely, frequency_sometimes, frequency_medium, frequency_often, frequency_always;
    Button submit_button;
    Boolean gender = true;
    TextView view1, view2;
    String frequency;
    int weight, age;
    float height;
    String name, password;
    StudentReader student_reader;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        setTitle("Edit Local User");
        view1 = findViewById(R.id.text_view_gender);
        view2 = findViewById(R.id.text_view_frequency);
        usernameEditText = findViewById(R.id.new_user_name);
        passwordEditText = findViewById(R.id.new_user_password);
        heightEditText = findViewById(R.id.new_height);
        weightEditText = findViewById(R.id.new_weight);
        ageEditText = findViewById(R.id.new_age);
        gender_female = findViewById(R.id.radio_female);
        gender_male = findViewById(R.id.radio_male);
        frequency_rarely = findViewById(R.id.frequency_rarely);
        frequency_sometimes = findViewById(R.id.frequency_sometimes);
        frequency_medium = findViewById(R.id.frequency_medium);
        frequency_often = findViewById(R.id.frequency_often);
        frequency_always = findViewById(R.id.frequency_always);
        submit_button = findViewById(R.id.button_submit);
        //set previous information
        Intent intent = getIntent();
        String path = intent.getExtras().getString("path");
        File file = new File(path);
        try {
            student_reader = new StudentReader(file);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        usernameEditText.setText(student_reader.getStudentName());
        passwordEditText.setText(student_reader.getPassword());
        heightEditText.setText(student_reader.getStudentHeight());
        weightEditText.setText(student_reader.getStudentWeight());
        ageEditText.setText(student_reader.getStudentAge());
        //need gender panduan(.
        String stringGender = student_reader.getStudentGender();
        frequency = student_reader.getFrequency();

        if(stringGender.equals("Female")){
            gender_female.setChecked(true);
        }else{
            gender_male.setChecked(true);
        }
        if(frequency.equals("rarely")){}



        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
