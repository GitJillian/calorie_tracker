package com.example.calorietracker.ui.login.local;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calorietracker.R;
import com.example.calorietracker.adapter.CartAdapter;
import com.example.calorietracker.menu.FoodImage;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.calorietracker.adapter.FoodAdapter.cartModels;


/**
 * Created by Deependra Singh Patel on 8/5/19.
 */

public class CartActivity extends AppCompatActivity {

    public static TextView grandTotal;
    public static int grandTotalplus;
    
    public static ArrayList<FoodImage> temparraylist;
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    LinearLayout proceedToBook;
    Context context;
    private Toolbar mToolbar;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        context = this;
        temparraylist = new ArrayList<>();

        proceedToBook = findViewById(R.id.proceed_to_book);
        grandTotal = findViewById(R.id.grand_total_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Generate Weekly Menu");

        //setSupportActionBar(mToolbar);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // these lines of code for show the same  cart for future refrence
                grandTotalplus = 0;

                cartModels.addAll(temparraylist);
                SetWeeklyMenu.cart_count = (temparraylist.size());
//                addItemInCart.clear();
                finish();
            }
        });
        SetWeeklyMenu.cart_count = 0;

        //addInCart();

        Log.d("sizecart_1", String.valueOf(temparraylist.size()));
        Log.d("sizecart_2", String.valueOf(cartModels.size()));

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
                    Log.d("remove", String.valueOf(cartModels.size()));

                }
            }

        }
        temparraylist.addAll(cartModels);
        cartModels.clear();
        Log.d("sizecart_11", String.valueOf(temparraylist.size()));
        Log.d("sizecart_22", String.valueOf(cartModels.size()));
        // this code is for get total Calorie
        for (int i = 0; i < temparraylist.size(); i++) {
            grandTotalplus += temparraylist.get(i).getTotalCalorie();
        }
        grandTotal.setText("Total Calorie"+ grandTotalplus);
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        cartAdapter = new CartAdapter(temparraylist, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(mLayoutManager);
        cartRecyclerView.setAdapter(cartAdapter);


        proceedToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "Confirm your meal", Toast.LENGTH_SHORT).show();
                temparraylist.clear();
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
