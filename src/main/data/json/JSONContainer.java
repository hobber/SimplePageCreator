package main.data.json;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class JSONContainer implements JSONObject {

  private LinkedHashMap<String, Object> values = new LinkedHashMap<String, Object>();
  
  public void add(String name, Object value) {
    values.put(name, value);
  }
  
  @Override
  public int size() {
    return values.size();
  }

  public Object get(String name) {
    if(values.containsKey(name) == false)
      return JSONSpecialValue.UNDEFINED;
    return values.get(name);
  }
  
  @Override
  public String toString() {
    return toString("");
  }
  
  @Override
  public String toString(String intend) {
    String newIntend = intend + "  ";
    String s;
    boolean isTopLevel = intend.equals(""); 
    if(isTopLevel)
      s = "{ ";
    else
      s = "{\n";
    
    int counter = 0;
    for(Entry<String, Object> entry : values.entrySet()) {
      if(isTopLevel == false || counter > 0)
        s += newIntend;
      Object value = entry.getValue(); 
      s += "\"" + entry.getKey() + "\": ";
      
      if(value instanceof String)
        s += "\"" + value + "\"";
      else if(value instanceof JSONObject)
        s += ((JSONObject)value).toString(newIntend);
      else
        s += value;
      s += (counter++ == values.size() - 1 ? "" : ",") + "\n";
    }
    
    return s + intend + "}"; 
  }
}
