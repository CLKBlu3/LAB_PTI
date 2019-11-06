package mypackage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.Iterator;

public class JsonWriter{

	public JsonWriter(){
	}
	
	public void addRental(Car rentedCar){
		JSONParser parser = new JSONParser();
		JSONObject obj;
		try{	 	
			obj = (JSONObject)parser.parse(new FileReader("test.json"));
		}catch(Exception e){
			obj = createEmptyFile();	
		}
		
		try{
            JSONArray vehicles = (JSONArray) obj.get("rentals");
		    vehicles.add(rentedCar.toJSON());
		    FileWriter file = new FileWriter("test.json");
		    file.write(obj.toJSONString());
                    file.flush();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private JSONObject createEmptyFile(){
		JSONObject obj = new JSONObject();
		JSONArray list = new JSONArray();
		obj.put("rentals", list);
		return obj;		
	}

}

