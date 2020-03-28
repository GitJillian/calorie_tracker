package com.example.calorietracker.ui.home;
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
import com.example.calorietracker.helper.MenuReader;
import com.example.calorietracker.menu.FoodModel;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SelfSelectView extends AppCompatActivity implements SelfSelectAdapter.CallBackUs, SelfSelectAdapter.HomeCallBack {
    public static ArrayList<FoodModel> arrayList = new ArrayList<>();

    public static int cart_count = 0;
    com.example.calorietracker.adapter.SelfSelectAdapter SelfSelectAdapter;
    RecyclerView FoodRecyclerView;
    static String date, path;

    Button selectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        path = intent.getStringExtra("path");
        setContentView(R.layout.activity_set_menu);

        readDatabase();

        FoodRecyclerView = findViewById(R.id.Food_recycler_view);
        selectAll = findViewById(R.id.button_submit);

        SelfSelectAdapter = new SelfSelectAdapter(arrayList, this, (com.example.calorietracker.adapter.SelfSelectAdapter.HomeCallBack) this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        FoodRecyclerView.setLayoutManager(gridLayoutManager);
        FoodRecyclerView.setAdapter(SelfSelectAdapter);


    }


    private void readDatabase() {
        JSONReaderFactory factory = new JSONReaderFactory();
        MenuReader menuReader;

        try {
            InputStream in = SelfSelectView.this.getAssets().open("menu3.JSON");
            //File file = new File(FileHelper.getFileDir(SelfSelectView.this,"/menu_release.JSON"));
           // MenuWriter writer = new MenuWriter(file);

            menuReader = new MenuReader(in);
            ArrayList<FoodModel> foodModelArrayList = menuReader.getFoodListObj();

            //writer.writeFoodArray(foodModelArrayList);

           // JsReader menu_reader = factory.JSONReaderFactory(file);
            //JSONArray foodList = (JSONArray) menu_reader.getProduct();
            for (int i = 0; i < foodModelArrayList.size(); i++) {
                FoodModel model = foodModelArrayList.get(i);
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
                    startActivity(intent);
                    //startActivity(new Intent(this, SelfSelectCartActivity.class));
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