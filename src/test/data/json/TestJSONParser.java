package test.data.json;

import static org.junit.Assert.*;
import main.data.json.JSONContainer;
import main.data.json.JSONParseException;
import main.data.json.JSONParser;
import main.data.json.JSONParseException.ParseError;

import org.junit.Test;

public class TestJSONParser {

  @Test
  public void nullInput() {
    try {
      JSONParser.parse(null);
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INPUT_IS_NULL, e.getError());
      assertEquals("input is null", e.getMessage());
    }
  }
  
  @Test
  public void emptyInput() {
    try {
      JSONParser.parse("");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INPUT_IS_EMPTY, e.getError());
      assertEquals("input is empty", e.getMessage());
    }
  }
  
  @Test
  public void wrongStartingCharacter() {
    try {
      JSONParser.parse(" [] ");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("container must start with { but starts with [", e.getMessage());
    }
    
    try {
      JSONParser.parse("()");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("container must start with { but starts with (", e.getMessage());
    } 
    
    try {
      JSONParser.parse(" 1 ");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("container must start with { but starts with 1", e.getMessage());
    } 
  }

  @Test
  public void incompleteDefinition() {
    try {
      JSONParser.parse(" { ");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INCOMPLETE_DEFINITION, e.getError());
      assertEquals("incomplete container", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': [");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INCOMPLETE_DEFINITION, e.getError());
      assertEquals("incomplete container", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': 123 ");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INCOMPLETE_DEFINITION, e.getError());
      assertEquals("incomplete container", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': {}");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INCOMPLETE_DEFINITION, e.getError());
      assertEquals("incomplete container", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': 1");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INCOMPLETE_DEFINITION, e.getError());
      assertEquals("definition ends with a number", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': 'abc");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INCOMPLETE_DEFINITION, e.getError());
      assertEquals("incomplete string", e.getMessage());
    }
  }
  
  @Test
  public void invalidCharacter() {
    try {
      JSONParser.parse(" { } 1");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("invalid character after top level container (1)", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 1 }");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("value definition must start with ' or \" but starts with 1", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc' 123 }");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("value name must be followed by : but os followed by 1", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': truE }");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("invalid value", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': falsE }");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("invalid value", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': undeFined }");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("invalid value", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': nul }");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("invalid value", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { 'abc': 12a");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("invalid number", e.getMessage());
    }
    
    try {
      JSONParser.parse(" { '123': 1.2.3}");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("number contains several decimal points", e.getMessage()); 
    }
    
    try {
      JSONParser.parse(" { '123': 1.2 '456': 3.5}");
      fail("no exception thrown");
    } catch(JSONParseException e) {
      assertEquals(ParseError.INVALID_CHARACTER, e.getError());
      assertEquals("values must be separated by commas", e.getMessage());
    }
  }
  
  @Test
  public void validDefinition() {
    try {
      JSONContainer container = JSONParser.parse(
          "{ '123': \"abc\"," +
          "  '456' :+456," +
          "  '789':-7.89," +
          "  \"abc\" : true," +
          "  'def': false, " + 
          "  'ghi': null, " +
          "  'jkl': undefined, " +
          "  'mno': {" + 
          "    'sub': 123" +
          "  }," +
          "  'pqr':{" +
          "    'array':[" +
          "      'abc', " +
          "      123, " +
          "      null " +
          "    ]" +
          "  } "+
          "}");
      assertEquals("abc", container.get("123").toString());
    } catch(JSONParseException error) {
      fail("no exception expected: " + error.getMessage());
    }
  }
}
