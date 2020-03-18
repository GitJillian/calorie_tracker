package com.example.calorietracker.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calorietracker.R;
import com.example.calorietracker.menu.FoodImage;
import com.example.calorietracker.menu.FoodModel;
import com.example.calorietracker.ui.login.local.CartActivity;
import com.example.calorietracker.ui.login.local.SetWeeklyMenu;
import static com.example.calorietracker.ui.login.local.SetWeeklyMenu.arrayList;
import java.util.ArrayList;


/**
 * Created by Deependra Singh Patel on 24/4/19.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    public static ArrayList<FoodModel> FoodsArray;
    public static ArrayList<FoodImage> cartModels = new ArrayList<FoodImage>();
    public static FoodImage cartModel;
    Context context;
    private CallBackUs mCallBackus;
    private HomeCallBack homeCallBack;

    public FoodAdapter(ArrayList<FoodModel> FoodsArray, Context context, HomeCallBack mCallBackus) {
        this.FoodsArray = FoodsArray;
        this.context = context;
        this.homeCallBack = mCallBackus;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_adapter_layout, viewGroup, false);
        return new FoodAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodAdapter.ViewHolder viewHolder, final int i) {
        //viewHolder.FoodImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bag));
        viewHolder.FoodName.setText(FoodsArray.get(i).getName()+"\n"+FoodsArray.get(i).getCalorie());

        viewHolder.FoodImage.setImageDrawable(ContextCompat.getDrawable(context, FoodsArray.get(i).imagePath));

        viewHolder.FoodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog_item_quantity_update);
                // Set dialog title
                dialog.setTitle("Custom Dialog");
                final ImageView cartDecrement = dialog.findViewById(R.id.cart_decrement);
                ImageView cartIncrement = dialog.findViewById(R.id.cart_increment);
                ImageView closeDialog = dialog.findViewById(R.id.imageView_close_dialog_cart);
                TextView updateQtyDialog = dialog.findViewById(R.id.update_quantity_dialog);
                TextView viewCartDialog = dialog.findViewById(R.id.view_cart_button_dialog);
                final TextView quantity = dialog.findViewById(R.id.cart_Food_quantity_tv);
                quantity.setText(String.valueOf(0));
                final int[] cartCounter = {0};//{(arrayListImage.get(position).getStocks())};
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
                cartIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartDecrement.setEnabled(true);
                        cartCounter[0] += 1;
                        quantity.setText(String.valueOf(cartCounter[0]));


                    }
                });
                viewCartDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        context.startActivity(new Intent(context, CartActivity.class));
                    }
                });

                dialog.show();
                updateQtyDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, String.valueOf(cartCounter[0]) + "", Toast.LENGTH_SHORT).show();

                        // from these line of code we add items in cart
                        cartModel = new FoodImage();
                        cartModel.setFoodQuantity((cartCounter[0]));
                        cartModel.setFoodCalorie(arrayList.get(i).getCalorie());
                        cartModel.setFoodImage(arrayList.get(i).getImagePath());
                        cartModel.setFoodName(arrayList.get(i).getName());
                        cartModel.setTotalCalorie(cartCounter[0] *
                                Integer.parseInt(arrayList.get(i).getCalorie()));
                        Log.d("pos", String.valueOf(i));

                        cartModels.add(cartModel);

//
                        // from these lines of code we update badge count value
                        SetWeeklyMenu.cart_count = 0;
                        for (int i = 0; i < cartModels.size(); i++) {
                            for (int j = i + 1; j < cartModels.size(); j++) {
                                if (cartModels.get(i).getFoodImage().equals(cartModels.get(j).getFoodImage())) {
                                    cartModels.get(i).setFoodQuantity(cartModels.get(j).getFoodQuantity());
                                    cartModels.get(i).setTotalCalorie(cartModels.get(j).getTotalCalorie());
                                    cartModels.get(i).setFoodName(cartModels.get(j).getFoodName());
                                    //          cartModels.get(i).setImageIdSlide(cartModels.get(j).getImageIdSlide());
                                    cartModels.remove(j);
                                    j--;
                                    Log.d("remove", String.valueOf(cartModels.size()));

                                }
                            }
                        }
                        SetWeeklyMenu.cart_count = cartModels.size();

                        // from this interface method calling we show the updated value of cart cout in badge
                        homeCallBack.updateCartCount(context);
                        dialog.dismiss();
                    }

                });

                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetWeeklyMenu.cart_count = 0;
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


                        SetWeeklyMenu.cart_count = cartModels.size();
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
    // this interface creates for call the invalidateoptionmenu() for refresh the menu item
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