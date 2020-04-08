package com.example.calorietracker.ui.home;

import java.io.FileNotFoundException;
import java.util.Calendar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Administrator;
import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.fragment.HealthMode;
import com.example.calorietracker.fragment.ReportMode;
import com.example.calorietracker.fragment.SelfSelectedMode;
import com.example.calorietracker.helper.DateHelper;
import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.helper.StudentWriter;
import com.example.calorietracker.ui.design.BottomNavigationViewHelper;
import com.example.calorietracker.fragment.HomeInfo;
import com.example.calorietracker.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.File;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private Administrator admin = new Administrator();
    String frequency, name, gender, password, path;
    int weight, age;
    float height;
    String todayStr;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todayStr = DateHelper.getTodayFormat();
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        JsReader student_reader;

        path = intent.getStringExtra("path");
        File file_path = new File(FileHelper.getFileDir(HomeActivity.this)+path);
        JSONReaderFactory factory = new JSONReaderFactory();

        try {
            student_reader = factory.JSONReaderFactory(file_path);
            Student student = (Student)student_reader.getProduct();
            /*Report report1 = new Report("2020-04-01");
            report1.setLunch(1293);
            Report report2 = new Report("2020-04-02");
            report2.setLunch(1344);
            Report report3 = new Report("2020-04-03");
            report3.setLunch(2000);
            Report report4 = new Report("2020-04-04");
            report4.setBreakfast(1331);
            Report report5 = new Report("2020-04-05");
            report5.setDinner(1922);
            Report report6 = new Report("2020-04-06");
            report6.setBreakfast(2000);
            StudentWriter writer = new StudentWriter(file_path);
            writer.addReport(report1);
            writer.addReport(report2);
            writer.addReport(report3);
            writer.addReport(report4);
            writer.addReport(report5);
            writer.addReport(report6);*/
            frequency = student.getFrequency();
            age = student.getAge();
            gender = student.getGender();
            height = student.getHeight();
            weight = student.getWeight();
            name = student.getName();
            } catch (JSONException e) {
            e.printStackTrace();
        } /*catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_person:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.action_self_select_mode:
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.action_health_mode:
                            viewPager.setCurrentItem(2);
                            break;
                        case R.id.action_report:
                            viewPager.setCurrentItem(3);
                            break;
                    }
                    return false;
                });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupViewPager(viewPager);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupViewPager(ViewPager viewPager) throws JSONException {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HomeInfo.newInstance(FileHelper.getFileDir(HomeActivity.this)+path,todayStr));
        adapter.addFragment(SelfSelectedMode.newInstance(FileHelper.getFileDir(HomeActivity.this)+path,todayStr));
        adapter.addFragment(HealthMode.newInstance(FileHelper.getFileDir(HomeActivity.this)+path,todayStr));
        adapter.addFragment(ReportMode.newInstance(FileHelper.getFileDir(HomeActivity.this)+path));
        viewPager.setAdapter(adapter);
    }
}
