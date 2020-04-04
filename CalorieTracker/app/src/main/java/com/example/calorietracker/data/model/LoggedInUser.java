package com.example.calorietracker.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {
    private String displayName;
    private String email;
    private String password;

    public LoggedInUser(String email, String password){
        this.email = email;
        this.password = password;
    }

    //deprecated: we used to user admin, but right now it does not need admin log in

    public boolean isAdmin(String email, String password){
        boolean flag = false;
        if(email.equals("admin") && password.equals("admin")){
            flag = true;
        }else{
            flag= false;
        }
        return flag;
    }

    public String getDisplayName() {
        return displayName;
    }

}
