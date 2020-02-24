import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader{
    JSONParser parser;
    FileReader reader;
    JSONArray itemArray;
    ArrayList<Food> foodList;

    JSONArray getArray(){
      return this.itemArray;
    }
    
    void readJson(String fileName, String listType){
      JSONParser jsonParser = new JSONParser();
      try(FileReader reader = new FileReader(fileName))
      {
        
        Object obj = jsonParser.parse(reader);
        JSONArray jsonList = (JSONArray) obj;
            //Iterate over the whole array
            jsonList.forEach( item -> parseJsonObject( (JSONObject) item, listType ) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
      void parseJsonObject(JSONObject item, String listType) 
    {
        //Get employee object within list
        JSONObject foodObject = (JSONObject) item.get(listType);
         
        //Get item name
        String name = (String) foodObject.get("name");    
        System.out.println(name);
         
        //Get calory
        String calory = (String) foodObject.get("calory");  
        System.out.println(calory);
        Float caloryFloat = Float.parseFloat(calory);
         
        //Get status
        Boolean status = (Boolean) foodObject.get("status");  
        
        List<String> tags = (List<String>) foodObject.get("tags");
        this.foodList.add(new Food(name, caloryFloat, status, tags));
    }

  }
