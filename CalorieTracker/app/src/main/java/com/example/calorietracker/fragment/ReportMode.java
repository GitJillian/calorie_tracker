package com.example.calorietracker.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.helper.StudentReader;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class ReportMode extends Fragment {

    private static List<Report> reportData;
    public static ReportMode newInstance(String path) {
        File file = new File(path);
        StudentReader student_reader;
        try {
            student_reader = new StudentReader(file);
            reportData = student_reader.getArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle args = new Bundle();
        ReportMode fragment = new ReportMode();
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
        suggestion.setText("Look at meeeeee!");
        reportChart = initBarchart(reportChart); //init barchart
        BarData reportData = null; //拿到数据
        try {
            reportData = setBarDate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reportChart.setData(reportData); //显示数据
        reportChart.animateY(3000);
        reportChart.invalidate(); //填充数据后刷新
        return view;
    }

    /*
    顾名思义set data
            */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public BarData setBarDate() throws IOException {
        List<BarEntry> entries = new ArrayList<>();

        //先自设7个试试

        /*for(int i =8;i>1;i--){
            entries.add(new BarEntry(i,new Random().nextInt(1200)));
        }*/
        LocalDate localDate=LocalDate.now();
        List<Float> totalCalorie = new ArrayList<>(Arrays.asList(0f, 0f, 0f, 0f, 0f, 0f,0f));
        for(int i = 0;i<reportData.size();i++){
            LocalDate localDate1 = LocalDate.parse(reportData.get(i).getDate());
            int daysGap = localDate.compareTo(localDate1);
            switch (daysGap){
                case 1:
                    Float day1Cal = totalCalorie.get(0) + reportData.get(i).getTotal();
                    totalCalorie.set(0,day1Cal);
                    break;
                case 2:
                    Float day2Cal = totalCalorie.get(1) + reportData.get(i).getTotal();
                    totalCalorie.set(1,day2Cal);
                    break;
                case 3:
                    Float day3Cal = totalCalorie.get(2) + reportData.get(i).getTotal();
                    totalCalorie.set(1,day3Cal);
                    break;
                case 4:
                    Float day4Cal = totalCalorie.get(3) + reportData.get(i).getTotal();
                    totalCalorie.set(1,day4Cal);
                    break;
                case 5:
                    Float day5Cal = totalCalorie.get(4) + reportData.get(i).getTotal();
                    totalCalorie.set(1,day5Cal);
                    break;
                case 6:
                    Float day6Cal = totalCalorie.get(5) + reportData.get(i).getTotal();
                    totalCalorie.set(1,day6Cal);
                    break;
                case 7:
                    Float day7Cal = totalCalorie.get(6) + reportData.get(i).getTotal();
                    totalCalorie.set(1,day7Cal);
                    break;
            }
        }
        for(int i =8;i>1;i--){
            entries.add(new BarEntry(i,totalCalorie.get((int)abs(i-8))));
        }
        BarDataSet barDataSet = new BarDataSet(entries,"Intake Calorie");//设置数据集
        BarData barData = new BarData(barDataSet);
        return barData;
    }

    /*
     *used to initiate barchart
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public BarChart initBarchart(BarChart reportChart){
        reportChart.getDescription().setText("Calorie Diagram");//description
        reportChart.setDrawBarShadow(false);
        reportChart.setDrawValueAboveBar(true);//show value in different barchart
        XAxis xAxis = reportChart.getXAxis(); //get x axis
        YAxis yAxisLeft = reportChart.getAxisLeft(); //get left y axis
        YAxis yAxisRight = reportChart.getAxisRight(); //get right y axis
        setAxis(xAxis,yAxisLeft,yAxisRight); //set x y axis
        return reportChart;
    }

    //init x and y axis
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setAxis(XAxis xAxis, YAxis yAxisLeft, YAxis yAxisRight){
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //设置x axis在底部显示
        xAxis.setAxisLineWidth(1); //设置x轴的宽度
        xAxis.setAxisMinimum(-0.5f); //设置x轴从0开始刻画
        xAxis.setAxisMaximum(6.5f);//maximum is 6
        xAxis.setDrawAxisLine(true); //设置显示x轴轴线
        xAxis.setDrawGridLines(false); //不显示x轴的表格线
        xAxis.setEnabled(true); //设置x轴显示
        List<String> stringDate = new ArrayList<String>();
        DateTimeFormatter format =DateTimeFormatter.ofPattern("MM-dd");
        LocalDateTime now = LocalDateTime.now();
        for(int j=8;j>1;j--){
            String date = now.minusDays(j).format(format);
            stringDate.add(date);
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return stringDate.get((int) value);
            }
        });

        yAxisLeft.setAxisMinimum(0); //设置y轴从0开始
        yAxisLeft.setDrawGridLines(false);//不显示y轴的表格线
        yAxisLeft.setDrawAxisLine(true); //设置显示y轴轴线
        yAxisLeft.setAxisLineWidth(1); //设置y轴的宽度
        yAxisLeft.setEnabled(true); //设置左边y轴显示

        yAxisRight.setAxisMinimum(0); //设置y轴从0开始
        yAxisRight.setDrawGridLines(false);//不显示y轴的表格线
        yAxisRight.setDrawAxisLine(true); //设置显示y轴轴线
        yAxisRight.setAxisLineWidth(1); //设置y轴的宽度
        yAxisRight.setEnabled(false); //设置右边y轴不显示

    }

}