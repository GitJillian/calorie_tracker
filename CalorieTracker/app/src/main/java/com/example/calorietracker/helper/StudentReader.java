package com.example.calorietracker.helper;

import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.data.model.Student;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class StudentReader extends JsReader {
    private InputStream is;
    private BufferedReader bufr;
    private StringBuilder builder;
    private JSONObject root;
    private JSONArray reportListJson;



    public StudentReader(File file) throws JSONException {
        super(file);
        root = super.getObject().getJSONObject("info");
        reportListJson = super.getJsonArrayByName("report");
    }

    public StudentReader(FileInputStream in) throws JSONException {
        super(in);
        root = super.getObject().getJSONObject("info");
        reportListJson = super.getJsonArrayByName("report");
    }

    public JSONObject getStudent(){return root;}

    public JSONArray getJSONArray(){
        return this.reportListJson;
    }

    public String getStudentName(){
        return super.getStringByName(root, "name");
    }

    public String getStudentAge(){
        return super.getStringByName(root, "age");
    }

    public String getStudentHeight(){
        return super.getStringByName(root, "height");
    }

    public String getStudentWeight(){
        return super.getStringByName(root, "weight");
    }

    public String getStudentGender(){
        return super.getStringByName(root, "gender");
    }

    public String getFrequency(){
        return super.getStringByName(root, "frequency");
    }

    public String getPassword(){
        return super.getStringByName(root, "password");
    }

@Override
    public Student getProduct(){
        String name,gender, age, height, weight, frequency, password;
        int ageInt = 0;
        int weightInt = 0;
        float heightFloat = 0.0f;
        name = getStudentName();
        gender = getStudentGender();
        age = getStudentAge();
        height = getStudentHeight();
        weight = getStudentWeight();
        frequency = getFrequency();
        password = getPassword();
        try{
            ageInt = Integer.parseInt(age);
            heightFloat = Float.parseFloat(height);
            weightInt = Integer.parseInt(weight);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Student student = new Student(name, gender, ageInt, frequency, heightFloat, weightInt, password);

        return student;
    }

   public String getTotalCalorieFromJSON(JSONObject obj){
        return super.getStringByName(obj,"total");
   }

    public String getBreakfastFromJSON(JSONObject obj){
        return super.getStringByName(obj,"breakfast");
    }

    public String getLunchFromJSON(JSONObject obj){
        return super.getStringByName(obj,"lunch");
    }

    public String getDinnerFromJSON(JSONObject obj){
        return super.getStringByName(obj,"dinner");
    }

    public String getDateFromJSON(JSONObject obj){
        return super.getStringByName(obj,"date");
    }

    public Report readReport(JSONObject report){
        int total_calorie=0;
        int breakfast_calorie=0;
        int lunch_calorie=0;
        int dinner_calorie = 0;
        String date = "";
        try{
            total_calorie = Integer.parseInt(getTotalCalorieFromJSON(report));
            breakfast_calorie = Integer.parseInt(getBreakfastFromJSON(report));
            lunch_calorie = Integer.parseInt(getLunchFromJSON(report));
            dinner_calorie = Integer.parseInt(getDinnerFromJSON(report));
            date = getDateFromJSON(report);
            }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Report report_obj = new Report(breakfast_calorie,lunch_calorie,dinner_calorie,total_calorie,date);
        return report_obj;
    }


   public ArrayList<Report> getArray() {

       ArrayList<Report> reportArrayList = new ArrayList<>();
        for(int i=0; i<reportListJson.length(); i++){
            JSONObject obj = getObjectByIndex(reportListJson,i);
            Report report = readReport(obj);
            reportArrayList.add(report);
        }
        return reportArrayList;
    }


    public ArrayList<Report> getArrayByDate(String date){
        ArrayList<Report> reportArrayList = this.getArray();
        ArrayList<Report> reportByDate = new ArrayList<>();
        for(Report report:reportArrayList){
            if(report.getDate().equals(date)){
                reportByDate.add(report);
            }

        }return reportByDate;

    }

    @Override

    public int[] getSum(String date){
        ArrayList<Report> reportOfDate = this.getArrayByDate(date);
        int[] sums = {0,0,0};
        for(Report report:reportOfDate){
                if(report.getBreakfast()!=0){
                    sums[0]+= report.getBreakfast();

            }
                if(report.getLunch()!=0){
                    sums[1]+= report.getLunch();
                }

                if(report.getDinner()!=0){
                    sums[2]+=report.getDinner();
                }

        }return sums;
    }
}
