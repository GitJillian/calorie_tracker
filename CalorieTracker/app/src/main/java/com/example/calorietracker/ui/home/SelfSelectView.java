package com.example.calorietracker.ui.home;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calorietracker.R;
import com.example.calorietracker.adapter.SelfSelectAdapter;
import com.example.calorietracker.helper.ConvertIcon;
import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.helper.MenuReader;
import com.example.calorietracker.helper.MenuWriter;
import com.example.calorietracker.menu.FoodModel;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@SuppressLint("Registered")

public class SelfSelectView extends AppCompatActivity implements SelfSelectAdapter.CallBackUs, SelfSelectAdapter.HomeCallBack {
    public static ArrayList<FoodModel> arrayList = new ArrayList<>();

    public static int cart_count = 0;
    com.example.calorietracker.adapter.SelfSelectAdapter SelfSelectAdapter;
    RecyclerView FoodRecyclerView;
    static String date, path,type;

    Button selectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        date = intent.getStringExtra("date");
        path = intent.getStringExtra("path");
        setContentView(R.layout.self_select_view);
        String typeTitle = type.toUpperCase();
        setTitle("BUILD YOUR "+typeTitle);
        //setting our database
        readDatabase();

        FoodRecyclerView = findViewById(R.id.Food_recycler_view);
        SelfSelectAdapter = new SelfSelectAdapter(arrayList, this, (com.example.calorietracker.adapter.SelfSelectAdapter.HomeCallBack) this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        FoodRecyclerView.setLayoutManager(gridLayoutManager);
        FoodRecyclerView.setAdapter(SelfSelectAdapter);
        SelfSelectAdapter.notifyDataSetChanged();
    }


    private void readDatabase() {
        JSONReaderFactory factory = new JSONReaderFactory();
        MenuReader menuReader;

        try {

            File file = new File(FileHelper.getFileDir(SelfSelectView.this),"/menu_to_publish.JSON");
            System.out.println(file.getAbsolutePath());
            MenuWriter menu_writer = new MenuWriter(file);
            InputStream in = SelfSelectView.this.getAssets().open("menu3.JSON");
            menuReader = new MenuReader(in);
            ArrayList<FoodModel> foodModelArrayList = menuReader.getFoodListObj();
            System.out.println(foodModelArrayList.size());
            menu_writer.writeFoodArray(foodModelArrayList);

            JsReader menu_reader = factory.JSONReaderFactory(file);
            ArrayList<FoodModel> models = menu_reader.getFoodListObj();
            System.out.println("models size"+models.size());

            for (int i = 0; i < models.size(); i++) {
                FoodModel model = models.get(i);
                //FoodModel model = (FoodModel)menu_reader.transJsonToFood(jsonArray.getJSONObject(i));
                model.setImagePath(FileHelper.getDrawable(SelfSelectView.this, model.getName()));
                arrayList.add(model);
            }
            in.close();
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
        menuItem.setIcon(ConvertIcon.convertLayoutToImage(SelfSelectView.this, cart_count, R.drawable.ic_shopping_cart_white_24dp));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cart_action:
                if (cart_count < 1) {
                    Toast.makeText(this, "Nothing is selected", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SelfSelectView.this, SelfSelectCartActivity.class);
                    intent.putExtra("date",date);
                    intent.putExtra("path",path);
                    intent.putExtra("type",type);
                    startActivity(intent);
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