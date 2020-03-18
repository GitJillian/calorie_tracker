package com.example.calorietracker.data.model;

public class Report {
   private int carbon;
   private int calorie;
   private int fats;
   private int protein;
   private String date;
   public Report(int carbon, int calorie, int fats, int protein, String date){
       this.carbon = carbon;
       this.calorie = calorie;
       this.fats = fats;
       this.protein = protein;
       this.date = date;
   }
   public int getCarbon(){
       return this.carbon;
   }

   public int getFats(){
       return this.fats;
   }

   public int getCalorie(){
       return this.calorie;
   }

   public String getDate(){
       return this.date;
   }

   public int getProtein(){
       return this.protein;
   }

}
