package com.example.calorietracker.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class JsWriter {
    File output;

    public JsWriter(File file_out){
        output = file_out;
    }

    public void writePath(File file_path){
        this.output = file_path;
    }

    public void writeFile(JSONObject sample){
        FileWriter writer =null;
        try {
            writer = new FileWriter(output);
            writer.write(sample.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeArrayToFile(JSONArray array){
        FileWriter writer =null;
        try {
            writer = new FileWriter(output);
            writer.write(array.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
