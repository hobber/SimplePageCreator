package main.data.json;

public class JSONParseException extends Exception {

  public static enum ParseError {
    INPUT_IS_NULL,
    INPUT_IS_EMPTY,
    INVALID_CHARACTER,
    INCOMPLETE_DEFINITION
  }
  
  private static final long serialVersionUID = -4410291325282254524L;
  
  private ParseError error;
  
  public JSONParseException(ParseError error, String message) {
    super(message);
    this.error = error;
  }
  
  public ParseError getError() {
    return error;
  }

}
