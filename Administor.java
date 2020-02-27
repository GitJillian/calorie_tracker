import java.util.*;
import java.util.ArrayList;

public class Administor{

  private Calculator calculator;
  private CreateJson creator;
  private JsonReader reader;
  private ArrayList<Food> menu;
  private ArrayList<Food> menuToPublish;
  
  public Administor(){
      this.reader = new JsonReader();
      this.creator = new CreateJson();
      this.menu = new ArrayList<Food>();
      this.calculator = new Calculator();
      this.menuToPublish = new ArrayList<Food>();
  }
  
  public Administor(ArrayList<Food> foods){
      this.menu = foods;
      this.reader = new JsonReader();
      this.creator = new CreateJson();
      this.calculator = new Calculator();
      this.menuToPublish = new ArrayList<Food>();
  }

  // adding new item to local menu into food database(into json file)
  void addItemToMenu(){
      
    
  }
  //adding new item to menu into the release at client side
  void addItemToPublish()
  {
    
    
  }
  // these three to be finished by adding andriod handler
  void deleteItemFromMenu(){
    
  }
  public void viewFoodItems(){
    Iterator<Food> iterator = this.menu.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next().foodStats());
    }
  }
  // read food items from json file
  public void getFoodFromJson(){
    this.reader.readJson("./menu.JSON","menu");
    this.menu = this.reader.getList();
  }
  
   public static void main(String[] args){
    Administor admin = new Administor();
    admin.getFoodFromJson();
    admin.viewFoodItems();
  }
}