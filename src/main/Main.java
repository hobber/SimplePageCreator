package main;

import main.data.json.JSONContainer;
import main.data.json.JSONParseException;
import main.data.json.JSONParser;
import main.data.json.JSONParseException.ParseError;


public class Main {

  public static void main(String[] args) {
    try {
      JSONParser.parse(null);
      System.err.println("test 1 failed");
    } catch(JSONParseException error) {
      if(error.getError() != ParseError.INPUT_IS_NULL || error.getMessage().equals("input is null") == false)
        System.err.println("test 1 failed");
    }
    
    try {
      JSONParser.parse("");
      System.err.println("test 2 failed");
    } catch(JSONParseException error) {
      if(error.getError() != ParseError.INPUT_IS_EMPTY || error.getMessage().equals("input is empty") == false)
        System.err.println("test 2 failed");
    }
    
    try {
      JSONParser.parse(" [] ");
      System.err.println("test 3 failed");
    } catch(JSONParseException error) {
      if(error.getMessage().equals("container must start with { but starts with [") == false)
        System.err.println("test 3 failed");
    }
    
    try {
      JSONParser.parse("   {   ");
      System.err.println("test 4 failed");
    } catch(JSONParseException error) {
      if(error.getMessage().equals("incomplete container") == false)
        System.err.println("test 4 failed");
    }
    
    try {
      JSONParser.parse("   {}   ");
    } catch(JSONParseException error) {
      System.err.println(error.getMessage());
      System.err.println("test 5 failed");
    }
    
    try {
      JSONParser.parse(" { } 1");
      System.err.println("test 6 failed");
    } catch(JSONParseException error) {
      if(error.getMessage().equals("invalid character after top level container (1)") == false)
        System.err.println("test 6 failed");
    }
    
    try {
      JSONParser.parse(" { 1 }");
      System.err.println("test 7 failed");
    } catch(JSONParseException error) {
      if(error.getMessage().equals("value definition must start with ' or \" but starts with 1") == false)
        System.err.println("test 7 failed");
    }
    
    try {
      JSONParser.parse(" { '123': 1");
      System.err.println("test 8 failed");
    } catch(JSONParseException error) {
      if(error.getMessage().equals("definition ends with a number") == false)
        System.err.println("test 8 failed");
    }
    
    try {
      JSONParser.parse(" { '123': 12a}");
      System.err.println("test 9 failed");
    } catch(JSONParseException error) {
      if(error.getMessage().equals("invalid number") == false)
        System.err.println("test 9 failed");
    }
    
    try {
      JSONParser.parse(" { '123': 1.2.3}");
      System.err.println("test 10 failed");
    } catch(JSONParseException error) {
      if(error.getMessage().equals("number contains several decimal points") == false)
        System.err.println("test 10 failed");
    }
    
    try {
      JSONParser.parse(" { '123': 1.2 '456': 3.5}");
      System.err.println("test 11 failed");
    } catch(JSONParseException error) {
      if(error.getMessage().equals("values must be separated by commas") == false)
        System.err.println("test 11 failed");
    }
    
    try {
      JSONContainer container = JSONParser.parse(
          " { '123': \"abc\", '456' :+456, '789':-7.89, 'abc' : true,"
          + "'def': false, 'ghi': null, 'jkl': undefined, 'mno': {'sub': 123}, 'pqr':{'array':['abc', 123, null]}}");
      System.out.println(container);
      
    } catch(JSONParseException error) {
      System.err.println(error.getMessage());
      System.err.println("test 12 failed");
    }
  }
  
}
