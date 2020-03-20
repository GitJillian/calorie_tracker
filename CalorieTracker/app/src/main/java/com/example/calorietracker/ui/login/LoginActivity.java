package com.example.calorietracker.ui.login;
import android.annotation.SuppressLint;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import com.example.calorietracker.helper.StudentReader;
import com.example.calorietracker.ui.home.HomeActivity;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calorietracker.R;
import com.example.calorietracker.ui.login.google.NewGoogleAccount;
import com.example.calorietracker.ui.login.google.GoogleAccountInfo;
import com.example.calorietracker.ui.login.local.AdminActivity;
import com.example.calorietracker.ui.login.local.NewLocalAccount;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Failure" ;
    private SignInButton signInButton;
    private LoginViewModel loginViewModel;
    private GoogleSignInClient mGoogleSignInAccount;
    private EditText usernameEditText, passwordEditText;
    private String username, password;
    private String student_password ,student_frequency, student_name, student_gender, student_height, student_age, student_weight;
    private int RC_SIGN_IN = 0;
    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.create_user);
        final Button adminButton = findViewById(R.id.admin_login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());

                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                boolean flag = false;
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);

                    if (loginResult.getError() != null) {
                        showLoginFailed(loginResult.getError());
                    }
                    if (loginResult.getSuccess() != null) {
                        try {
                            updateUiWithUser(loginResult.getSuccess());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        flag =true;
                     }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                if(flag){finish();}
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(LoginActivity.this,usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(LoginActivity.this,usernameEditText.getText().toString(), passwordEditText.getText().toString());

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NewLocalAccount.class);
                startActivity(intent);
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        this.mGoogleSignInAccount = GoogleSignIn.getClient(this, gso);
       // this.users.add(this.mGoogleSignInAccount);
        this.signInButton = findViewById(R.id.sign_in_button);
        this.signInButton.setSize(SignInButton.SIZE_STANDARD);


        this.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

    }

@Override
    public void onStart() {
        super.onStart();
    }

    private void signIn() {
        Intent signInIntent = this.mGoogleSignInAccount.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUiWithUser(LoggedInUserView model) throws FileNotFoundException {
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        JsReader student_reader;//FACTORY METHOD PATTERN
        JSONReaderFactory factory = new JSONReaderFactory();
        boolean flag;

        String path = FileHelper.getFileDir(LoginActivity.this)+"/"+username+".JSON";
        String path_user = "/"+username+".JSON";
        File file = new File(path);

        try {
            student_reader = factory.JSONReaderFactory(file);
            Student student = (Student)student_reader.getProduct();//RETURN STUDENT PRODUCT
            student_name =student.getName();
            student_password = student.getPassword();

            flag = true;

            if(!flag){
                Toast.makeText(LoginActivity.this, "the account does not exist", Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            if(flag && password.equals(student_password)){

                intent.putExtra("path",path_user);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this,"User name and password not match",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "User does not exist",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(LoginActivity.this, errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            assert account != null;

            String email =account.getEmail();
            String str = email.split("@")[0].replace(' ','_');
            String path = FileHelper.getFileDir(LoginActivity.this)+"/"+str+".JSON";
            File file = new File(path);
            if(FileHelper.userExists(LoginActivity.this,str)){
                Intent intent = new Intent(LoginActivity.this, GoogleAccountInfo.class);
                 startActivity(intent);
                 Toast.makeText(LoginActivity.this,"Log in with existing user", Toast.LENGTH_LONG).show();
                }
                else{
                 Intent intent = new Intent(LoginActivity.this, NewGoogleAccount.class);
                 startActivity(intent);
                 Toast.makeText(LoginActivity.this,"Log in with new user", Toast.LENGTH_LONG).show();
             }
            }
        catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
