import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CreateJson{
  
  JSONArray jsArray = new JSONArray();
  JSONArray getArray(){return this.jsArray;}
  JSONObject createObj(String name, String calory, Boolean status,List<String> tags, String listType){
    JSONObject obj = new JSONObject();
    obj.put("name",name);
    obj.put("calory", calory);
    obj.put("status", status);
    obj.put("tags", tags);
    JSONObject item = new JSONObject();
    item.put(listType, obj);
    this.jsArray.add(item);
    return item;
  }
  
  void writeToFile(String fileName){
    
    try(FileWriter file = new FileWriter(fileName)){
      file.write(jsArray.toString());
      file.flush();
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }
/* this is for testing 
  public static void main(String[] args){
    CreateJson creator = new CreateJson();
    List<String> list = Arrays.asList("healthy", "good", "potein");
    creator.createObj("egg","120",true,list,"menu");
    creator.createObj("chicken","322",false,list,"menu");
    creator.writeToFile("myJSON.JSON");

  }*/
}
