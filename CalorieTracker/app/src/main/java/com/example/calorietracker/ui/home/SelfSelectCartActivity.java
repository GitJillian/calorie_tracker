package com.example.calorietracker.ui.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calorietracker.R;
import com.example.calorietracker.adapter.MenuAdapter;
import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
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
    Button proceedToBook,clearAll;
    public static TextView grandTotal;
    private Toolbar mToolbar;
    String date, path, type;
    int limitOfCalorie;

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
        type = intent.getExtras().getString("type");
        File student_file = new File(path);
        StudentWriter writer = new StudentWriter(student_file);

        JSONReaderFactory factory = new JSONReaderFactory();
        JsReader reader;
        try{

        reader = factory.JSONReaderFactory(student_file);
        Student student = (Student) reader.getProduct();
            if(type.equals("breakfast")){
                limitOfCalorie = student.getBMRPropotion()[0];
            }
            else if(type.equals("lunch")){
                limitOfCalorie = student.getBMRPropotion()[1];
            }
            else{
                limitOfCalorie = student.getBMRPropotion()[2];
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        proceedToBook = findViewById(R.id.proceed_to_book);
        grandTotal = findViewById(R.id.grand_total_cart);
        clearAll = findViewById(R.id.delete_all);


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

        grandTotal.setText("Total Calorie: "+grandTotalplus+" cals");
        proceedToBook.setText("Confirm "+type);
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        menuAdapter = new MenuAdapter(temparraylist,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(mLayoutManager);
        cartRecyclerView.setAdapter(menuAdapter);

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temparraylist.clear();
                grandTotal.setText("Total Calorie: 0 cals");
                cartRecyclerView.setVisibility(View.INVISIBLE);
            }
        });





        proceedToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SelfSelectCartActivity.this);
                dialog.setContentView(R.layout.dialog_proceed);
                TextView message = dialog.findViewById(R.id.self_proceed_message);
                Button okButton = dialog.findViewById(R.id.ok_button);
                Button cancelButton = dialog.findViewById(R.id.cancel_button);
                if(grandTotalplus > limitOfCalorie + 100){
                    message.setText("Exceeds recommended calorie range "+String.valueOf(limitOfCalorie - 100)+"~"+String.valueOf(limitOfCalorie + 100)+" cals. \nDo you wish to proceed?\n");
                }
                else if(grandTotalplus < limitOfCalorie - 100){
                    message.setText("Lower than recommended calorie range "+String.valueOf(limitOfCalorie - 100)+"~"+String.valueOf(limitOfCalorie + 100)+" cals.\n Do you wish to proceed?\n");
                }
                else{
                    message.setText("Perfect food choice according to your recommended intake!");
                }

                dialog.show();

                //click OK and confirm your meal
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Report report = new Report(0,0,0,0,date);
                        if(type.equals("breakfast")){
                            report.setBreakfast(grandTotalplus);
                        }
                        else if(type.equals("lunch")){
                            report.setLunch(grandTotalplus);
                        }
                        else{
                            report.setDinner(grandTotalplus);
                        }

                        try {
                            writer.addReport(report);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(SelfSelectCartActivity.this, "Confirm", Toast.LENGTH_SHORT).show();
                        temparraylist.clear();
                        grandTotal.setText("Total Calorie: 0 cals");
                        cartRecyclerView.setVisibility(View.INVISIBLE);
                        dialog.dismiss();

                    }
                });
                //click cancel and return to modify your meal
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

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
