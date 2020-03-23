package com.example.calorietracker.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Administrator;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.fragment.BaseFragment;
import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.ui.design.BottomNavigationViewHelper;
import com.example.calorietracker.fragment.HomeInfo;
import com.example.calorietracker.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.File;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private Administrator admin = new Administrator();
    String frequency, name, gender, password, path;
    int weight, age;
    float height;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Bundle data = getIntent().getExtras();
        Intent intent = getIntent();
        JsReader student_reader;

        path = intent.getStringExtra("path");
        File file_path = new File(FileHelper.getFileDir(HomeActivity.this)+path);
        JSONReaderFactory factory = new JSONReaderFactory();

        try {
            student_reader = factory.JSONReaderFactory(file_path);
            Student student = (Student)student_reader.getProduct();
            frequency = student.getFrequency();
            age = student.getAge();
            gender = student.getGender();
            height = student.getHeight();
            weight = student.getWeight();
            name = student.getName();
            } catch (JSONException e) {
            e.printStackTrace();
        }

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
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HomeInfo.newInstance(FileHelper.getFileDir(HomeActivity.this)+path));
        adapter.addFragment(BaseFragment.newInstance("Self-selected"));
        adapter.addFragment(BaseFragment.newInstance("Health mode"));
        //adapter.addFragment(BaseFragment.newInstance("Report"));
        adapter.addFragment(ReportMode.newInstance(FileHelper.getFileDir(HomeActivity.this)+path));
        viewPager.setAdapter(adapter);
    }
}
