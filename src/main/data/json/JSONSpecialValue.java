package main.data.json;

public class JSONSpecialValue {

  public enum Value {
    UNDEFINED,
    NULL
  };
  
  public static final JSONSpecialValue UNDEFINED = new JSONSpecialValue(Value.UNDEFINED);
  public static final JSONSpecialValue NULL = new JSONSpecialValue(Value.NULL);
  
  private Value value;
  
  private JSONSpecialValue(Value value) {
    this.value = value;
  }
  
  public Value getValue() {
    return value;
  }
  
  @Override
  public String toString() {
    if(value == Value.UNDEFINED)
      return "undefined";
    
    return "null";
  }
}
