package test.data.json;

import static org.junit.Assert.assertEquals;
import main.data.json.JSONSpecialValue;

import org.junit.Test;

public class TestJSONSpecialValue {

  @Test
  public void value() {
    assertEquals("null", JSONSpecialValue.Value.NULL, JSONSpecialValue.NULL.getValue());
    assertEquals("undefined", JSONSpecialValue.Value.UNDEFINED, JSONSpecialValue.UNDEFINED.getValue());
  }
  
  @Test
  public void stringification() {
    assertEquals("null", "null", JSONSpecialValue.NULL.toString());
    assertEquals("undefined", "undefined", JSONSpecialValue.UNDEFINED.toString());
  }
}
