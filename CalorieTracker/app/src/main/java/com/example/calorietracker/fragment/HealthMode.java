package com.example.calorietracker.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.menu.FoodModel;
import org.json.JSONException;

import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.helper.MenuReader;
import com.example.calorietracker.ui.login.LoginActivity;
import com.example.calorietracker.ui.login.local.EditProfile;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HealthMode extends Fragment {
    //this one determines how many type of food to be returned
    private int numberOfFood;


    private Float sum;
    //TODO: please change limit, num, sum to integer
    private Float limit;
    private Float num;
    private MenuReader mReader;
    private static int[] mealBmrs;
    private static int breakfast_calorie, lunch_calorie, dinner_calorie;

    public static HealthMode newInstance(String path, String date) {

        Bundle args = new Bundle();

        HealthMode fragment = new HealthMode();
        args.putString("date",date);
        args.putString("path",path);
        File student_file = new File(path);

        JSONReaderFactory factory = new JSONReaderFactory();
        JsReader reader;
        try{
            reader = factory.JSONReaderFactory(student_file);
            Student student = (Student) reader.getProduct();
            mealBmrs= student.getBMRPropotion();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        breakfast_calorie = mealBmrs[0];
        lunch_calorie = mealBmrs[1];
        dinner_calorie = mealBmrs[2];
        args.putInt("breakfast", breakfast_calorie);
        args.putInt("lunch", lunch_calorie);
        args.putInt("dinner", dinner_calorie);
        fragment.setArguments(args);
        return fragment;

    }

    public Boolean isFull(Float limit){
        if (this.sum < limit){
            return true;
        }else{
            return false;
        }
    }

    public String[] calorieList(MenuReader mReader){


        ArrayList<FoodModel> menu = mReader.getFoodListObj();
        String[] list = new String[256];
        for (int i = 0; i < menu.size(); i++){
            list[i] = menu.get(i).getCalorie();
        }

        return list;

    }

    public Boolean isNull(Float average) {
        float calory = 0;
        Boolean tmp = true;
        try{
            InputStream in = getContext().getAssets().open("menu3.JSON");
            mReader = new MenuReader(in);
        }
        catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] list = this.calorieList(this.mReader);

        labelA:
        for (int i = 0; i < list.length; i++) {
            calory = Float.parseFloat(list[i]);
            if (calory > average) {
                tmp = true;
            } else {
                tmp = false;
                break labelA;
            }
        }

        return tmp;

    }

    public String pickFood(float limit, float num){
        String list = "";

        float average = (limit / num);

        ArrayList<FoodModel> menu = mReader.getFoodListObj();

        labelB:
        while(this.isFull(limit)){
            if (this.isNull(average)){
                ArrayList<FoodModel> menu1 = new ArrayList<>();
                for (int i = 0; i < menu.size(); i++) {
                    if (average > Float.parseFloat(menu.get(i).getCalorie())) {
                        menu1.add(menu.get(i));
                    }
                }

                FoodModel food = menu1.get((int)Math.random()*menu.size());
                list = list + " + " + food.getName();
                sum = sum + Float.parseFloat(food.getCalorie());
                num = num - 1;
                average = ((limit-sum) / num);
            }else{
                break labelB;
            }
        }
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_mode, null);
        RadioButton item_one, item_two, item_three, item_four, item_five, breakfastButton, lunchButton, dinnerButton;
        Button submitButton, generateButton;
        ListView foodListView;
        //foodListView = view.findViewById(R.id.list_health_menu);
        item_one = view.findViewById(R.id.item_one);
        item_two = view.findViewById(R.id.item_two);
        item_three = view.findViewById(R.id.item_three);
        item_four = view.findViewById(R.id.item_four);
        item_five = view.findViewById(R.id.item_five);
        breakfastButton = view.findViewById(R.id.choose_breakfast);
        lunchButton = view.findViewById(R.id.choose_lunch);
        dinnerButton = view.findViewById(R.id.choose_dinner);
        submitButton = view.findViewById(R.id.button_health_submit);
        generateButton = view.findViewById(R.id.button_change_menu);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: PUT STORE THE REPORT INTO STDUENT FILE
                //example to follow. to add a breakfast report
               // Report report = new Report(getArguments().getString("date"));

                //File student_file = new File(getArguments().getString("path");
                //StudentWriter writer = new StudentWriter(student_file);
                //if(breakfastButton.isChecked()){report.setBreakfast(sum);}
                //if(lunchButton.isChecked()){report.setLunch(sum);}
                //if(dinnerButton.isChecked()){report.setDinner(sum);}
                //writer.addReport(report);


            }
        });

        generateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(item_one.isChecked()){numberOfFood =1;}
                if(item_two.isChecked()){numberOfFood =2;}
                if(item_three.isChecked()){numberOfFood =3;}
                if(item_four.isChecked()){numberOfFood =4;}
                if(item_five.isChecked()){numberOfFood =5;}
                //TODO: change limint to interger pls!
                //if(breakfastButton.isChecked()){limit = breakfast_calorie;}
                //if(lunchButton.isChecked()){limit = lunch_calorie;}
                //if(dinnerButton.isChecked()){limit = dinner_calorie;}
            }
        });


        return view;

    }


}
