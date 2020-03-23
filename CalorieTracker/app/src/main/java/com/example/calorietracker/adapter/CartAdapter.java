package com.example.calorietracker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.calorietracker.R;
import com.example.calorietracker.menu.FoodImage;
import com.example.calorietracker.menu.FoodModel;


import java.util.ArrayList;
import static com.example.calorietracker.ui.login.local.CartActivity.temparraylist;
//import static com.example.calorietracker.ui.login.local.CartActivity.grandTotal;
import static com.example.calorietracker.ui.login.local.CartActivity.grandTotalplus;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<FoodImage> cartModelArrayList;
    ArrayList<FoodModel> cartPublishArrayList;
    Context context;

    public CartAdapter(ArrayList<FoodImage> cartModelArrayList, ArrayList<FoodModel> modelArrayList, Context context) {
        this.context = context;
        this.cartModelArrayList = cartModelArrayList;
        this.cartPublishArrayList = modelArrayList;
    }


    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list, parent, false);
        return new CartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.ViewHolder holder, final int position) {

        holder.FoodCartPrice.setText(String.valueOf(cartModelArrayList.get(position).getTotalCalorie()));
        holder.FoodCartCode.setText(cartModelArrayList.get(position).getFoodName());
        holder.FoodCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getFoodQuantity()));

        RequestOptions requestOptions = new RequestOptions();

        Log.d("imageurl", String.valueOf(cartModelArrayList.get(position).getFoodImage()));
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(cartModelArrayList.get(position).getFoodImage()).into(holder.FoodCartImage);


        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (cartModelArrayList.size() == 1) {
                    FoodImage removed = cartModelArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());

                    grandTotalplus = 0;
                   // grandTotal.setText(grandTotalplus);
                }

                if (cartModelArrayList.size() > 0) {
                    FoodImage removed = cartModelArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());

                    grandTotalplus = 0;
                    for (int i = 0; i < temparraylist.size(); i++) {
                        grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCalorie();
                    }


                  //  grandTotal.setText(String.valueOf(grandTotalplus));

                } else {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // increment quantity and update quamtity and total cash
        holder.cartIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //total_cash=0;\

                grandTotalplus = 0;
                holder.cartDecrement.setEnabled(true);

                int cartUpdateCounter = (cartModelArrayList.get(position).getFoodQuantity());


                holder.cartIncrement.setEnabled(true);
                cartUpdateCounter += 1;

                cartModelArrayList.get(position).setFoodQuantity((cartUpdateCounter));
                int calorie = (Integer.parseInt(cartModelArrayList.get(position).getFoodCalorie()) * (cartModelArrayList.get(position).getFoodQuantity()));

                holder.FoodCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getFoodQuantity()));

                cartModelArrayList.get(position).setTotalCalorie(calorie);
                holder.FoodCartPrice.setText(String.valueOf(calorie));


                for (int i = 0; i < temparraylist.size(); i++) {
                    grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCalorie();
                }


               // grandTotal.setText(String.valueOf(grandTotalplus));

            }

        });

        // decrement quantity and update quamtity and total cash
        holder.cartDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //total_cash=0;
                grandTotalplus = 0;
                holder.cartIncrement.setEnabled(true);

                int cartUpdateCounter = (cartModelArrayList.get(position).getFoodQuantity());



                if (cartUpdateCounter == 1) {
                    holder.cartDecrement.setEnabled(false);
                    Toast.makeText(context, "quantity can't be zero", Toast.LENGTH_SHORT).show();
                } else {
                    holder.cartDecrement.setEnabled(true);
                    cartUpdateCounter -= 1;
                    cartModelArrayList.get(position).setFoodQuantity((cartUpdateCounter));
                    holder.FoodCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getFoodQuantity()));
                    int calorie = (Integer.parseInt(cartModelArrayList.get(position).getFoodCalorie()) * (cartModelArrayList.get(position).getFoodQuantity()));

                    cartModelArrayList.get(position).setTotalCalorie(calorie);
                    holder.FoodCartPrice.setText(String.valueOf(calorie));
                    for (int i = 0; i < temparraylist.size(); i++) {

                        grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCalorie();
                    }


                 //  grandTotal.setText(String.valueOf(grandTotalplus));

                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return cartModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView FoodCartImage, cartIncrement, cartDecrement, deleteItem;
        TextView FoodCartCode, FoodCartPrice, FoodCartQuantity;

        @SuppressLint("ResourceType")
        public ViewHolder(View itemView) {
            super(itemView);
            FoodCartImage = itemView.findViewById(R.id.list_image_cart);
            deleteItem = itemView.findViewById(R.id.delete_item_from_cart);
            FoodCartCode = itemView.findViewById(R.id.Food_cart_code);
            FoodCartPrice = itemView.findViewById(R.id.Food_cart_calorie);
            FoodCartQuantity = itemView.findViewById(R.id.cart_Food_quantity_tv);
            cartDecrement = itemView.findViewById(R.id.cart_decrement);
            cartIncrement = itemView.findViewById(R.id.cart_increment);
        }
    }
}
