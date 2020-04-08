package com.example.calorietracker.data.model;

import android.content.Context;
import android.util.JsonReader;

import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.helper.MenuWriter;
import com.example.calorietracker.menu.FoodImage;
import com.example.calorietracker.menu.FoodModel;

import java.io.File;
import java.util.*;
import java.util.ArrayList;

//update: now we do not need this since we realized that the product ids in RESTAPI is hard coded
//Then if we send a HTTP REQUEST, we won't get all product ids, also sometimes it return null
//then we decide to delete the function of admin, but the interface is still here
public class Administrator {

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

  public void writeDataBaseImage(Context context, ArrayList<FoodImage> FoodArray){
      File file = new File(FileHelper.getFileDir(context),"/menu_to_publish.JSON");
      MenuWriter menu_writer = new MenuWriter(file);
      menu_writer.writeFoodImageArray(context,FoodArray);
      //not used any more

  }


    public void writeDataBase(Context context, ArrayList<FoodModel> FoodArray) {
      File file = new File(FileHelper.getFileDir(context),"/menu_to_publish.JSON");
      MenuWriter menu_writer = new MenuWriter(file);
      menu_writer.writeFoodArray(FoodArray);
      //not used any more
    }
}