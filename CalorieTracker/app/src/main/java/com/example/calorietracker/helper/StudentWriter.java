package com.example.calorietracker.helper;

import android.content.Context;

import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.data.model.Student;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;


public class StudentWriter extends JsWriter {


    public StudentWriter(File file_out){

        super(file_out);
    }

    public void editStudent(Context context, Student student) throws FileNotFoundException, JSONException {
        FileInputStream in = new FileInputStream(this.output);
        StudentReader reader = new StudentReader(in);

        JSONObject sample = reader.getObject();
        JSONObject old_student = reader.getStudent();
        JSONObject new_student = new JSONObject();
        JSONArray report = reader.getJSONArray();

        new_student.put("name",student.getName());
        new_student.put("gender",student.getGender());
        new_student.put("height",String.valueOf(student.getHeight()));
        new_student.put("weight",String.valueOf(student.getWeight()));
        new_student.put("age",String.valueOf(student.getAge()));
        new_student.put("password",student.getPassword());
        new_student.put("frequency", student.getFrequency());
        sample.put("info", new_student);
        sample.put("report",report);
        if(!student.getName().equals(old_student.getString("name"))){
            File new_student_output = new File(FileHelper.getFileDir(context,"/"+student.getName()+".JSON"));
            super.writePath(new_student_output);
        }
        writeFile(sample);
        context.deleteFile(student.getName()+".JSON");

    }

    //adding report to the student profile
    public void addReport(Report report) throws JSONException, FileNotFoundException {

        FileInputStream in = new FileInputStream(this.output);
        StudentReader reader = new StudentReader(in);
        JSONObject sample = reader.getObject();
        //getting student info
        JSONObject info = sample.getJSONObject("info");
        //getting student reports
        JSONArray reports = sample.getJSONArray("report");
        JSONObject single_report = reportToJson(report);
        reports.put(single_report);
        sample.put("info",info);
        sample.put("report",reports);
        super.writeFile(sample);
    }

    public JSONObject reportToJson(Report report){
        JSONObject single_report = new JSONObject();
        try{
        single_report.put("carbon",String.valueOf(report.getCarbon()));
        single_report.put("protein",String.valueOf(report.getProtein()));
        single_report.put("fats",String.valueOf(report.getFats()));
        single_report.put("calorie",String.valueOf(report.getCalorie()));
        single_report.put("date",report.getDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return single_report;
    }

//initialize new student
    public void writeStudent(Student student, ArrayList<Report> reports) {

        JSONObject sample = new JSONObject();
        FileWriter writer =null;

        try{
            
            JSONObject student_json = new JSONObject();
            JSONArray report = new JSONArray();
            student_json.put("name",student.getName());
            student_json.put("gender",student.getGender());
            student_json.put("height",String.valueOf(student.getHeight()));
            student_json.put("weight",String.valueOf(student.getWeight()));
            student_json.put("age",String.valueOf(student.getAge()));
            student_json.put("password",student.getPassword());
            student_json.put("frequency", student.getFrequency());
            //parsing info
            for (Report value : reports) {
                JSONObject single_report = new JSONObject();
                single_report.put("carbon", String.valueOf(value.getCarbon()));
                single_report.put("protein", String.valueOf(value.getProtein()));
                single_report.put("fats", String.valueOf(value.getFats()));
                single_report.put("calorie", String.valueOf(value.getCalorie()));
                single_report.put("date", value.getDate());
                report.put(single_report);
            }

        sample.put("info",student_json);//putting student info into jsonfile
        sample.put("report",report);//putting report array into jsonfile
        super.writeFile(sample);

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
