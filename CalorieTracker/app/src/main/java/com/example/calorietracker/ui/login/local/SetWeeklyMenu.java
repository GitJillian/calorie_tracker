package com.example.calorietracker.ui.login.local;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import com.example.calorietracker.R;
import com.example.calorietracker.adapter.FoodAdapter;
import com.example.calorietracker.helper.ConvertIcon;
import com.example.calorietracker.menu.FoodModel;

import java.util.ArrayList;

@SuppressLint("Registered")
public class SetWeeklyMenu extends AppCompatActivity implements FoodAdapter.CallBackUs, FoodAdapter.HomeCallBack {

    public static ArrayList<FoodModel> arrayList = new ArrayList<>();
    public static int cart_count = 0;
    com.example.calorietracker.adapter.FoodAdapter FoodAdapter;
    RecyclerView FoodRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_menu);

        addFood();
        FoodAdapter = new FoodAdapter(arrayList, this, (com.example.calorietracker.adapter.FoodAdapter.HomeCallBack) this);
        FoodRecyclerView = findViewById(R.id.Food_recycler_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        FoodRecyclerView.setLayoutManager(gridLayoutManager);
        FoodRecyclerView.setAdapter(FoodAdapter);

    }

//TODO:REPLACE DATABASE
    private void addFood() {
        FoodModel burger = new FoodModel("burger","100","22",R.drawable.burger);
        FoodModel rolls = new FoodModel("rolls", "120", "20", R.drawable.roll);
        arrayList.add(burger);
        arrayList.add(rolls);
        FoodModel seasonedPotatoWedges = new FoodModel("Seasoned Potato Wedges", "100", "21",R.drawable.patato_wedges);
        arrayList.add(seasonedPotatoWedges);
        FoodModel boilEgg = new FoodModel("Boil egg", "70", "63", R.drawable.boiled_egg);
        arrayList.add(boilEgg);
        FoodModel scrambledEgg = new FoodModel("Scrambled egg", "120", "11", R.drawable.scrambled_eggs);
        arrayList.add(scrambledEgg);
        FoodModel frenchToast = new FoodModel("French Toast", "120", "12", R.drawable.french_toast);
        arrayList.add(frenchToast);
        FoodModel grilledBreakfastHam = new FoodModel("Grilled breakfast ham", "35", "5", R.drawable.grilled_ham);
        arrayList.add(grilledBreakfastHam);
        FoodModel vegetarianBakedBeans = new FoodModel("Vegetarian Baked Beans", "150", "7", R.drawable.baked_beans);
        arrayList.add(vegetarianBakedBeans);
        FoodModel cheddarCheese = new FoodModel("Cheddar cheese", "60", "4", R.drawable.cheddar_cheese);
        arrayList.add(cheddarCheese);
        FoodModel dicedHam = new FoodModel("Dice Ham", "20", "3", R.drawable.diced_ham);
        arrayList.add(dicedHam);
        FoodModel egg = new FoodModel("Egg", "160", "20", R.drawable.egg);
        arrayList.add(egg);
        FoodModel eggWhite = new FoodModel("Egg White", "60", "10", R.drawable.egg_whites);
        arrayList.add(eggWhite);
        FoodModel dicedOnion = new FoodModel("Diced Onion", "10", "10", R.drawable.diced_onion);
        arrayList.add(dicedOnion);
        FoodModel Peppers = new FoodModel("Pepper", "10", "24", R.drawable.peppers);
        arrayList.add(Peppers);
        FoodModel slicedMushroom = new FoodModel("Sliced Mushroom", "0", "12", R.drawable.sliced_mushrooms);
        arrayList.add(slicedMushroom);
        FoodModel riceKrispieSquare = new FoodModel("Rice Krispie Square", "80", "10", R.drawable.rice_krispie_square);
        arrayList.add(riceKrispieSquare);/*

        Food saltedCaramelBrownie = new Food("Salted Caramel Brownie", "240", "20", R.drawable.salted_caramel_brownies);
        arrayList.add(saltedCaramelBrownie);
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(ConvertIcon.convertLayoutToImage(SetWeeklyMenu.this, cart_count, R.drawable.ic_shopping_cart_white_24dp));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //  String name = item.get
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.cart_action:
                if (cart_count < 1) {
                    Toast.makeText(this, "there is no item in cart", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this, CartActivity.class));
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }

    @Override
    public void updateCartCount(Context context) {
        invalidateOptionsMenu();
    }

    @Override
    public void addCartItemView() {
    }
}
