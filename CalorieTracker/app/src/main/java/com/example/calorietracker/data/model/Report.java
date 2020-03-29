package com.example.calorietracker.data.model;

public class Report {
    private int breakfast_calorie;
    private int lunch_calorie;
    private int dinner_calorie;
    private int total_calorie;
    private String date;
    public Report(int breakfast, int lunch, int dinner, int total, String date){
       this.breakfast_calorie = breakfast;
       this.lunch_calorie = lunch;
       this.dinner_calorie = dinner;
       this.total_calorie = total;
       this.date = date;
   }



   public Report(String date){
        this.date = date;
        this.breakfast_calorie = 0;
        this.lunch_calorie = 0;
        this.dinner_calorie = 0;
        this.total_calorie = 0;
   }


   public int getBreakfast(){
       return this.breakfast_calorie;
   }
   public int getLunch(){
        return this.lunch_calorie;
    }
    public int getDinner(){
        return this.dinner_calorie;
    }
    public int getTotal(){
        return this.getBreakfast()+this.getLunch()+this.getDinner();
    }
    public void setBreakfast(int breakfast){this.breakfast_calorie = breakfast;}
    public void setLunch(int lunch){this.lunch_calorie = lunch; }
    public void setDinner(int dinner){this.dinner_calorie = dinner;}
    public void setTotal(){this.total_calorie = this.breakfast_calorie+this.lunch_calorie+this.dinner_calorie;}


   public String getDate(){
       return this.date;
   }


}
