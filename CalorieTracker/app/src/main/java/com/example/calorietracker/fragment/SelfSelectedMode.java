package com.example.calorietracker.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.calorietracker.R;
import com.example.calorietracker.ui.home.SelfSelectView;
import com.example.calorietracker.ui.login.local.EditProfile;
import com.github.siyamed.shapeimageview.CircularImageView;
import org.jetbrains.annotations.Nullable;

public class SelfSelectedMode extends Fragment {
    ImageView Breakfast, Lunch, Dinner;
    ImageView BreakfastButton, LunchButton, DinnerButton;
    TextView BreakfastShow, LunchShow, DinnerShow;
    private CircularImageView chooseMenu;
    public static SelfSelectedMode newInstance(String path,String date) {

        Bundle args = new Bundle();
        SelfSelectedMode fragment = new SelfSelectedMode();
        args.putString("date", date);
        args.putString("path",path);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.self_ui, null);

        Breakfast = view.findViewById(R.id.self_breakfast);
        BreakfastShow = view.findViewById(R.id.self_breakfast_show);
        Lunch = view.findViewById(R.id.self_lunch);
        LunchShow = view.findViewById(R.id.self_lunch_show);
        Dinner = view.findViewById(R.id.self_dinner);
        DinnerShow = view.findViewById(R.id.self_dinner_show);
        chooseMenu = view.findViewById(R.id.image_view);
        BreakfastButton = view.findViewById(R.id.button_breakfast);
        LunchButton = view.findViewById(R.id.button_lunch);
        DinnerButton = view.findViewById(R.id.button_dinner);
        Intent intent = new Intent(getActivity(), SelfSelectView.class);
        intent.putExtra("date",getArguments().getString("date"));
        intent.putExtra("path",getArguments().getString("path"));
        Uri uri = Uri.parse("android.resource://com.example.calorietracker/drawable/icon_select_2");
        Glide.with(this).load(String.valueOf(uri)).into(chooseMenu);
        BreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","breakfast");
                Toast.makeText(getContext(),"Breakfast",Toast.LENGTH_SHORT).show();
                startActivity(intent);

                }
            }
        );
        LunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","lunch");
                Toast.makeText(getContext(),"Lunch",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                 }
            }
        );
        DinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","dinner");
                Toast.makeText(getContext(),"Dinner",Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
            }
        );


        return view;

    }

}



