package com.example.calorietracker.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.calorietracker.R;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.*;
import android.graphics.Color;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReportMode extends Fragment {

    public static ReportMode newInstance(String path) {
        File file_path = new File(path);
        JSONReaderFactory factory = new JSONReaderFactory();
        JsReader student_reader;
        String name, gender, frequency, password;
        int age, weight;
        float height;
        Bundle args = new Bundle();
        ReportMode fragment = new ReportMode();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.report_mode, null);
        BarChart reportChart = view.findViewById(R.id.bar_chart);
        TextView suggestion = view.findViewById(R.id.suggest);
        suggestion.setText("Look at meeeeee!");
        reportChart = initBarchart(reportChart); //init barchart
        BarData reportData = setBarDate(); //拿到数据
        reportChart.setData(reportData); //显示数据
        reportChart.invalidate(); //填充数据后刷新
        return view;
    }

    /*
    顾名思义set data
            */
    public BarData setBarDate(){
        List<BarEntry> entries = new ArrayList<>();
        //先自设7个试试
        for(int i =0;i<7;i++){
            entries.add(new BarEntry(i,new Random().nextInt(1200)));
        }
        BarDataSet barDataSet = new BarDataSet(entries,"Report Test");//设置数据集
        BarData barData = new BarData(barDataSet);
        return barData;
    }

    /*
    *used to initiate barchart
     */
    public BarChart initBarchart(BarChart reportChart){
        reportChart.getDescription().setText("Report Diagram");//description
        reportChart.setDrawBarShadow(false);
        reportChart.setDrawValueAboveBar(true);//show value in different barchart
        XAxis xAxis = reportChart.getXAxis(); //get x axis
        YAxis yAxisLeft = reportChart.getAxisLeft(); //get left y axis
        YAxis yAxisRight = reportChart.getAxisRight(); //get right y axis
        setAxis(xAxis,yAxisLeft,yAxisRight); //set x y axis
        return reportChart;
    }

    //init x and y axis
    public void setAxis(XAxis xAxis, YAxis yAxisLeft, YAxis yAxisRight){
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //设置x axis在底部显示
        xAxis.setAxisLineWidth(1); //设置x轴的宽度
        xAxis.setAxisMinimum(1); //设置x轴从0开始刻画
        xAxis.setDrawAxisLine(true); //设置显示x轴轴线
        xAxis.setDrawGridLines(false); //不显示x轴的表格线
        xAxis.setEnabled(true); //设置x轴显示

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
