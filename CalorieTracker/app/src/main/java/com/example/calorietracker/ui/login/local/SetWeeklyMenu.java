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
import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.helper.MenuReader;
import com.example.calorietracker.menu.FoodModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@SuppressLint("Registered")
public class SetWeeklyMenu extends AppCompatActivity implements FoodAdapter.CallBackUs, FoodAdapter.HomeCallBack {

    public static ArrayList<FoodModel> arrayList = new ArrayList<>();
    public static int cart_count = 0;
    com.example.calorietracker.adapter.FoodAdapter FoodAdapter;
    RecyclerView FoodRecyclerView;
    JSONReaderFactory factory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_menu);

        readDatabase();
        FoodAdapter = new FoodAdapter(arrayList, this, (com.example.calorietracker.adapter.FoodAdapter.HomeCallBack) this);
        FoodRecyclerView = findViewById(R.id.Food_recycler_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        FoodRecyclerView.setLayoutManager(gridLayoutManager);
        FoodRecyclerView.setAdapter(FoodAdapter);

    }

//TODO:REPLACE DATABASE
    private void readDatabase() {

        MenuReader menuReader;

        try{
        InputStream in = SetWeeklyMenu.this.getAssets().open("menu3.JSON");
        menuReader = new MenuReader(in);
        JSONArray jsonArray = menuReader.getFoodListJson();
        for(int i = 0; i<jsonArray.length(); i++){
            FoodModel model = menuReader.transJsonToFood(jsonArray.getJSONObject(i));
            //TODO:AFTER ALL PICTURES FOUNDED, PLEASE ADD IT!
            //model.setImagePath(FileHelper.getDrawable(SetWeeklyMenu.this,model.getName()));
            arrayList.add(model);
        }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
