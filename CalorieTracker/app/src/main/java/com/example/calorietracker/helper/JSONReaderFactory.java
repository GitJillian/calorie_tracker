package com.example.calorietracker.helper;

import org.json.JSONException;

import java.io.*;

public class JSONReaderFactory {
    private static final int Menu = 1;
    private static final int Student = 0;

    private JsReader product = null;

    public JsReader JSONReaderFactory(File file_path) throws JSONException {
        String path = file_path.getAbsolutePath();
        int JSONType = determineJSONType(path);

        switch (JSONType) {
            case JSONReaderFactory.Menu:
                product = new MenuReader(file_path);//return a menu reader
            case JSONReaderFactory.Student:
                product = new StudentReader(file_path);//return a student reader
        }
        return product;
    }

    public static int determineJSONType(String path){

        int json_type = 2;
        if(path.contains("menu")){
            json_type = JSONReaderFactory.Menu;
        }
        else{
            json_type = JSONReaderFactory.Student;
        }
        return json_type;
    }


    public JsReader JSONReaderFactory(FileInputStream is) throws JSONException {

        int JSONType = determineJSONType(is);
        switch (JSONType) {
            case JSONReaderFactory.Menu:
                product = new MenuReader(is);
            case JSONReaderFactory.Student:
                product = new StudentReader(is);
        }
        return product;
    }

    public static int determineJSONType(FileInputStream is){

        int json_type = 2;
        BufferedReader bufr;
        StringBuilder builder;
        try {
            bufr = new BufferedReader(new InputStreamReader(is));
            String line;
            builder = new StringBuilder();
            while ((line = bufr.readLine()) != null) {
                builder.append(line);
            }
            is.close();
            bufr.close();
            if(builder.toString().contains("info")){json_type =  JSONReaderFactory.Student;}
            else{
                json_type = JSONReaderFactory.Menu;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return json_type;
    }

}
