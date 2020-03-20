package com.example.calorietracker.helper;

import android.renderscript.ScriptGroup;
import android.util.JsonReader;
import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.menu.FoodModel;
import com.example.calorietracker.ui.login.LoginActivity;
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

   public String getCalorieFromJSON(JSONObject obj){
        return super.getStringByName(obj,"calorie");
   }

    public String getProteinFromJSON(JSONObject obj){
        return super.getStringByName(obj,"protein");
    }

    public String getFatsFromJSON(JSONObject obj){
        return super.getStringByName(obj,"fats");
    }

    public String getCarbonFromJSON(JSONObject obj){
        return super.getStringByName(obj,"carbon");
    }

    public String getDateFromJSON(JSONObject obj){
        return super.getStringByName(obj,"date");
    }

    public Report readReport(JSONObject report){
        int calorie=0;
        int carbon=0;
        int fats=0;
        int protein = 0;
        String date = "";
        try{
            calorie = Integer.parseInt(getCalorieFromJSON(report));
            carbon = Integer.parseInt(getCarbonFromJSON(report));
            fats = Integer.parseInt(getFatsFromJSON(report));
            protein = Integer.parseInt(getProteinFromJSON(report));
            date = getDateFromJSON(report);
            }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Report report_obj = new Report(carbon,calorie,fats, protein,date);
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
}
