package com.example.calorietracker.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.helper.StudentWriter;
import com.example.calorietracker.ui.home.HomeActivity;
import com.example.calorietracker.ui.login.LoginActivity;
import com.example.calorietracker.ui.login.local.EditProfile;
import com.github.siyamed.shapeimageview.CircularImageView;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;

public class HomeInfo extends Fragment {


    public static HomeInfo newInstance(String path, String date) {
        File file_path = new File(path);
        JSONReaderFactory factory = new JSONReaderFactory();
        JsReader student_reader;
        String name, gender, frequency, password;
        int age, weight;
        float height;
        Bundle args = new Bundle();
        HomeInfo fragment = new HomeInfo();
        try {
//using JSONREADERFACTORY to generate reader for json file
            student_reader = factory.JSONReaderFactory(file_path);
            Student student = (Student) student_reader.getProduct();

            // create a student using the file info
            frequency = student.getFrequency();
            age = student.getAge();
            gender = student.getGender();
            height = student.getHeight();
            weight = student.getWeight();
            password = student.getPassword();
            name = student.getName();

            //setting the information to the bundle of HomeInfo
            args.putString("path", path);
            args.putString("name", name);
            args.putFloat("height", height);
            args.putString("gender", gender);
            args.putString("frequency", frequency);
            args.putInt("weight", weight);
            args.putInt("age", age);
            args.putString("password", password);
            args.putString("date", date);

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
        LinearLayout btnEdit, btnLogout;
        TextView date, name, bmi, bmr, bmiHint;
        int weightInt, ageInt;
        float heightFloat, bmiFloat;
        String dateStr, nameStr, frequencyStr, genderStr, bmiStr, password;
        String path = getArguments().getString("path");
        File file_path = new File(path);

        //putting picture into home info page

        CircularImageView imageView;
        imageView = view.findViewById(R.id.imageView);
        Uri uri = Uri.parse("android.resource://com.example.calorietracker/drawable/user_icon");
        Glide.with(this).load(String.valueOf(uri)).into(imageView);

        //rendering HomeInfo page
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
        name.setText("Welcome, " + nameStr);
        password = getArguments().getString("password");
        Student student = new Student(nameStr, genderStr, ageInt, frequencyStr, heightFloat, weightInt, password);
        bmiFloat = student.getBMI();
        bmiStr = String.valueOf(bmiFloat);
        bmi.setText("Current BMI " + bmiStr);
        bmiHint.setText(student.getBmiString());
        dateStr = getArguments().getString("date");
        date.setText("Today, " + dateStr);
        int bmrInt = (int) student.getBMR();
        bmr.setText("Your Suggeested daily intake is " + bmrInt + " cals");

        //btnEdit is the Edit profile button. If you click on it, would start a new Activity called EditProfile

        btnEdit = view.findViewById(R.id.edit_button);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EditProfile.class);
                intent.putExtra("path", getArguments().getString("path"));
                startActivity(intent);
            }
        });

        //btnLogout is for user to log out. would pop up a warning asking whether the user wants to log out
        btnLogout = view.findViewById(R.id.log_out_button);
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

        //if you click on the circular image, would pop up a dialog showing your personal information
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.fragment_student_info);

                //setting the dialog
                CircularImageView genderView = dialog.findViewById(R.id.image_gender);
                TextView nameView, ageView, heightView, weightView, frequencyView;
                nameView = dialog.findViewById(R.id.student_name);
                ageView = dialog.findViewById(R.id.student_age);
                heightView = dialog.findViewById(R.id.student_height);
                weightView = dialog.findViewById(R.id.student_weight);
                frequencyView = dialog.findViewById(R.id.student_frequency);
                Button clearTodayRecord = dialog.findViewById(R.id.clear_today_record);
                Button clearAllRecord = dialog.findViewById(R.id.clear_all_record);

                nameView.setText(nameStr);
                ageView.setText(String.valueOf(ageInt) + " yrs old");
                heightView.setText(String.valueOf(heightFloat) + " m");
                weightView.setText(weightInt + " kg");
                frequencyView.setText(frequencyStr);

                if (student.getGender().equals("Female")) {
                    Uri uri = Uri.parse("android.resource://com.example.calorietracker/drawable/icon_female");
                    Glide.with(getContext()).load(String.valueOf(uri)).into(genderView);
                } else {
                    Uri uri = Uri.parse("android.resource://com.example.calorietracker/drawable/icon_male");
                    Glide.with(getContext()).load(String.valueOf(uri)).into(genderView);

                }

                //this one is to clear today's record. If you added some food for today and want to reset, just click it

                clearTodayRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StudentWriter writer = new StudentWriter(file_path);
                        try {
                            writer.deleteReportByDate(getArguments().getString("date"));
                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            String[] splits =  path.split("/");
                            int len = splits.length;
                            String new_path = "/"+splits[len-1];
                            intent.putExtra("path",new_path);
                            intent.putExtra("date",getArguments().getString("date"));
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

                //clears all records for this user. be careful with this!!!!
                clearAllRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StudentWriter writer = new StudentWriter(file_path);
                        try {
                            writer.deleteAllRecord();
                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            String[] splits =  path.split("/");
                            int len = splits.length;
                            String new_path = "/"+splits[len-1];
                            intent.putExtra("path",new_path);
                            intent.putExtra("date",getArguments().getString("date"));
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }

        });

        return view;
    }
}


