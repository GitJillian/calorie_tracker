package com.example.calorietracker.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
        //ImageButton btnEdit, btnLogout;
        LinearLayout btnEdit, btnLogout;
        TextView date, name, bmi, bmr,bmiHint;
        int weightInt, ageInt;
        float heightFloat, bmiFloat;
        String dateStr,nameStr, frequencyStr, genderStr, bmiStr,  password;
        CircularImageView imageView;
        imageView = view.findViewById(R.id.imageView);
        Uri uri = Uri.parse("android.resource://com.example.calorietracker/drawable/user_icon");
        Glide.with(this).load(String.valueOf(uri)).into(imageView);
        date = view.findViewById(R.id.home_date);
        name = view.findViewById(R.id.home_name);
        bmi = view.findViewById(R.id.home_bmi);
        bmiHint = view.findViewById(R.id.home_bmi_hint);
        bmr = view.findViewById(R.id.bmr);
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
        int bmrInt = (int)student.getBMR();
        bmr.setText("Your Suggeested daily intake is "+bmrInt+" cals");
        btnEdit = view.findViewById(R.id.edit_button);
        btnLogout =view.findViewById(R.id.log_out_button);
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
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_logout);
                Button okButton = dialog.findViewById(R.id.ok_button);
                Button cancelButton = dialog.findViewById(R.id.cancel_button);
                dialog.show();
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        dialog.dismiss();

                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        return view;

    }

}

