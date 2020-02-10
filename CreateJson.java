import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.*;
public class CreateJson{
  JSONObject createObj(String name, String calory, String type,List<String> tags){
    JSONObject obj = new JSONObject();
    obj.put("name",name);
    obj.put("calory", calory);
    obj.put("type", type);
    obj.put("tags", tags);
    try(FileWriter file = new FileWriter("myJSON.JSON")){
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