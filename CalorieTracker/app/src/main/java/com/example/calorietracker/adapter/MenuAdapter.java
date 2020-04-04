package com.example.calorietracker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.ArrayList;

//grandTotal is the textview showing the total calorie in the cart
import static com.example.calorietracker.ui.home.SelfSelectCartActivity.grandTotal;

//temparry list is the list of food items in the cart
import static com.example.calorietracker.ui.home.SelfSelectCartActivity.temparraylist;

//grandtotalplus is the value of total calorie

import static com.example.calorietracker.ui.home.SelfSelectCartActivity.grandTotalplus;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    ArrayList<FoodImage> cartModelArrayList;
    Context context;

    public MenuAdapter(ArrayList<FoodImage> cartModelArrayList, Context context) {
        this.context = context;
        this.cartModelArrayList = cartModelArrayList;

    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list, parent, false);
        return new MenuAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MenuAdapter.ViewHolder holder, final int position) {
        //for each items in the cart, we should see its total calorie, quantity, the its name

        holder.FoodCartCalorie.setText(cartModelArrayList.get(position).getTotalCalorie()+" cals");
        holder.FoodCartCode.setText(cartModelArrayList.get(position).getFoodName());
        holder.FoodCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getFoodQuantity()));

        //load food pictures
        RequestOptions requestOptions = new RequestOptions();

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(cartModelArrayList.get(position).getFoodImage()).into(holder.FoodCartImage);

        //delete item from cart
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                holder.deleteItem.setEnabled(true);

                if (cartModelArrayList.size() == 1) {
                    cartModelArrayList.remove(position);
                    notifyItemChanged(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());

                    grandTotalplus = 0;
                    grandTotal.setText("Total Calorie: "+grandTotalplus+" cals");
                }

                if (cartModelArrayList.size() > 0) {
                    cartModelArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());

                    grandTotalplus = 0;
                    for (int i = 0; i < temparraylist.size(); i++) {
                        grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCalorie();
                    }
                    grandTotal.setText("Total Calorie: "+grandTotalplus+" cals");
                }

            }
        });
/// click this and increment the current food item
        holder.cartIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grandTotalplus = 0;
                holder.cartDecrement.setEnabled(true);

                int cartUpdateCounter = (cartModelArrayList.get(position).getFoodQuantity());


                holder.cartIncrement.setEnabled(true);
                cartUpdateCounter += 1;

                cartModelArrayList.get(position).setFoodQuantity((cartUpdateCounter));
                int calorie = (Integer.parseInt(cartModelArrayList.get(position).getFoodCalorie()) * (cartModelArrayList.get(position).getFoodQuantity()));

                holder.FoodCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getFoodQuantity()));

                cartModelArrayList.get(position).setTotalCalorie(calorie);
                holder.FoodCartCalorie.setText(String.valueOf(calorie) +" cals");


                for (int i = 0; i < temparraylist.size(); i++) {
                    grandTotalplus += temparraylist.get(i).getTotalCalorie();
                }

                grandTotal.setText("Total Calorie: "+grandTotalplus+" cals");

            }

        });
        //decrement the food item for each positon

        holder.cartDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    holder.FoodCartCalorie.setText(String.valueOf(calorie)+ " cals");
                    for (int i = 0; i < temparraylist.size(); i++) {

                        grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalCalorie();
                    }
                    grandTotal.setText("Total Calorie: "+grandTotalplus+" cals");

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
        TextView FoodCartCode, FoodCartCalorie, FoodCartQuantity;

        @SuppressLint("ResourceType")
        public ViewHolder(View itemView) {
            super(itemView);

            //renders each food item view in the cart according to its food type
            FoodCartImage = itemView.findViewById(R.id.list_image_cart);
            deleteItem = itemView.findViewById(R.id.delete_item_from_cart);
            FoodCartCode = itemView.findViewById(R.id.Food_cart_code);
            FoodCartCalorie = itemView.findViewById(R.id.Food_cart_calorie);
            FoodCartQuantity = itemView.findViewById(R.id.cart_Food_quantity_tv);
            cartDecrement = itemView.findViewById(R.id.cart_decrement);
            cartIncrement = itemView.findViewById(R.id.cart_increment);
        }
    }
}
