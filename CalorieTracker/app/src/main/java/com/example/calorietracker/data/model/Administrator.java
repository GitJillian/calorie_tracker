package com.example.calorietracker.data.model;

import android.util.JsonReader;
import com.example.calorietracker.menu.FoodModel;

import java.util.*;
import java.util.ArrayList;

//TODO: finish administrator while reading json file
public class Administrator {

  private JsonReader reader;
  private ArrayList<FoodModel> menu;
  private ArrayList<FoodModel> menuToPublish;
  
  public Administrator(){


      this.menu = new ArrayList<FoodModel>();
      this.menuToPublish = new ArrayList<FoodModel>();
  }
  
  public Administrator(ArrayList<FoodModel> foods){
      this.menu = foods;
      this.menuToPublish = new ArrayList<FoodModel>();
  }

  // adding new item to local menu into food database(into json file)
  void addItemToMenu(){
      
    
  }
  //adding new item to menu into the release at client side
  void addItemToPublish()
  {
    
    
  }
  // these three to be finished by adding andriod handler
  void deleteItemFromMenu(){
    
  }
  public void viewFoodItems(){
    Iterator<FoodModel> iterator = this.menu.iterator();
    while (iterator.hasNext()) {
   //   System.out.println(iterator.next().foodStats());
    }
  }
  // read food items from json file
  public void getFoodFromJson(){
   /* this.reader.
    this.menu = this.reader.getList();*/
  }

}