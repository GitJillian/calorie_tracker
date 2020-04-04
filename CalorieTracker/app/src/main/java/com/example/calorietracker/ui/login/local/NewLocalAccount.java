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
import com.example.calorietracker.ui.home.HomeActivity;

import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;

public class NewLocalAccount extends AppCompatActivity {

    //user should be able to register for a new account through the settings

    EditText usernameEditText, passwordEditText, heightEditText, weightEditText, ageEditText;
    RadioButton gender_female, gender_male, frequency_rarely, frequency_sometimes, frequency_medium, frequency_often, frequency_always;
    Button submit_button;
    Boolean gender = true;
    TextView view1, view2;
    String frequency;


    @SuppressLint("WrongViewCast")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view1 = findViewById(R.id.text_view_gender);
        view2 = findViewById(R.id.text_view_frequency);
        setContentView(R.layout.activity_create_user);
        setTitle("Create Local User");
        //setting title of the activity
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
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender_female.isChecked()) {
                    gender = true;
                } else if (gender_male.isChecked()) {
                    gender = false;
                }
                if (!gender_female.isChecked() && !gender_male.isChecked()) {
                    Toast.makeText(NewLocalAccount.this, "Please choose gender", Toast.LENGTH_LONG).show();
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

                if(passwordEditText.getText().toString().length()<5){
                    Toast.makeText(NewLocalAccount.this,"Password Length should be more than 5", Toast.LENGTH_LONG).show();
                }
                else if(helper.userExists(NewLocalAccount.this, usernameEditText.getText().toString())){
                    Toast.makeText(NewLocalAccount.this, "This user name exists. Maybe try another?", Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        checkInput(heightEditText, weightEditText, usernameEditText, gender, ageEditText, frequency, passwordEditText);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                     }
                }

            }
        });
    }

//check user input is valid

    public boolean checkInput(EditText heightEditText, EditText weightEditText, EditText nameEditText, boolean gender,
                              EditText ageEditText, String frequency, EditText passwordEditText) throws IOException {
        String name, password,sex;
        int age, weight;
        float height;
        boolean flag = false;
        //create a JSON file for the student using the name, should be  <absolute path>+"/studentname.json"

        File file_file = new File(FileHelper.getFileDir(NewLocalAccount.this) + "/" + transName(nameEditText.getText().toString()) + ".JSON");
        Log.d("file_path", FileHelper.getFileDir(NewLocalAccount.this) + "/" + transName(nameEditText.getText().toString()) + ".JSON");
        StudentWriter student_writer = new StudentWriter(file_file);
        try {
            age = Integer.parseInt(ageEditText.getText().toString());
            name = nameEditText.getText().toString();
            password = passwordEditText.getText().toString();
            height = Float.parseFloat(heightEditText.getText().toString());
            weight = Integer.parseInt(weightEditText.getText().toString());
            if (frequency == "") {
                frequency = "medium";
            }
            flag = true;
            Intent intent = new Intent(NewLocalAccount.this, HomeActivity.class);


            if (gender == true) {
                sex = "Female";
            } else {
                sex = "Male";
            }


            Toast.makeText(NewLocalAccount.this, "Switching to Home Page", Toast.LENGTH_LONG).show();
            FileOutputStream is = new FileOutputStream(file_file);
            ArrayList<Report> report = new ArrayList<>();


            Student student = new Student(name, sex, age, frequency, height, weight, password);
            student_writer.writeStudent(student, report);
            intent.putExtra("path","/" + transName(nameEditText.getText().toString()) + ".JSON");
            startActivity(intent);
            //if the input is valid, switch to home page using startActivity()
        }
    catch(FileNotFoundException e){
                flag = false;
                e.printStackTrace();
            }

        return flag;
        }
    private String transName(String name){
        String str = name.replace(' ','_');
        return str;
    }

    }

