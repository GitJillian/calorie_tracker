import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;
public class CreateJson{
  JSONObject createObj(String name, String calory, String type,List<String> tags){
    JSONObject obj = new JSONObject();
    obj.put("name",name);
    obj.put("calory", calory);
    obj.put("type", type);
    obj.put("tags", tags);
    try(FileWriter file = new FileWriter("myJSON.json")){
      file.write(obj.toString());
      file.flush();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    System.out.println(obj);
    return obj;
  }

  public static void main(String[] args){
    CreateJson obj = new CreateJson();
    List<String> list = Arrays.asList("one", "two", "three");
    obj.createObj("egg","120","healthy",list);
    System.out.println(obj);

  }
}
