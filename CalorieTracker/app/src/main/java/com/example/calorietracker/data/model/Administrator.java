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

//update: now we do not need this
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

  }


    public void writeDataBase(Context context, ArrayList<FoodModel> FoodArray) {
      File file = new File(FileHelper.getFileDir(context),"/menu_to_publish.JSON");
      MenuWriter menu_writer = new MenuWriter(file);
      menu_writer.writeFoodArray(FoodArray);
    }
}