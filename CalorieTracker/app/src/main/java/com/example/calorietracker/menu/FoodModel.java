package com.example.calorietracker.menu;

/**
 * Created by Deependra Singh Patel on 24/4/19.
 */
public class FoodModel {
    public String type;
    public String sodium;
    public String servingSize;
    public String sugar;
    public String protein;
    public String totalCarbon;
    public String foodName;
    public String calorie;
    public String fats;
    public int imagePath;

    public FoodModel(String foodName, String calorie, String fats, int imagePath) {
        this.foodName = foodName;
        this.calorie = calorie;
        this.fats= fats;
        this.imagePath = imagePath;
    }

    public FoodModel(String name, String calorie, String type, String sodium, String servingSize, String sugar, String fats, String protein, String totalCarbon) {
        this.foodName = name;
        this.calorie = calorie;
        this.sodium = sodium;
        this.type = type;
        this.servingSize = servingSize;
        this.sugar = sugar;
        this.fats = fats;
        this.protein = protein;
        this.totalCarbon = totalCarbon;
    }

    public String getName() {
        return this.foodName;
    }
    public String getFats(){
        return this.fats;
    }
    public void setName(String foodName) {
        this.foodName = foodName;
    }
    public String getCalorie() {
        return calorie;
    }
    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public int getImagePath() {
        return imagePath;
    }
    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}
