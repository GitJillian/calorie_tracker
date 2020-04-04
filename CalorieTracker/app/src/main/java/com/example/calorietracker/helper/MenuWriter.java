package com.example.calorietracker.helper;

import android.content.Context;

import com.example.calorietracker.menu.FoodImage;
import com.example.calorietracker.menu.FoodModel;
import com.example.calorietracker.ui.login.local.SetWeeklyMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;


public class MenuWriter extends JsWriter {

    //this class is for writing the food items into menu database, which is used by admin
    //not that relevant right now. can be deleted

    //setting file output stream path
    public MenuWriter(File file_out){
        super(file_out);
    }

    //from write FoodImage lists into JSON file

    public void writeFoodImageArray(Context context, ArrayList<FoodImage> FoodArray){
        ArrayList<String> names = new ArrayList<>();
        ArrayList<FoodModel> modelArrayList = new ArrayList<>();
        for(FoodImage image:FoodArray){
            names.add(image.getFoodName());
        }

        try{
            InputStream in = context.getAssets().open("menu3.JSON");
            MenuReader menuReader = new MenuReader(in);
            ArrayList<FoodModel> origin_models = menuReader.getFoodList();
            for(FoodModel model:origin_models){
                for(String name:names){
                    if(name.equals(model.getName())){
                        modelArrayList.add(model);

                    }
                }
            }
            writeFoodArray(modelArrayList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


// transform each food model into JSON objects
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

    //write foodmodels into database
    public void writeFoodArray(ArrayList<FoodModel> foodList) {

        JSONObject sample = new JSONObject();

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

