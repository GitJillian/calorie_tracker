/**
 * Auto Generated Java Class.
 */
package com.example.calorietracker.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private float height;
    private int weight;
    private String name;
    private Boolean sex; // true is for female, false is for male
    private float BMI; //this is the bmi value for user
    private String frequency;//frequency of sports
    private int age;
    private float BMR;
    private String email;
    private String gender;
    private String password;

    public Student(String s, String gender, int age, String frequency, float h, int w, String password) {
        this.height = h;
        this.weight = w;
        this.name = s;
        this.gender = gender;
        this.age = age;
        this.frequency = frequency;
        this.password = password;
    }

    public Student(float h, int w, String s, String gender, int age, String frequency, String email) {

        this.height = h;
        this.weight = w;
        this.name = s;
        this.gender = gender;
        this.age = age;
        this.frequency = frequency;
        this.email = email;
    }

    //new_student = new Student(height, weight, name, gender, age, frequency);
    public Student(Parcel in) {
        this.height = in.readFloat();
        this.weight = in.readInt();
        this.name = in.readString();
        this.gender = in.readString();
        this.age = in.readInt();
        this.frequency = in.readString();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String toJsonString() {
        return "{\"Name\": \"" + this.name + "\",\n" +
                "\"Gender\":\"" + this.sex + "\",\n" +
                "\"Age\":" + this.age + ",\n" +
                "\"Height\":" + this.height + ",\n" +
                "\"Weight\":" + this.height + ",\n" +
                "\"Frequency\":\"" + this.frequency + "\"}";
    }

    public String getFrequency() {
        return this.frequency;
    }

    public float getBMI() {
        this.BMI = this.weight / (this.height * this.height);
        this.BMI = ((float) (Math.round(this.BMI * 100)) / 100);
        return this.BMI;
    }

    public float[] getBMRPropotion() {
        float bmr = this.getBMR();
        final float[] arr = new float[3];
        if (this.frequency.equals("rarely") || this.frequency.equals("sometimes") || this.frequency.equals("medium")) {
            arr[0] = ((float) (Math.round(bmr * 100 * 0.3)) / 100);
            arr[1] = ((float) (Math.round(bmr * 100 * 0.4)) / 100);
            arr[2] = ((float) (Math.round(bmr * 100 * 0.3)) / 100);
        } else {

            arr[0] = ((float) (Math.round(bmr * 100 * 0.4)) / 100);
            arr[1] = ((float) (Math.round(bmr * 100 * 0.4)) / 100);
            arr[2] = ((float) (Math.round(bmr * 100 * 0.2)) / 100);
        }
        return arr;
    }

    public String getEmail() {
        return this.email;
    }

    public String getGender() {
        return this.gender;
    }

    public float getBMR() {
        float bmr = 1f;
        float value;
        switch (this.getFrequency().toLowerCase()) {
            case "rarely":
                value = 1.2f;
                break;
            case "sometimes":
                value = 1.375f;
                break;
            case "medium":
                value = 1.55f;
                break;
            case "often":
                value = 1.725f;
                break;
            case "always":
                value = 1.9f;
                break;
            default:
                value = 1;
        }

        if (this.gender.equals("Female")) {
            bmr = 655.1f + (this.weight * 9.6f + 1.8f * this.height) - 4.7f * this.age;
        } else {
            bmr = 66.47f + (this.weight * 13.7f + 5f * this.height) - 6.8f * this.age;
        }
        this.BMR = ((float) (Math.round(bmr * 100 * value)) / 100);
        return this.BMR;
    }

    public int getAge() {
        return this.age;
    }

    public float getHeight() {

        return this.height;
    }

    public int getWeight() {
        return this.weight;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getSex() {
        return this.sex;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public void setName(String s) {
        this.name = s;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public void setWeight(int w) {
        this.weight = w;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gender);
        dest.writeString(this.name);
        dest.writeString(this.frequency);
        dest.writeInt(this.weight);
        dest.writeFloat(this.height);
        dest.writeFloat(this.BMI);
        dest.writeFloat(this.BMR);
        dest.writeInt(this.age);
    }

}
