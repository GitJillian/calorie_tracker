package com.example.calorietracker.menu;


public class FoodImage {

    private Integer id;
    private String FoodName;
    private Integer FoodImage;
    int totalCalorie,FoodQuantity;
    String FoodCalorie;

    public FoodImage() {
    }

    public void setFoodName(String name){this.FoodName = name;}
    public void setFoodCalorie(String FoodCalorie){this.FoodCalorie = FoodCalorie;}

    public String getFoodCalorie() {
        return FoodCalorie;
    }

    public int getTotalCalorie() {
        return totalCalorie;
    }

    public void setTotalCalorie(int totalCalorie) {
        this.totalCalorie = totalCalorie;
    }

    public int getFoodQuantity() {
        return FoodQuantity;
    }

    public void setFoodQuantity(int FoodQuantity) {
        this.FoodQuantity = FoodQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoodName() {
        return FoodName;
    }

    public Integer getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(Integer FoodImage) {
        this.FoodImage = FoodImage;
    }}
