package com.example.calorietracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Student;
import com.google.android.material.snackbar.Snackbar;

public class HomeInfo extends Fragment {

    public static HomeInfo newInstance(String name, String gender, String frequency, int weight, float height, int age, String password){
        Bundle args = new Bundle();
        HomeInfo fragment = new HomeInfo();
        args.putString("name", name);
        args.putFloat("height", height);
        args.putString("gender", gender);
        args.putString("frequency", frequency);
        args.putInt("weight", weight);
        args.putInt("age", age);
        args.putString("password",password);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_info, null);
        TextView name, gender, age, height, weight, frequency, bmi, bmr;
        name = view.findViewById(R.id.home_name);
        gender = view.findViewById(R.id.home_gender);
        age =  view.findViewById(R.id.home_age);
        height = view.findViewById(R.id.home_height);
        weight =view.findViewById(R.id.home_weight);
        frequency =  view.findViewById(R.id.home_frequency);
        bmi = view.findViewById(R.id.home_bmi);
        bmr =  view.findViewById(R.id.home_bmr);
        String nameStr, ageStr, heightStr,weightStr,frequencyStr, genderStr, bmiStr, bmrStr, password;
        int weightInt, ageInt;
        float heightFloat, bmiFloat, bmrFloat;
        weightInt = getArguments().getInt("weight");
        weightStr = String.valueOf(weightInt);
        heightFloat = getArguments().getFloat("height");
        heightStr = String.valueOf(heightFloat);
        ageInt = getArguments().getInt("age");
        ageStr = String.valueOf(ageInt);
        age.setText("age"+ageStr);
        frequencyStr = getArguments().getString("frequency");
        frequency.setText(frequencyStr);
        weight.setText(weightStr);
        height.setText(heightStr);
        genderStr = getArguments().getString("gender");
        gender.setText(genderStr);
        nameStr = getArguments().getString("name");
        name.setText(nameStr);
        password = getArguments().getString("password");
        Student student = new Student(nameStr, genderStr,ageInt,frequencyStr, heightFloat, weightInt,password);
        bmiFloat = student.getBMI();
        bmrFloat = student.getBMR();
        bmiStr = String.valueOf(bmiFloat);
        bmrStr = String.valueOf(bmrFloat);
        bmi.setText(bmiStr);
        bmr.setText(bmrStr);

/*        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Don't click me.please!.", Snackbar.LENGTH_SHORT).show();
            }

        });
*/
        return view;

    }

}

