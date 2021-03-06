package com.example.calorietracker.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calorietracker.R;
import com.example.calorietracker.menu.FoodImage;
import com.example.calorietracker.menu.FoodModel;
import com.example.calorietracker.ui.home.SelfSelectView;


import static com.example.calorietracker.ui.home.SelfSelectView.arrayList;
import java.util.ArrayList;

//This is an adapter for SelfSelectView
public class SelfSelectAdapter extends RecyclerView.Adapter<SelfSelectAdapter.ViewHolder> {

    public static ArrayList<FoodModel> FoodsArray;
    public static ArrayList<FoodImage> cartModels = new ArrayList<>();
    public static FoodImage cartModel;
    private Context context;
    private HomeCallBack homeCallBack;

    public SelfSelectAdapter(ArrayList<FoodModel> FoodsArray,Context context, HomeCallBack mCallBackus) {
        this.FoodsArray = FoodsArray;
        this.context = context;
        this.homeCallBack = mCallBackus;

    }

    @NonNull
    @Override
    public SelfSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_adapter_layout, viewGroup, false);
        return new SelfSelectAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelfSelectAdapter.ViewHolder viewHolder, final int i) {
        //shows food name and image
        viewHolder.FoodName.setText(FoodsArray.get(i).getName());

        viewHolder.FoodImage.setImageDrawable(ContextCompat.getDrawable(context, FoodsArray.get(i).getImagePath()));
        //if you click on each picture of the food list, you are able to see a view holder that shows food information
        viewHolder.FoodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.view_food);
                TextView infoCalorie, infoType, infoProtein, infoFats, infoSodium, infoSugar, infoServing, infoCarbon;
                infoCalorie = dialog.findViewById(R.id.info_calorie);
                infoFats = dialog.findViewById(R.id.info_fats);
                infoProtein = dialog.findViewById(R.id.info_protein);
                infoServing = dialog.findViewById(R.id.info_serving);
                infoSodium = dialog.findViewById(R.id.info_sodium);
                infoSugar = dialog.findViewById(R.id.info_sugar);
                infoType = dialog.findViewById(R.id.info_type);
                infoCarbon = dialog.findViewById(R.id.info_carbon);

                infoCalorie.setText(FoodsArray.get(i).getCalorie());
                   infoFats.setText(FoodsArray.get(i).getFats()+" g");
                infoProtein.setText(FoodsArray.get(i).getProtein()+" g");
                infoServing.setText(FoodsArray.get(i).getServingSize());
                 infoSodium.setText(FoodsArray.get(i).getSodium()+" mg");
                  infoSugar.setText(FoodsArray.get(i).getSugar()+" g");
                 infoCarbon.setText(FoodsArray.get(i).getTotalCarbon()+" g");

                 //for the food that is neither "vegan" nor "vegetarian", would be set to "regular"
                if(FoodsArray.get(i).getType().equals("")){
                    infoType.setText("Regular");
                }else{
                    infoType.setText(FoodsArray.get(i).getType());
                }

                //decrement item quantity
                final ImageView cartDecrement = dialog.findViewById(R.id.cart_decrement);
                //increment item quntity
                ImageView cartIncrement = dialog.findViewById(R.id.cart_increment);
                //close the dialog button
                ImageView closeDialog = dialog.findViewById(R.id.imageView_close_dialog_cart);
                //update quantity
                Button updateQtyDialog = dialog.findViewById(R.id.update_quantity_dialog);
                //show quantity in textview
                final TextView quantity = dialog.findViewById(R.id.cart_Food_quantity_tv);
                //first add, set to 1
                quantity.setText(String.valueOf(1));
                final int[] cartCounter = {0};
                cartDecrement.setEnabled(false);
                cartDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cartCounter[0] == 1) {
                            Toast.makeText(context, "cant add less than 0", Toast.LENGTH_SHORT).show();
                        } else {
                            cartCounter[0] -= 1;
                            quantity.setText(String.valueOf(cartCounter[0]));
                        }

                    }
                });

                //click increment button, your food quantity +=1
                cartIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartDecrement.setEnabled(true);
                        cartCounter[0] += 1;
                        quantity.setText(String.valueOf(cartCounter[0]));


                    }
                });

                dialog.show();
                updateQtyDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cartModel = new FoodImage();
                        cartModel.setFoodQuantity((cartCounter[0]));
                        cartModel.setFoodCalorie(arrayList.get(i).getCalorie());
                        cartModel.setFoodImage(arrayList.get(i).getImagePath());
                        cartModel.setFoodName(arrayList.get(i).getName());
                        cartModel.setTotalCalorie(cartCounter[0] *
                                Integer.parseInt(arrayList.get(i).getCalorie()));

                        cartModels.add(cartModel);

                        // from these lines of code we update badge count value
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
                        SelfSelectView.cart_count = cartModels.size();
                        homeCallBack.updateCartCount(context);
                        dialog.dismiss();
                    }

                });
                //close dialog
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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


                        SelfSelectView.cart_count = cartModels.size();
                        homeCallBack.updateCartCount(context);
                        dialog.dismiss();
                    }
                });


            }
        });


    }

    @Override
    public int getItemCount() {
        return FoodsArray.size();
    }

    public interface CallBackUs {
        void addCartItemView();
    }


    public interface HomeCallBack {
        void updateCartCount(Context context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView FoodImage;
        TextView FoodName;

        ViewHolder(View itemView) {
            super(itemView);
            FoodImage = itemView.findViewById(R.id.android_gridview_image);
            FoodName = itemView.findViewById(R.id.android_gridview_text);
        }
    }
}