package com.example.calorietracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.calorietracker.R;
import com.example.calorietracker.menu.FoodModel;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.helper.MenuReader;

import java.util.ArrayList;

public class HealthMode {

    private Float sum;
    private Float limit;
    private Float num;
    private MenuReader mReader;

    public static HealthMode newInstance(String info) {

        Bundle args = new Bundle();

        HealthMode fragment = new HealthMode();

        args.putString("info", info);

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
            if (this.isFull(average)){
                ArrayList<FoodModel> menu1 = new ArrayList<FoodModel>();
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


}
