package test.data.json;

import static org.junit.Assert.assertEquals;
import main.data.json.JSONContainer;
import main.data.json.JSONSpecialValue;

import org.junit.Test;

public class TestJSONContainer {

  @Test
  public void addAndGetFromArray() {
    JSONContainer container = new JSONContainer();
    container.add("3", 3);
    container.add("123", "123");
    container.add("false", false);
    container.add("null", null);
    
    assertEquals("size must be 4", 4, container.size());
    assertEquals("element \"3\" is 3", 3, container.get("3"));
    assertEquals("element \"123\" is \"123\"", "123", container.get("123"));
    assertEquals("element \"false\" is false", false, container.get("false"));
    assertEquals("element \"null\" is null", null, container.get("null"));
  }

  @Test
  public void undefinedValue() {
    JSONContainer container = new JSONContainer();
    
    assertEquals("size must be 0", 0, container.size());
    assertEquals("undefined value", container.get("?"), JSONSpecialValue.UNDEFINED);
  }
  
  @Test
  public void stringification() {
    JSONContainer container = new JSONContainer();
    container.add("3", 3);
    container.add("123", "123");
    container.add("false", false);
    
    JSONContainer subContainer = new JSONContainer();
    subContainer.add("12.34", 12.34);
    subContainer.add("null", null);
    container.add("container", subContainer);
    
    assertEquals("size must be 4", 4, container.size());
    assertEquals("{ \"3\": 3,\n"
               + "  \"123\": \"123\",\n"
               + "  \"false\": false,\n"
               + "  \"container\": {\n"
               + "    \"12.34\": 12.34,\n"
               + "    \"null\": null\n"
               + "  }\n"
               + "}", container.toString());
  }
}
