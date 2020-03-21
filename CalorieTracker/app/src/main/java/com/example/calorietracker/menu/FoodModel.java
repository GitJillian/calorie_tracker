package com.example.calorietracker.menu;


public class FoodModel {
    private String type;
    private String sodium;
    private String servingSize;
    private String sugar;
    private String protein;
    private String totalCarbon;
    private String foodName;
    private String calorie;
    private String fats;
    private int imagePath;
    private boolean isSelected;

    public FoodModel(String foodName, String calorie, String fats, int imagePath) {
        this.foodName = foodName;
        this.calorie = calorie;
        this.fats= fats;
        this.imagePath = imagePath;
    }

    public FoodModel(String name, String calorie, String type, String sodium, String servingSize, String sugar, String fats, String protein, String totalCarbon,int imagePath) {
        this.foodName = name;
        this.calorie = calorie;
        this.sodium = sodium;
        this.type = type;
        this.servingSize = servingSize;
        this.sugar = sugar;
        this.fats = fats;
        this.protein = protein;
        this.totalCarbon = totalCarbon;
        this.imagePath = imagePath;
    }
    public void setSelected(boolean selected){this.isSelected = selected;}
    public boolean getSelected(){return this.isSelected;}
    public void setName(String foodName) {
        this.foodName = foodName;
    }
    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getName() {
        return this.foodName;
    }
    public String getFats(){
        return this.fats;
    }
    public String getType(){return this.type;}
    public String getSodium(){return this.sodium;}
    public String getCalorie() {
        return calorie;
    }
    public String getServingSize(){return this.servingSize;}
    public String getSugar(){return this.sugar;}
    public String getProtein(){return this.protein;}
    public String getTotalCarbon(){return this.totalCarbon;}
    public int getImagePath() {
        return imagePath;
    }

}
