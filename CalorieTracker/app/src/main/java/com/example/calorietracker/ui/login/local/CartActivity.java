package com.example.calorietracker.ui.login.local;

import android.annotation.SuppressLint;
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
import com.example.calorietracker.adapter.CartAdapter;
import com.example.calorietracker.data.model.Administrator;
import com.example.calorietracker.menu.FoodImage;
import com.example.calorietracker.menu.FoodModel;

import java.util.ArrayList;

import static com.example.calorietracker.adapter.FoodAdapter.cartModels;
import static com.example.calorietracker.adapter.FoodAdapter.FoodArrayPublish;

public class CartActivity extends AppCompatActivity {

    public static TextView grandTotal;
    public static int grandTotalplus;
    public static ArrayList<FoodModel> modelArrayList;
    public static ArrayList<FoodImage> temparraylist;
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    Button proceedToBook;
    private Toolbar mToolbar;
    Administrator admin;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        temparraylist = new ArrayList<>();
        admin = new Administrator();
        proceedToBook = findViewById(R.id.proceed_to_book);
        //grandTotal = findViewById(R.id.grand_total_cart);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Weekly Menu");


        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // these lines of code for show the same  cart for future refrence
                grandTotalplus = 0;

                cartModels.addAll(temparraylist);
                SetWeeklyMenu.cart_count = (temparraylist.size());

                finish();
            }
        });
        SetWeeklyMenu.cart_count = 0;

        // from these lines of code we remove the duplicacy of cart and set last added quantity in cart
        // for replace same item
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
        //FoodArrayPublish.addAll(modelArrayList);
        temparraylist.addAll(cartModels);
        cartModels.clear();

        // this code is for get total Calorie
        for (int i = 0; i < temparraylist.size(); i++) {
            grandTotalplus += temparraylist.get(i).getTotalCalorie();
        }
//        grandTotal.setText("Total Calorie"+ grandTotalplus);
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        cartAdapter = new CartAdapter(temparraylist, modelArrayList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(mLayoutManager);
        cartRecyclerView.setAdapter(cartAdapter);


        proceedToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "Confirm", Toast.LENGTH_SHORT).show();
              //  admin.writeDataBase(CartActivity.this, FoodArrayPublish);
                admin.writeDataBaseImage(CartActivity.this,temparraylist);
                FoodArrayPublish.clear();
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
            SetWeeklyMenu.cart_count = (temparraylist.size());
        }
        cartModels.addAll(temparraylist);
    }




}
