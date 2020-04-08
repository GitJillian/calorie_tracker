package com.example.calorietracker.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Report;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.menu.FoodModel;
import org.json.JSONException;
import com.example.calorietracker.helper.StudentWriter;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.helper.MenuReader;
import com.example.calorietracker.ui.home.HomeActivity;
import com.example.calorietracker.ui.home.SelfSelectView;
import com.github.siyamed.shapeimageview.CircularImageView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HealthMode extends Fragment {
    //this one determines how many type of food to be returned
    private int numberOfFood;
    private int temparySum;
    private int sum = 0;
    private int limit;
    private JsReader mReader;
    private static int[] mealBmrs;
    private static int breakfast_calorie, lunch_calorie, dinner_calorie;
    private ArrayList<FoodModel> foodList;
    private String type = "";

    public static HealthMode newInstance(String path, String date) {

        Bundle args = new Bundle();

        HealthMode fragment = new HealthMode();
        args.putString("date",date);
        args.putString("path",path);
        File student_file = new File(path);

        JSONReaderFactory factory = new JSONReaderFactory();
        JsReader reader;
        int breakfastEaten, lunchEaten, dinnerEaten;
        try{
            reader = factory.JSONReaderFactory(student_file);
            Student student = (Student) reader.getProduct();
            mealBmrs= student.getBMRPropotion();
            breakfastEaten = reader.getSum(date)[0];
            lunchEaten = reader.getSum(date)[1];
            dinnerEaten = reader.getSum(date)[2];
            //this is getting the eaten calorie from today
            args.putInt("breakfast_eaten",breakfastEaten);
            args.putInt("lunch_eaten",lunchEaten);
            args.putInt("dinner_eaten",dinnerEaten);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //this refers to the recommended calorie intake
        breakfast_calorie = mealBmrs[0];
        lunch_calorie = mealBmrs[1];
        dinner_calorie = mealBmrs[2];

        //setting the recommended calorie to argument

        args.putInt("breakfast", breakfast_calorie);
        args.putInt("lunch", lunch_calorie);
        args.putInt("dinner", dinner_calorie);
        fragment.setArguments(args);
        return fragment;

    }

    public Boolean isFull(int limit){
        if (this.sum < limit){
            return true;
        }else{
            return false;
        }
    }


    public String[] calorieList(JsReader mReader){

        //getting the calorie list from our database
        ArrayList<FoodModel> menu = mReader.getFoodListObj();
        // getting the menu as a list of <FoodModel>
        String[] list = new String[256];
        for (int i = 0; i < menu.size(); i++){
            list[i] = menu.get(i).getCalorie();
        }

        return list;

    }
    
    //function in order to test whether there is any food in database for average
    public Boolean isNull(float average) {
        float calory = 0;
        Boolean tmp = true;

        String[] list = this.calorieList(this.mReader);

        labelA:
        for (int i = 0; i < list.length; i++) {
            calory = Float.parseFloat(list[i]);
            if (calory > average) {
                tmp = true;
            } else {
                tmp = false;
                break labelA;
            }
        }

        return tmp;

    }

    //function in order to manage foodlist with food limit and food numbers
    public ArrayList<FoodModel> pickFood(int limit, int num){
        ArrayList<FoodModel> list = new ArrayList<>();

        float average = (limit / num);

        try{
            //InputStream in = getContext().getAssets().open("menu3.JSON");
            File file_menu = new File(FileHelper.getFileDir(getContext()),"/menu_to_publish.JSON");
            JSONReaderFactory factory = new JSONReaderFactory();
            mReader = factory.JSONReaderFactory(file_menu);
            //mReader = new MenuReader(in);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<FoodModel> menu = mReader.getFoodListObj();

        labelB:
        while(this.isFull(limit)){
            if (!this.isNull(average)){
                System.out.println("average is " + average);
                ArrayList<FoodModel> menu1 = new ArrayList<>();
                for (int i = 0; i < menu.size(); i++) {
                    if (average < 10){
                        break labelB;
                    }else if (((average+50) > Float.parseFloat(menu.get(i).getCalorie())) && ((average - 50.0) < Float.parseFloat(menu.get(i).getCalorie()))) {
                        System.out.println("menu in " + i +" is "+ menu.get(i).getName()+ " and calorie is "+ menu.get(i).getCalorie());
                        //delete repeated items
                        menu1.add(menu.get(i));

                    }
                }

                FoodModel food = menu1.get((int) (Math.random() * menu1.size()));
                list.add(food);
                sum = sum + Integer.parseInt(food.getCalorie());
                num = num - 1;
                if (num ==0){
                    break labelB;
                }
                else{
                    average = ((limit-sum) / num);
                }
            }else{
                break labelB;
            }
        }
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.health_mode, null);
        Button submitButton, generateButton;
        ListView foodListView;
        foodListView = view.findViewById(R.id.list_image_cart);

        submitButton = view.findViewById(R.id.button_health_submit);
        generateButton = view.findViewById(R.id.button_change_menu);

        final Button btn = view.findViewById(R.id.popupMenuBtn);

        final PopupMenu popupMenu = new PopupMenu(getContext(), btn);
        
        CircularImageView imageView;
        imageView = view.findViewById(R.id.image_view);
        TextView totalText = view.findViewById(R.id.total);
        totalText.setText("Total calorie: 0 cal");
        Uri uri = Uri.parse("android.resource://com.example.calorietracker/drawable/icon_health_mode");
        Glide.with(this).load(String.valueOf(uri)).into(imageView);

        popupMenu.inflate(R.menu.menu_self_select);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });

        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.breakfast:
                                type = "breakfast";
                                limit = breakfast_calorie;
                                break;
                            case R.id.lunch:
                                type = "lunch";
                                limit = lunch_calorie;
                                break;
                            case R.id.dinner:
                                type = "dinner";
                                limit = dinner_calorie;
                                break;
                        }
                        return true;
                    }
                });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_proceed);
                TextView message = dialog.findViewById(R.id.self_proceed_message);
                Button okButton = dialog.findViewById(R.id.ok_button);
                Button cancelButton = dialog.findViewById(R.id.cancel_button);
                int total = 0;
                dialog.show();
                Toast.makeText(getContext(),"Confirm",Toast.LENGTH_SHORT).show();
               // message.setText("Are you sure to add these?");
                switch(type){
                    case "breakfast":
                        total = temparySum + getArguments().getInt("breakfast_eaten");
                        break;
                    case "lunch":
                        total = temparySum + getArguments().getInt("lunch_eaten");
                        break;
                    case "dinner":
                        total = temparySum + getArguments().getInt("dinner_eaten");


                }
                //if the total is greater than upper bound
                if(total > limit + 100){
                 message.setText("Your food choice exceeds your recommended calorie intake. Do you wish to proceed? : )" + "total = "+ String.valueOf(total));
                }
                //if you have not add any food
                else if(temparySum ==0){
                    message.setText("You haven't add any food yet");
                }
                //proper situation
                else{
                    message.setText("Perfect! Do you wish to confirm?");
                }

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                okButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                    public void onClick(View v) {

                       //example to follow. to add a breakfast report
                       Report report = new Report(getArguments().getString("date"));

                       File student_file = new File(getArguments().getString("path"));
                       StudentWriter writer = new StudentWriter(student_file);

                       if (!type.equals("")) {

                           //if the student choose breakfast/lunch/dinner, set sum to one of it accordingly
                           if (type.equals("breakfast")) {
                               report.setBreakfast(temparySum);
                           } else if (type.equals("lunch")) {
                               report.setLunch(temparySum);
                           } else if (type.equals("dinner")) {
                               report.setDinner(temparySum);
                           }


                           try {
                               writer.addReport(report);
                           } catch (JSONException e) {
                               e.printStackTrace();
                           } catch (FileNotFoundException e) {
                               e.printStackTrace();
                           }
                           Intent intent = new Intent(getContext(), HomeActivity.class);
                           foodListView.setVisibility(View.INVISIBLE);
                           totalText.setText("Total calorie: 0 cal");
                           String[] splits =  getArguments().getString("path").split("/");
                           int len = splits.length;
                           String new_path = "/"+splits[len-1];
                           intent.putExtra("path",new_path);
                           intent.putExtra("date",getArguments().getString("date"));
                           startActivity(intent);
                           //since the database has changed, we need to switch to new home activity
                           dialog.dismiss();

                       }

                     else{
                        Toast.makeText(getContext(),"Please select your meal type", Toast.LENGTH_SHORT).show();
                         }
                        }
                    });

                }
            });

        generateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(type.equals("breakfast")){limit = breakfast_calorie;}
                if(type.equals("lunch")){limit = lunch_calorie;}
                if(type.equals("dinner")){limit = dinner_calorie;}
                numberOfFood = (int)(2+Math.random()*3);
                ArrayList<String> nameOfFood = new ArrayList<>();
                while(true){
                    foodList = pickFood(limit, numberOfFood);
                    if(foodList.size() != 0){
                        for(FoodModel model:foodList){
                            nameOfFood.add(model.getName());
                        }
                        break;
                    }
                }
                temparySum = sum;
                totalText.setText("Total calorie: "+ temparySum+" cal");
                ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nameOfFood);
                foodListView.setAdapter(itemAdapter);
                foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.food_info);
                        FoodModel model = foodList.get(position);
                        TextView infoCalorie, infoType, infoProtein, infoFats, infoSodium, infoSugar, infoServing, infoCarbon;
                        infoCalorie = dialog.findViewById(R.id.info_calorie);
                        infoFats = dialog.findViewById(R.id.info_fats);
                        infoProtein = dialog.findViewById(R.id.info_protein);
                        infoServing = dialog.findViewById(R.id.info_serving);
                        infoSodium = dialog.findViewById(R.id.info_sodium);
                        infoSugar = dialog.findViewById(R.id.info_sugar);
                        infoType = dialog.findViewById(R.id.info_type);
                        infoCarbon = dialog.findViewById(R.id.info_carbon);

                        infoCalorie.setText(model.getCalorie());
                        infoFats.setText(model.getFats()+" g");
                        infoProtein.setText(model.getProtein()+" g");
                        infoServing.setText(model.getServingSize());
                        infoSodium.setText(model.getSodium()+" mg");
                        infoSugar.setText(model.getSugar()+" g");
                        infoCarbon.setText(model.getTotalCarbon()+" g");

                        if(model.getType().equals("")){
                            infoType.setText("Regular");
                        }else{
                            infoType.setText(model.getType());
                        }
                        dialog.show();

                        ImageView closeDialog = dialog.findViewById(R.id.imageView_close_dialog_cart);
                        closeDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                    }
                });
                sum = 0;

            }

        });

        return view;

    }


}
