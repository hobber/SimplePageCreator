package main.data.json;

import java.util.ArrayList;

public class JSONArray implements JSONObject{

  private ArrayList<Object> array = new ArrayList<Object>();
  
  public void add(Object child) {
    array.add(child);
  }
  
  @Override
  public int size() {
    return array.size();
  }
  
  public Object get(int index) {
    if(array.isEmpty())
      throw new IndexOutOfBoundsException("index " + index + " out of bounds of empty array");
    
    if(index < 0 || index >= array.size())
      throw new IndexOutOfBoundsException("index " + index + " out of bounds of [0, " + (array.size() - 1) + "]");
    
    return array.get(index);
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
      s = "[ ";
    else
      s = "[\n";
    
    int counter = 0;
    for(Object value : array) {
      if(isTopLevel == false || counter > 0)
        s += newIntend;
      
      if(value instanceof String)
        s += "\"" + value + "\"";
      else if(value instanceof JSONObject)
        s += ((JSONObject)value).toString(newIntend);
      else
        s += value;
      s += (counter++ == array.size() - 1 ? "" : ",") + "\n";
    }
    
    return s + intend + "]";
  }
}
