package com.example.calorietracker.helper;

import android.content.Context;
import java.io.File;

public class FileHelper {


        public FileHelper() {
        }

    public static String getFileDir(Context context) {
        return String.valueOf(context.getFilesDir());
    }

    public static boolean userExists(Context context, String name){
            boolean flag;
        String path = getFileDir(context)+"/"+name+".JSON";
        File file = new File(path);
        if(file.exists()){
            flag = true;
        }
        else{
            flag = false;
        }
        return flag;
    }

    public static String getFileDir(Context context, String customPath) {
        String path = context.getFilesDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }


    public static String getCacheDir(Context context) {
        return String.valueOf(context.getCacheDir());
    }

    public static String getCacheDir(Context context, String customPath) {
        String path = context.getCacheDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

    public static String getExternalFileDir(Context context) {
        return String.valueOf(context.getExternalFilesDir(""));
    }


    public static String getExternalFileDir(Context context, String customPath) {
        String path = context.getExternalFilesDir("") + formatPath(customPath);
        mkdir(path);
        return path;
    }

    public static String getExternalCacheDir(Context context) {
        return String.valueOf(context.getExternalCacheDir());
    }


    public static String getExternalCacheDir(Context context, String customPath) {
        String path = context.getExternalCacheDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

    public static void mkdir(String DirPath) {
        File file = new File(DirPath);
        if (!(file.exists() && file.isDirectory())) {
            file.mkdirs();
        }
    }

    private static String formatPath(String path) {
        if (!path.startsWith("/"))
            path = "/" + path;
        while (path.endsWith("/"))
            path = new String(path.toCharArray(), 0, path.length() - 1);
        return path;
    }


}
