package main.data.json;

import main.data.json.JSONParseException.ParseError;

public class JSONParser {
  
  private static int index;
  private static String data;
  private static int dataLength;
  
  public static JSONContainer parse(String definition) throws JSONParseException {
    if(definition == null)
      throw new JSONParseException(ParseError.INPUT_IS_NULL, "input is null");
    
    index = 0;
    data = definition;
    dataLength = definition.length();
    
    if(searchNextNonWhiteSpaceIndex() == false)
      throw new JSONParseException(ParseError.INPUT_IS_EMPTY, "input is empty");
    
    JSONContainer container = parseContainer();

    if(searchNextNonWhiteSpaceIndex())
      throw new JSONParseException(ParseError.INVALID_CHARACTER, 
          "invalid character after top level container (" + data.charAt(index)+ ")");
    
    return container;
  }
  
  private static JSONContainer parseContainer() throws JSONParseException {
    JSONContainer container = new JSONContainer();
    char c = data.charAt(index);
    if(c != '{')
      throw new JSONParseException(ParseError.INVALID_CHARACTER, 
          "container must start with { but starts with " + c);
    index++;
    
    boolean valueWasCompletedByComma = true;
    while(true) {
      if(searchNextNonWhiteSpaceIndex() == false)
        throw new JSONParseException(ParseError.INCOMPLETE_DEFINITION, "incomplete container");
      
      c = data.charAt(index);
      if(c == '}') {
        index++;
        break; 
      }
      
      else if(c == ',') {
        valueWasCompletedByComma = true;
        index++;
      }
      
      else if(c == '\'' || c == '"') {
        if(valueWasCompletedByComma == false)
          throw new JSONParseException(ParseError.INVALID_CHARACTER, "values must be separated by commas");
        
        parseNamedValue(container);
        valueWasCompletedByComma = false;
      }
      
      else
        throw new JSONParseException(ParseError.INVALID_CHARACTER, 
            "value definition must start with ' or \" but starts with " + c);
    }
    
    return container;
  }
  
  private static JSONArray parseArray() throws JSONParseException {
    JSONArray array = new JSONArray();
    char c = data.charAt(index);
    if(c != '[')
      throw new JSONParseException(ParseError.INVALID_CHARACTER, 
          "container must start with [ but starts with " + c);
    index++;
    
    boolean valueWasCompletedByComma = true;
    while(true) {
      if(searchNextNonWhiteSpaceIndex() == false)
        throw new JSONParseException(ParseError.INCOMPLETE_DEFINITION, "incomplete container");
      
      c = data.charAt(index);
      if(c == ']') {
        index++;
        break; 
      }
      
      else if(c == ',') {
        valueWasCompletedByComma = true;
        index++;
      }
      
      else {
        if(valueWasCompletedByComma == false)
          throw new JSONParseException(ParseError.INVALID_CHARACTER, "values must be separated by commas");
        
        array.add(parseValue());
        valueWasCompletedByComma = false;
      }
    }
    
    return array;
  }
  
  private static boolean searchNextNonWhiteSpaceIndex() {
    while(index < dataLength) {
      if(Character.isWhitespace(data.charAt(index)) == false)
        return true;
      else
        index++;
    }
    
    return false;
  }
  
  private static void parseNamedValue(JSONContainer container) throws JSONParseException {
    String name = parseString();    

    if(searchNextNonWhiteSpaceIndex() == false || data.charAt(index) != ':')
      throw new JSONParseException(ParseError.INVALID_CHARACTER, 
          "value name must be followed by : but os followed by " + data.charAt(index));
    index++;
    
    if(searchNextNonWhiteSpaceIndex() == false)
      throw new JSONParseException(ParseError.INCOMPLETE_DEFINITION, "incomplete value definition");
    
    container.add(name, parseValue());
  }
  
  private static Object parseValue() throws JSONParseException {
    char c = data.charAt(index);
    if(c == '\'' || c == '"') {
      return parseString();
    }
    
    else if(c == '+' || c == '-' || Character.isDigit(c)) {
      Integer integerValue = parseInt();
      if(integerValue != null)
        return integerValue;
      
      Float floatValue = parseFloat();
      if(floatValue != null)
        return floatValue;
      
      throw new JSONParseException(ParseError.INVALID_CHARACTER, "invalid number");
    }
    
    else if(c == '{') {
      return parseContainer();
    }
    
    else if(c == '[') {
      return parseArray();
    }
    
    else if(c == 't' && parseParticularWord("true")) {
      return true;
    }
    
    else if(c == 'f' && parseParticularWord("false")) {
      return false;
    }
    
    else if(c == 'u' && parseParticularWord("undefined")) {
      return JSONSpecialValue.UNDEFINED;
    }
    
    else if(c == 'n' && parseParticularWord("null")) {
      return JSONSpecialValue.NULL;
    }
     
    throw new JSONParseException(ParseError.INVALID_CHARACTER, "invalid value");
  }
  
  private static Integer parseInt() throws JSONParseException {
    int end = index;
    char c = 'x';
    
    while(end < dataLength) {
      c = data.charAt(end);
      if(Character.isDigit(data.charAt(end)) || 
         (end == index && c == '+') ||
         (end == index && c == '-'))
        end++;
      
      else
        break;
    }
    
    if(end >= dataLength)
      throw new JSONParseException(ParseError.INCOMPLETE_DEFINITION, "definition ends with a number");
    
    if(Character.isWhitespace(c) || c == ',' || c == '}' || c == ']') {
      Integer value = Integer.parseInt(data.substring(index, end));
      index = end;
      return value;
    }
    
    return null;
  }
  
  private static Float parseFloat() throws JSONParseException {
    int end = index;
    char c = 'x';
    
    boolean firstDecimalPoint = true;
    while(end < dataLength) {
      c = data.charAt(end);
      if(Character.isDigit(data.charAt(end)) || 
         (end == index && c == '+') ||
         (end == index && c == '-'))
        end++;
      
      else if(c == '.') {
        if(firstDecimalPoint) {
          firstDecimalPoint = false;
          end++;
        }
        else
          throw new JSONParseException(ParseError.INVALID_CHARACTER, "number contains several decimal points");
      }
      
      else
        break;
    }
    
    if(end >= dataLength)
      throw new JSONParseException(ParseError.INCOMPLETE_DEFINITION, "definition ends with a number");
    
    if(Character.isWhitespace(c) || c == ',' || c == '}' || c == ']') {
      Float value = Float.parseFloat(data.substring(index, end));
      index = end;
      return value;
    }
    
    return null;
  }
  
  private static String parseString() throws JSONParseException {
    char startCharacter = data.charAt(index);
    int startIndex = index + 1;
    
    while(true) {
      index++;
      if(index >= dataLength)
        throw new JSONParseException(ParseError.INCOMPLETE_DEFINITION, "incomplete string");
      
      else if(data.charAt(index) == startCharacter)
        break;
    }
    
    String s = data.substring(startIndex, index);
    index++;
    return s;
  }
  
  private static boolean parseParticularWord(String word) {
    if(index + word.length() > dataLength)
      return false;
    
    for(int i = 0; i < word.length(); i++)
      if(word.charAt(i) != data.charAt(index + i))
        return false;
    
    index += word.length();
    return true;
  }
}
