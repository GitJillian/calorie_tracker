package com.example.calorietracker.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.ui.login.LoginActivity;
import com.example.calorietracker.ui.login.local.EditProfile;
import com.github.siyamed.shapeimageview.CircularImageView;

import org.json.JSONException;

import java.io.File;

public class HomeInfo extends Fragment {


    public static HomeInfo newInstance(String path, String date){
        File file_path = new File(path);
        JSONReaderFactory factory = new JSONReaderFactory();
        JsReader student_reader;
        String name, gender,frequency, password;
        int age, weight;
        float height;
        Bundle args = new Bundle();
        HomeInfo fragment = new HomeInfo();
        try {

            student_reader = factory.JSONReaderFactory(file_path);
            Student student = (Student)student_reader.getProduct();

            frequency = student.getFrequency();
            age = student.getAge();
            gender = student.getGender();
            height = student.getHeight();
            weight = student.getWeight();
            password = student.getPassword();
            name = student.getName();
            args.putString("path",path);
            args.putString("name", name);
            args.putFloat("height", height);
            args.putString("gender", gender);
            args.putString("frequency", frequency);
            args.putInt("weight", weight);
            args.putInt("age", age);
            args.putString("password",password);
            args.putString("date",date);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        fragment.setArguments(args);
        return fragment;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_info_new, null);
        //Button btnEdit,btnLogout;
        ImageButton btnEdit, btnLogout;
        TextView date, name, gender, age, height, weight, frequency, bmi, bmr, bmrBreakfast,bmrLunch, bmrDinner, bmiHint;
        int weightInt, ageInt;
        float heightFloat, bmiFloat, bmrFloat;
        int bmr_breakfast,bmr_lunch,bmr_dinner ;
        String dateStr,nameStr, ageStr, heightStr,weightStr,frequencyStr, genderStr, bmiStr, bmrStr, password;
        CircularImageView imageView;
        imageView = view.findViewById(R.id.imageView);
        Uri uri = Uri.parse("android.resource://com.example.calorietracker/drawable/user_icon");
        Glide.with(this).load(String.valueOf(uri)).into(imageView);


        date = view.findViewById(R.id.home_date);
        name = view.findViewById(R.id.home_name);

        bmi = view.findViewById(R.id.home_bmi);
       // bmr =  view.findViewById(R.id.home_bmr);
        bmiHint = view.findViewById(R.id.home_bmi_hint);
        bmrBreakfast = view.findViewById(R.id.home_bmr_breakfast);
        bmrLunch = view.findViewById(R.id.home_bmr_lunch);
        bmrDinner = view.findViewById(R.id.home_bmr_dinner);
        weightInt = getArguments().getInt("weight");
        heightFloat = getArguments().getFloat("height");
        ageInt = getArguments().getInt("age");
        frequencyStr = getArguments().getString("frequency");
        genderStr = getArguments().getString("gender");
        nameStr = getArguments().getString("name");
        name.setText("Welcome, "+nameStr);
        password = getArguments().getString("password");
        Student student = new Student(nameStr, genderStr,ageInt,frequencyStr, heightFloat, weightInt,password);
        bmiFloat = student.getBMI();
        bmiStr = String.valueOf(bmiFloat);
        bmi.setText("Current BMI " + bmiStr);
        bmiHint.setText(student.getBmiString());
        dateStr = getArguments().getString("date");
        date.setText("Today, "+dateStr);
        int[] bmr_portion = student.getBMRPropotion();
        bmr_breakfast = bmr_portion[0];
        bmr_lunch = bmr_portion[1];
        bmr_dinner = bmr_portion[2];
        bmrBreakfast.setText("Recommended "+String.valueOf(student.getLowerBound(bmr_breakfast))+"~"+String.valueOf(student.getUpperBound(bmr_breakfast))+" cals");
        bmrLunch.setText("Recommended "+String.valueOf(student.getLowerBound(bmr_lunch))+"~"+String.valueOf(student.getUpperBound(bmr_lunch))+" cals");
        bmrDinner.setText("Recommended "+String.valueOf(student.getLowerBound(bmr_dinner))+"~"+String.valueOf(student.getUpperBound(bmr_dinner))+" cals");


        btnEdit = view.findViewById(R.id.setting);
        btnLogout =view.findViewById(R.id.log_out);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EditProfile.class);
                intent.putExtra("path",getArguments().getString("path"));
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }

}

