/**
 * Auto Generated Java Class.
 */
public class User {
  float height;
  float weight;
  String name;
  Boolean sex; // true is for female, false is for male
  float BMI; //this is the bmi value for user
  public User(float h, float w, String s, Boolean gender){
    
    this.height = h;
    this.weight = w;
    this.name = s;
    this.sex = gender;
  }
  public float getBMI(){
    float bmi;
    bmi = 703 * weight / (height * height);
    return bmi;
  }

  public float getHeight(){
    return this.height;
  }
  
  public float getWeight(){
    return this.weight;
  }
  public String getName(){
    return this.name;  
  }
  public Boolean getSex(){
    return this.sex;
  }
  
  public void setBMI(){
    this.BMI = this.getBMI();
  }
  public void setName(String s){
    this.name = s;
  }
  public void setHeight(float h){
    this.height = h;
  }
  public void setWeight(float w){
    this.weight = w;
  }
  
  /* ADD YOUR CODE HERE */
  
}
