
import java.util.*;
public class Food{
  
  private String name;
  private Float calorie;
  private Boolean status;
  private List<String> tags;
  private String image;

  public Food(String name, Float calorie, Boolean status, List<String> tags, String image){
    this.name = name;
    this.calorie = calorie;
    this.status = status;
    this.tags = tags;
    this.image = image;
  }
  
  public void SetFood(String name, Float calorie, Boolean status,List<String> tags, String image){
    this.name = name;
    this.calorie = calorie;
    this.status = status;
    this.tags = tags;
    this.image = image;
  }
  
  public String getName(){
    return this.name;
  }
  
  public Float getCalorie(){
    return this.calorie;
  }
  
  public Boolean getStatus(){
    return this.status;
  }
  
  public List<String> getTags(){
    return this.tags;
  }
  
  public String getImage() {
    return this.image;
  }
  
  public String foodStats(){
    String s = String.valueOf(this.status);
    return "name: " + this.name + "\n" + "calorie: " + this.calorie + "\nstatus:" + s +"\ntags:"+ this.tags.toString() +"\n"; 
    }
  }
  
