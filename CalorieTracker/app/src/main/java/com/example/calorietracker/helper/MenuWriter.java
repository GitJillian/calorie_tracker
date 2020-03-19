package com.example.calorietracker.helper;

import com.example.calorietracker.menu.FoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;


public class MenuWriter extends JsWriter {

    public MenuWriter(File file_out){
        super(file_out);
    }



    public JSONObject menuToJson(FoodModel model){
        JSONObject single_menu = new JSONObject();
        try{
            single_menu.put("totalCarbohydrate",String.valueOf(model.getTotalCarbon()));
            single_menu.put("protein",String.valueOf(model.getProtein()));
            single_menu.put("calorie",String.valueOf(model.getCalorie()));
            single_menu.put("sugar",String.valueOf(model.getSugar()));
            single_menu.put("sodium",String.valueOf(model.getSodium()));
            single_menu.put("servingSize",String.valueOf(model.getServingSize()));
            single_menu.put("name",String.valueOf(model.getName()));
            single_menu.put("fats",String.valueOf(model.getFats()));
            single_menu.put("type",String.valueOf(model.getType()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return single_menu;
    }

    //initialize new student
    public void writeFoodArray(ArrayList<FoodModel> foodList) {

        JSONObject sample = new JSONObject();
        FileWriter writer =null;

        try{
            JSONArray foods = new JSONArray();

            for (FoodModel food : foodList) {
                JSONObject single_report = menuToJson(food);
                foods.put(single_report);
            }

            sample.put("menu",foods);//putting food array into jsonfile
            super.writeFile(sample);

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}

