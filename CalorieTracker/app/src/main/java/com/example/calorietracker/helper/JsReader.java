package com.example.calorietracker.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;

public class JsReader {

    private InputStream is;
    private BufferedReader bufr;
    private StringBuilder builder;
    private JSONObject root;
    private File file_path;

    public JsReader(File file) throws JSONException {
        file_path = file;
        try {
            FileInputStream is = new FileInputStream(file);
            bufr = new BufferedReader(new InputStreamReader(is));
            String line;
            builder = new StringBuilder();
            while ((line = bufr.readLine()) != null) {
                builder.append(line);
            }
            is.close();
            bufr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            root = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JsReader(FileInputStream input) throws JSONException {
        is = input;
        try {
            bufr = new BufferedReader(new InputStreamReader(is));
            String line;
            builder = new StringBuilder();
            while ((line = bufr.readLine()) != null) {
                builder.append(line);
            }
            is.close();
            bufr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            root = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JsReader(Reader in) {
    }

    public JsReader() {
    }

    public void setInput(InputStream input) {
        is = input;
        try {
            bufr = new BufferedReader(new InputStreamReader(is));
            String line;
            builder = new StringBuilder();
            while ((line = bufr.readLine()) != null) {
                builder.append(line);
            }
            is.close();
            bufr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            root = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public InputStream getInput() {
        return is;
    }

    public JSONObject getObject()  {
        JSONObject obj = null;
        try{
        obj = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String getStringByName(JSONObject obj, String name){
        String str = "";
        try{
            str = obj.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    public JSONArray getJsonArrayByName(String name){
        JSONArray jsonArray = null;
        try{
            jsonArray = root.getJSONArray(name);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONObject getObjectByIndex(JSONArray array, int index){
        JSONObject obj = null;
        try {
           obj = array.getJSONObject(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Object getProduct(){
        Object obj = new Object();
        return obj;
    }



}
