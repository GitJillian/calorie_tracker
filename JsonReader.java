
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.*;
import java.io.File; 

public class JsonReader{
    JSONParser parser;
    FileReader reader;
    JSONArray itemArray;
    
    JSONArray getArray(){
      return this.itemArray;
    }
    void readJson(){
      try
      {
        Object item = parser.parse(new FileReader("myJSON.json"));
        JSONObject jsonObj = (JSONObject) item;
        String name = (String) jsonObj.get("name");
        String calory = (String) jsonObj.get("calory");
        this.itemArray = (JSONArray) jsonObj.get("type");
        Iterator<String> iterator = itemArray.iterator();
        while(iterator.hasNext()){
          System.out.println("Type: " + iterator.next());
        }
      }
      catch(FileNotFoundException e){e.printStackTrace();}
      catch(IOException e){e.printStackTrace();}
      catch(ParseException e){e.printStackTrace();}
      catch(Exception e){e.printStackTrace();}
    }
    
    public static void main(String[] args){
      JsonReader reader = new JsonReader();
      reader.readJson();
      


      }

  }
