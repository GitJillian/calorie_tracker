package com.example.calorietracker.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    private static Date today;
    private static String todayStr;
    private static String todayFormat;


    public static Date getToday(){
        Calendar calendar = Calendar.getInstance();
        Date today_get = calendar.getTime();
        today = today_get;
        return today;

    }

    public static String getTodayStr(){
        return getToday().toString();
    }

    public static String getTodayFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        todayFormat = sdf.format(getToday());
        return todayFormat;

    }

}
