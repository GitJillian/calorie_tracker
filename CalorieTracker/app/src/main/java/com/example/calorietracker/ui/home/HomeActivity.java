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
import com.example.calorietracker.ui.design.BottomNavigationViewHelper;
import com.example.calorietracker.fragment.HomeInfo;
import com.example.calorietracker.ui.design.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private Administrator admin = new Administrator();
    String frequency, name, gender, password;
    int weight, age;
    float height;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle data = getIntent().getExtras();
        Intent intent = getIntent();

        frequency = intent.getStringExtra("frequency");
        name = intent.getStringExtra("name");
        gender = intent.getStringExtra("gender");
        weight = intent.getIntExtra("weight",0);
        height = intent.getFloatExtra("height",0);
        age = intent.getIntExtra("age",0);
        password = intent.getStringExtra("password");

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

        adapter.addFragment(HomeInfo.newInstance(name,gender,frequency,weight, height, age,password));
        adapter.addFragment(BaseFragment.newInstance("Self-selected"));
        adapter.addFragment(BaseFragment.newInstance("Health mode"));
        adapter.addFragment(BaseFragment.newInstance("Report"));
        viewPager.setAdapter(adapter);
    }
}