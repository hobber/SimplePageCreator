package test.data.json;

import static org.junit.Assert.*;
import main.data.json.JSONArray;

import org.junit.Test;

public class TestJSONArray {

  @Test
  public void addAndGetFromArray() {
    JSONArray array = new JSONArray();
    array.add(3);
    array.add("123");
    array.add(false);
    array.add(null);
    
    assertEquals("size must be 4", 4, array.size());
    assertEquals("first element is 3", 3, array.get(0));
    assertEquals("second element is \"123\"", "123", array.get(1));
    assertEquals("third element is false", false, array.get(2));
    assertEquals("fourth element is null", null, array.get(3));
  }
  
  @Test
  public void invalidIndexForEmptyArray() {
    JSONArray array = new JSONArray();
    
    assertEquals("size must be 0", 0, array.size());
    try {
      array.get(0);
      fail("should throw exception");
    } catch (IndexOutOfBoundsException e) {
      assertEquals("index 0 out of bounds of empty array", e.getMessage());
    }
  }
  
  @Test
  public void invalidIndexForFilledArray() {
    JSONArray array = new JSONArray();
    array.add(3);
    array.add("123");
    array.add(false);
    array.add(null);
    
    assertEquals("size must be 4", 4, array.size());
    try {
      array.get(-1);
      fail("should throw exception");
    } catch (IndexOutOfBoundsException e) {
      assertEquals("index -1 out of bounds of [0, 3]", e.getMessage());
    }
    try {
      array.get(4);
      fail("should throw exception");
    } catch (IndexOutOfBoundsException e) {
      assertEquals("index 4 out of bounds of [0, 3]", e.getMessage());
    }
  }
  
  @Test
  public void stringification() {
    JSONArray array = new JSONArray();
    array.add(3);
    array.add("123");
    array.add(false);
    JSONArray subArray = new JSONArray();
    subArray.add(12.34);
    subArray.add(null);
    array.add(subArray);
    
    assertEquals("size must be 4", 4, array.size());
    assertEquals("[ 3,\n"
               + "  \"123\",\n"
               + "  false,\n"
               + "  [\n"
               + "    12.34,\n"
               + "    null\n"
               + "  ]\n"
               + "]", array.toString());
  }

}
