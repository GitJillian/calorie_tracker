package com.example.calorietracker.fragment;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.StudentReader;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.siyamed.shapeimageview.CircularImageView;
import org.json.JSONException;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ReportMode extends Fragment {

    private static List<Report> reportData;

    public static ReportMode newInstance(String path) {
        File file = new File(path);
        StudentReader student_reader;
        Student thisUser;
        float userBMR;
        Bundle args = new Bundle();
        ReportMode fragment = new ReportMode();
        try {
            student_reader = new StudentReader(file);
            reportData = student_reader.getArray();
            for(int i=0;i<reportData.size();i++){
                System.out.println("show cal: "+reportData.get(i).getTotal());
            }
            if(reportData.size()==0){
                System.out.println("NO data available");
            }

            thisUser = student_reader.getProduct();
            userBMR = thisUser.getBMR();
            args.putFloat("BMR",userBMR);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        fragment.setArguments(args);
        return fragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.report_mode, null);

        BarChart reportChart = view.findViewById(R.id.bar_chart);
        TextView suggestion = view.findViewById(R.id.suggest);
        TextView averageBMR = view.findViewById(R.id.average_bmr);

        CircularImageView imageView;
        imageView = view.findViewById(R.id.image_view);
        Uri uri = Uri.parse("android.resource://com.example.calorietracker/drawable/ic_report");
        Glide.with(this).load(String.valueOf(uri)).into(imageView);

        List<Float> totalCal = get7dayCal();
        float userBMR = getArguments().getFloat("BMR");
        float countCalAver = 0;
        int countNum = 0;
        reportChart = initBarchart(reportChart,userBMR); //init barchart
        BarData reportData = setBarDate(); //getdata
        reportChart.setData(reportData); //show data
        reportChart.animateY(3000); //animation
        reportChart.invalidate(); //refresh
        for(int j =0; j<totalCal.size();j++){
            if(totalCal.get(j)!=0){
                countCalAver = countCalAver+totalCal.get(j);
                countNum =countNum+1;
            }
        }

        if(countNum!=0) {
            averageBMR.setText("The Daily Average Calorie is " + (float) countCalAver / countNum);
        }
        else{
            averageBMR.setText("The Daily Average Calorie is 0. ");
        }
        if(countCalAver <=userBMR-100){
            suggestion.setText("This Week you may eat too less. ");
        }
        else if(countCalAver >=userBMR+100){
            suggestion.setText("This Week you may eat too much. ");
        }
        else{
            suggestion.setText("Intake balanced. Keep Going! ");
        }

        return view;
    }

    /*
    set data
            */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public BarData setBarDate(){
        List<BarEntry> entries = new ArrayList<>();
        /*for(int i =8;i>1;i--){
            entries.add(new BarEntry(i,new Random().nextInt(1200)));
        }*/
        List<Float> totalCalorie = get7dayCal();
        for(int i =0;i<7;i++){
        //for(int i=6;i>=0;i--){
            entries.add(new BarEntry(i,totalCalorie.get((int)abs(i))));
            //entries.add(new BarEntry(i,new Random().nextInt(2000)));
        }
        BarDataSet barDataSet = new BarDataSet(entries,"Intake Calorie");//set dataset
        BarData barData = new BarData(barDataSet);
        return barData;
    }

    public List<Float> get7dayCal() {
        //String localDay = DateHelper.getTodayFormat2();
        LocalDate localDate = LocalDate.now();
        List<Float> totalCalorie = new ArrayList<>(Arrays.asList(0f, 0f, 0f, 0f, 0f, 0f, 0f));
        for (int i = 0; i < reportData.size(); i++) {
            LocalDate localDate1 = LocalDate.parse(reportData.get(i).getDate());
            int daysGap = localDate.compareTo(localDate1);
            switch (daysGap) {
                case 1:
                    Float day1Cal = totalCalorie.get(6) + reportData.get(i).getTotal();
                    totalCalorie.set(6, day1Cal);
                    break;
                case 2:
                    Float day2Cal = totalCalorie.get(5) + reportData.get(i).getTotal();
                    totalCalorie.set(5, day2Cal);
                    break;
                case 3:
                    Float day3Cal = totalCalorie.get(4) + reportData.get(i).getTotal();
                    totalCalorie.set(4, day3Cal);
                    break;
                case 4:
                    Float day4Cal = totalCalorie.get(3) + reportData.get(i).getTotal();
                    totalCalorie.set(3, day4Cal);
                    break;
                case 5:
                    Float day5Cal = totalCalorie.get(2) + reportData.get(i).getTotal();
                    totalCalorie.set(2, day5Cal);
                    break;
                case 6:
                    Float day6Cal = totalCalorie.get(1) + reportData.get(i).getTotal();
                    totalCalorie.set(1, day6Cal);
                    break;
                case 7:
                    Float day7Cal = totalCalorie.get(0) + reportData.get(i).getTotal();
                    totalCalorie.set(0, day7Cal);
                    break;
            }
        }
        return totalCalorie;
    }
    /*
     *used to initiate barchart
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public BarChart initBarchart(BarChart reportChart,float userBMR){
        reportChart.getDescription().setText("Calorie Diagram");//description
        reportChart.setDrawBarShadow(false);
        reportChart.setDrawValueAboveBar(true);//show value in different barchart
        XAxis xAxis = reportChart.getXAxis(); //get x axis
        YAxis yAxisLeft = reportChart.getAxisLeft(); //get left y axis
        YAxis yAxisRight = reportChart.getAxisRight(); //get right y axis
        setAxis(xAxis,yAxisLeft,yAxisRight,userBMR); //set x y axis
        return reportChart;
    }

    //init x and y axis
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setAxis(XAxis xAxis, YAxis yAxisLeft, YAxis yAxisRight,float userBMR){
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); 
        xAxis.setAxisLineWidth(1); 
        xAxis.setAxisMinimum(-0.5f); 
        xAxis.setAxisMaximum(6.5f);//maximum is 6
        xAxis.setDrawAxisLine(true); 
        xAxis.setDrawGridLines(false); 
        xAxis.setEnabled(true); 
        List<String> stringDate = new ArrayList<String>();
        DateTimeFormatter format =DateTimeFormatter.ofPattern("MM-dd");
        LocalDateTime now = LocalDateTime.now();
        for(int j=7;j>0;j--){
            String date = now.minusDays(j).format(format);
            stringDate.add(date);
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return stringDate.get((int) value);
            }
        });

        LimitLine yLimitLine = new LimitLine(userBMR,"User BMR");//add limit line of y axis
        yAxisLeft.setAxisMinimum(0); 
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(true); 
        yAxisLeft.setAxisLineWidth(1); 
        yAxisLeft.setEnabled(true); 
        yAxisLeft.addLimitLine(yLimitLine);


        yAxisRight.setAxisMinimum(0); 
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(true); 
        yAxisRight.setAxisLineWidth(1); 
        yAxisRight.setEnabled(false); 

    }

}
