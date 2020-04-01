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
    EditText heightEditText, weightEditText, ageEditText;
    RadioButton gender_female, gender_male, frequency_rarely, frequency_sometimes, frequency_medium, frequency_often, frequency_always;
    Button submit_button;
    Boolean gender = true;
    TextView view1, view2;
    String frequency;
    int weight, age;
    float height;
    String name, password;
    StudentReader student_reader;
    String path;
    String oldName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");
        view1 = findViewById(R.id.text_view_gender);
        view2 = findViewById(R.id.text_view_frequency);

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
        path = intent.getExtras().getString("path");
        File file = new File(path);
        try {
            student_reader = new StudentReader(file);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        oldName = student_reader.getStudentName();
        password = student_reader.getPassword();

        heightEditText.setText(student_reader.getStudentHeight());
        weightEditText.setText(student_reader.getStudentWeight());
        ageEditText.setText(student_reader.getStudentAge());
        //need gender panduan(.
        String stringGender = student_reader.getStudentGender();
        frequency = student_reader.getFrequency();

        //get previous information
        if(stringGender.equals("Female")){
            gender_female.setChecked(true);
        }else{
            gender_male.setChecked(true);
        }
        if(frequency.equals("rarely")){
            frequency_rarely.setChecked(true);
        }
        else if(frequency.equals("sometimes")){
            frequency_sometimes.setChecked(true);
        }
        else if(frequency.equals("medium")){
            frequency_medium.setChecked(true);
        }
        else if(frequency.equals("often")){
            frequency_often.setChecked(true);
        }
        else{
            frequency_always.setChecked(true);
        }

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender_female.isChecked()) {
                    gender = true;
                } else if (gender_male.isChecked()) {
                    gender = false;
                }
                if (!gender_female.isChecked() && !gender_male.isChecked()) {
                    Toast.makeText(EditProfile.this, "Please choose gender", Toast.LENGTH_LONG).show();
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
                FileHelper helper = new FileHelper();
                try {
                    checkInput(heightEditText, weightEditText, gender, ageEditText, frequency);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public boolean checkInput(EditText heightEditText, EditText weightEditText, boolean gender,
                              EditText ageEditText, String frequency) throws IOException {
        String sex;
        int age, weight;
        float height;
        boolean flag = false;
        File file_file = new File(path);

        StudentWriter student_writer = new StudentWriter(file_file);
        try {
            age = Integer.parseInt(ageEditText.getText().toString());
            height = Float.parseFloat(heightEditText.getText().toString());
            weight = Integer.parseInt(weightEditText.getText().toString());
            if (frequency == "") {
                frequency = "medium";
            }
            flag = true;
            Intent intent = new Intent(EditProfile.this, HomeActivity.class);


            if (gender==true) {
                sex = "Female";
            } else {
                sex = "Male";
            }

            File file = new File(path);
            try {
                student_reader = new StudentReader(file);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(EditProfile.this, "Switching to Home Page", Toast.LENGTH_LONG).show();
            FileOutputStream is = new FileOutputStream(file_file);

            ArrayList<Report> report = student_reader.getArray();
            Student student = new Student(oldName, sex, age, frequency, height, weight, password);

            student_writer.writeStudent(student,report);

            //let

            String[] splits =  path.split("/");
            int len = splits.length;
            String new_path = "/"+splits[len-1];
            intent.putExtra("path",new_path);

            startActivity(intent);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return flag;
    }
}

