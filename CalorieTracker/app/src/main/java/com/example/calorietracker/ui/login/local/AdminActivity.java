package com.example.calorietracker.ui.login.local;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.calorietracker.R;

public class AdminActivity extends AppCompatActivity {


    //deprecated as Admin is no longer used
    EditText name, password;
    String admin_name, admin_password;
    Button signInButton;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_admin);
        setTitle("Admin Sign In");
        name = findViewById(R.id.admin_name);
        password = findViewById(R.id.admin_password);
        signInButton = findViewById(R.id.admin_sign);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = checkValid(name, password);
                if(flag){
                Intent intent = new Intent(AdminActivity.this, SetWeeklyMenu.class);
                startActivity(intent);
                }
                else{
                    Toast.makeText(AdminActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean checkValid(EditText name, EditText password){
        admin_name = name.getText().toString();
        admin_password = password.getText().toString();
        return admin_name.equals("admin") && admin_password.equals("password");
    }
}
