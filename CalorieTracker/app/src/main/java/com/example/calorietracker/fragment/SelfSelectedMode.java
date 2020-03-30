package com.example.calorietracker.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.ui.home.SelfSelectView;

import com.github.siyamed.shapeimageview.CircularImageView;

import org.json.JSONException;

import java.io.File;

public class SelfSelectedMode extends Fragment {
    ImageView Breakfast, Lunch, Dinner;
    private CircularImageView chooseMenu;
    public static SelfSelectedMode newInstance(String path,String date) {

        Bundle args = new Bundle();
        SelfSelectedMode fragment = new SelfSelectedMode();
        File file_path = new File(path);
        JSONReaderFactory factory = new JSONReaderFactory();
        JsReader student_reader;
        String name, gender,frequency, password;
        int age, weight;
        float height;
        int breakfastEaten, lunchEaten, dinnerEaten;
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
            breakfastEaten = student_reader.getSum(date)[0];
            lunchEaten = student_reader.getSum(date)[1];
            dinnerEaten = student_reader.getSum(date)[2];
            args.putString("path",path);
            args.putString("name", name);
            args.putFloat("height", height);
            args.putString("gender", gender);
            args.putString("frequency", frequency);
            args.putInt("weight", weight);
            args.putInt("age", age);
            args.putString("password",password);
            args.putString("date",date);
            args.putInt("breakfast",breakfastEaten);
            args.putInt("lunch",lunchEaten);
            args.putInt("dinner",dinnerEaten);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_selected_ui, null);
        TextView eatenBreakfast, eatenLunch, eatenDinner;
        ImageView BreakfastButton, LunchButton, DinnerButton;
        TextView bmrBreakfast,bmrLunch, bmrDinner;
        int weightInt, ageInt;
        float heightFloat;
        int bmr_breakfast,bmr_lunch,bmr_dinner,eaten_breakfast, eaten_lunch, eaten_dinner;
        String nameStr, frequencyStr, genderStr, password;
        CircularImageView imageView;
        imageView = view.findViewById(R.id.imageView);

        Uri uri = Uri.parse("android.resource://com.example.calorietracker/drawable/icon_select_2");
        Glide.with(this).load(String.valueOf(uri)).into(imageView);

        bmrBreakfast = view.findViewById(R.id.home_bmr_breakfast);
        bmrLunch = view.findViewById(R.id.home_bmr_lunch);
        bmrDinner = view.findViewById(R.id.home_bmr_dinner);
        eatenBreakfast = view.findViewById(R.id.eaten_breakfast);
        eatenLunch = view.findViewById(R.id.eaten_lunch);
        eatenDinner = view.findViewById(R.id.eaten_dinner);

        eaten_breakfast = getArguments().getInt("breakfast");
        eaten_lunch =getArguments().getInt("lunch");
        eaten_dinner = getArguments().getInt("dinner");

        eatenBreakfast.setText("You have eaten "+eaten_breakfast+" cals");
        //eatenBreakfast.setTextColor(1);
        eatenLunch.setText("You have eaten "+eaten_lunch+" cals");
        eatenDinner.setText("You have eaten "+eaten_dinner+" cals");

        weightInt = getArguments().getInt("weight");
        heightFloat = getArguments().getFloat("height");
        ageInt = getArguments().getInt("age");
        frequencyStr = getArguments().getString("frequency");
        genderStr = getArguments().getString("gender");
        nameStr = getArguments().getString("name");
        password = getArguments().getString("password");
        Student student = new Student(nameStr, genderStr,ageInt,frequencyStr, heightFloat, weightInt,password);
        int[] bmr_portion = student.getBMRPropotion();
        bmr_breakfast = bmr_portion[0];
        bmr_lunch = bmr_portion[1];
        bmr_dinner = bmr_portion[2];
        bmrBreakfast.setText("Recommended "+String.valueOf(student.getLowerBound(bmr_breakfast))+"~"+String.valueOf(student.getUpperBound(bmr_breakfast))+" cals");
        bmrLunch.setText("Recommended "+String.valueOf(student.getLowerBound(bmr_lunch))+"~"+String.valueOf(student.getUpperBound(bmr_lunch))+" cals");
        bmrDinner.setText("Recommended "+String.valueOf(student.getLowerBound(bmr_dinner))+"~"+String.valueOf(student.getUpperBound(bmr_dinner))+" cals");

        BreakfastButton = view.findViewById(R.id.button_breakfast);
        LunchButton = view.findViewById(R.id.button_lunch);
        DinnerButton = view.findViewById(R.id.button_dinner);
        Intent intent = new Intent(getActivity(), SelfSelectView.class);
        intent.putExtra("date",getArguments().getString("date"));
        intent.putExtra("path",getArguments().getString("path"));

        BreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","breakfast");
                Toast.makeText(getContext(),"Breakfast",Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        LunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","lunch");
                Toast.makeText(getContext(),"Lunch",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        DinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","dinner");
                Toast.makeText(getContext(),"Dinner",Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        return view;

    }


}



