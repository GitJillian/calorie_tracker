package com.example.calorietracker.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calorietracker.R;
import com.example.calorietracker.adapter.MenuAdapter;
import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.helper.StudentWriter;
import com.example.calorietracker.menu.FoodImage;


import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static com.example.calorietracker.adapter.SelfSelectAdapter.cartModels;


public class SelfSelectCartActivity extends AppCompatActivity {

    public static int grandTotalplus;
    public static ArrayList<FoodImage> temparraylist;
    RecyclerView cartRecyclerView;
    MenuAdapter menuAdapter;
    Button proceedToBook,grandTotal;
    private Toolbar mToolbar;
    String date, path;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_select_mode);
        temparraylist = new ArrayList<>();
        Intent intent = getIntent();
        date = intent.getExtras().getString("date");
        path = intent.getExtras().getString("path");
        File student_file = new File(path);
        StudentWriter writer = new StudentWriter(student_file);


        proceedToBook = findViewById(R.id.proceed_to_book);
        grandTotal = findViewById(R.id.grand_total_cart);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Your Food Cart");


        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grandTotalplus = 0;
                cartModels.addAll(temparraylist);
                SelfSelectView.cart_count = (temparraylist.size());

                finish();
            }
        });
        SelfSelectView.cart_count = 0;

        for (int i = 0; i < cartModels.size(); i++) {
            for (int j = i + 1; j < cartModels.size(); j++) {
                if (cartModels.get(i).getFoodImage().equals(cartModels.get(j).getFoodImage())) {
                    cartModels.get(i).setFoodQuantity(cartModels.get(j).getFoodQuantity());
                    cartModels.get(i).setTotalCalorie(cartModels.get(j).getTotalCalorie());
                    cartModels.get(i).setFoodName(cartModels.get(j).getFoodName());
                    cartModels.remove(j);
                    j--;

                }
            }

        }

        temparraylist.addAll(cartModels);
        cartModels.clear();

        for (int i = 0; i < temparraylist.size(); i++) {
            grandTotalplus += temparraylist.get(i).getTotalCalorie();
        }
        grandTotal.setText("Calorie Summary");
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        menuAdapter = new MenuAdapter(temparraylist,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(mLayoutManager);
        cartRecyclerView.setAdapter(menuAdapter);


        proceedToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Report report = new Report(0,grandTotalplus,0,0,date);
                try {
                    writer.addReport(report);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(SelfSelectCartActivity.this, "Confirm", Toast.LENGTH_SHORT).show();
                temparraylist.clear();
                cartRecyclerView.setVisibility(View.INVISIBLE);
            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        grandTotalplus = 0;
        for (int i = 0; i < temparraylist.size(); i++) {
            SelfSelectView.cart_count = (temparraylist.size());
        }
        cartModels.addAll(temparraylist);
    }


}
