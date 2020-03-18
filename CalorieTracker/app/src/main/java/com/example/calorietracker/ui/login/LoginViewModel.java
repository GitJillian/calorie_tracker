package com.example.calorietracker.ui.login;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.calorietracker.data.LoginRepository;
import com.example.calorietracker.data.Result;
import com.example.calorietracker.data.model.LoggedInUser;
import com.example.calorietracker.R;
import com.example.calorietracker.data.model.Student;
import com.example.calorietracker.helper.FileHelper;
import com.example.calorietracker.helper.JSONReaderFactory;
import com.example.calorietracker.helper.JsReader;
import org.json.JSONException;

import java.io.*;

public class LoginViewModel extends ViewModel {
    private String student_password ,student_frequency, student_name, student_gender, student_height, student_age, student_weight;
    private String username;
    private String password;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(Context context, String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);
        this.username = username;
        this.password = password;

        if (result instanceof Result.Success && checkValid(context,username, password)) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    private boolean checkValid(Context context,String username, String password)  {
        JSONReaderFactory readerFactory = new JSONReaderFactory();
        JsReader student_reader;
        boolean flag;

        try{
            File file = new File(FileHelper.getFileDir(context)+"/"+username+".JSON");
            student_reader = readerFactory.JSONReaderFactory(file);
            Student student;
            student =(Student)student_reader.getProduct();
            student_password = student.getPassword();
            flag = true;

            if(flag && password.equals(student_password)){
                flag = true;
            }
            else{
                flag = false;
            }

        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public void loginDataChanged(String username, String password) throws JSONException {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    //TODO: finish the password logic
    private boolean isPasswordValid(String password) {
        if (password.length() < 5)
        { return false;
    }return true;
}
}
