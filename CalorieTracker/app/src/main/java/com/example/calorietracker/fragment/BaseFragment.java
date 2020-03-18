package com.example.calorietracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.calorietracker.R;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.example.calorietracker.R.*;


public class BaseFragment extends Fragment {

    @NotNull
    public static BaseFragment newInstance(String info) {

        Bundle args = new Bundle();

        BaseFragment fragment = new BaseFragment();

        args.putString("info", info);

        fragment.setArguments(args);

        return fragment;

    }



    @Nullable

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(layout.fragment_base, null);

        TextView tvInfo = (TextView) view.findViewById(id.textView);

        tvInfo.setText(getArguments().getString("info"));

        tvInfo.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Snackbar.make(v, "Don't click me.please!.", Snackbar.LENGTH_SHORT).show();

            }

        });

        return view;

    }

}