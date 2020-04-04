package com.example.calorietracker.helper;

import android.content.Context;

import com.example.calorietracker.menu.FoodModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import com.example.calorietracker.R;

public class MenuReader extends JsReader {
    private InputStream is;
    private BufferedReader bufr;
    private StringBuilder builder;
    private ArrayList<FoodModel> foodObjs;
    private JSONArray foodlistJson;
    private JSONObject root;
    private File file_path;

    //open file
    public MenuReader(File file) throws JSONException {
        super(file);
        root = super.getObject();
        foodlistJson = super.getJsonArrayByName("menu");
    }
// open file input stream
    public MenuReader(FileInputStream input) throws JSONException {
       super(input);
       root = super.getObject();
       foodlistJson = super.getJsonArrayByName("menu");
    }
//open input stream
    public MenuReader(InputStream input) throws JSONException{
        super(input);
        root = super.getObject();
        foodlistJson = super.getJsonArrayByName("menu");
    }

    //return food list as json objects
    public JSONArray getFoodListJson(){
        return foodlistJson;
    }

    public ArrayList<FoodModel> getFoodList(){
        return foodObjs;
    }

    public JSONObject getMenu(){
        return root;
    }

    //get each food using index

    public JSONObject getFoodByIndexJson(int index){
        JSONObject obj = super.getObjectByIndex(foodlistJson, index);
        return obj;
    }

    //getting food status functions

    public String getFoodName(JSONObject obj){
        return super.getStringByName(obj, "name");
    }

    public String getCalorie(JSONObject obj){
        return super.getStringByName(obj, "calory");
    }

    public String getFats(JSONObject obj){
        return super.getStringByName(obj,"totalFat");
    }

    public String getCarbohydrate(JSONObject obj){
        return super.getStringByName(obj, "totalCarbohydrate");
    }

    public String getSodium(JSONObject obj){
        return super.getStringByName(obj, "sodium");
    }

    public String getProtein(JSONObject obj){
        return super.getStringByName(obj, "protein");
    }

    public String getServingSize(JSONObject obj){
        return super.getStringByName(obj, "servingSize");
    }

    public String getType(JSONObject obj){
        return super.getStringByName(obj, "type");
    }

    public String getSugar(JSONObject obj){
        return super.getStringByName(obj,"sugar");
    }


//transform JSON object to FoodModel Object
    public FoodModel transJsonToFood(JSONObject obj){
        String name, calorie, type, sodium, servingSize, sugar, fats,protein, totalCarbon;
        name = getFoodName(obj);
        calorie = getCalorie(obj);
        sodium = getSodium(obj);
        type = getType(obj);
        servingSize = getServingSize(obj);
        sugar = getSugar(obj);
        protein = getProtein(obj);
        totalCarbon = getCarbohydrate(obj);
        fats = getFats(obj);
        FoodModel food = new FoodModel(name, calorie, type, sodium, servingSize, sugar, fats, protein, totalCarbon,R.drawable.burger);
        return food;
    }

//getting all food items from database
    @Override
    public ArrayList<FoodModel> getProduct(){
        for(int i=0; i<foodlistJson.length(); i++){
            JSONObject obj = getFoodByIndexJson(i);
            FoodModel food = transJsonToFood(obj);
            foodObjs.add(food);
        }
        return foodObjs;
    }

    public ArrayList<FoodModel> getFoodListObj() {
        ArrayList<FoodModel> models = new ArrayList<>();

        for(int i=0; i<foodlistJson.length(); i++){
            JSONObject obj = getFoodByIndexJson(i);
            FoodModel food = transJsonToFood(obj);
            models.add(food);
        }
        return models;
    }
}
