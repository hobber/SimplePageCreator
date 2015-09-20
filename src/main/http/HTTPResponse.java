package main.http;

import main.data.json.JSONContainer;

public class HTTPResponse {

  public static final int CODE_OK = 200;
  
  int statusCode;
  JSONContainer jsonData = null;
  String stringData = null;
  
  public HTTPResponse(int statusCode, JSONContainer data) {
    this.statusCode = statusCode;
    this.jsonData = data;
  }
  
  public HTTPResponse(int statusCode, String data) {
    this.statusCode = statusCode;
    this.stringData = data;
  }
  
  public boolean wasSuccess() {
    return statusCode == CODE_OK;
  }
  
  public int getStatusCode() {
    return statusCode; 
  }
  
  public boolean hasJSONData() {
    return jsonData != null;
  }
  
  public JSONContainer getJSONData() {
    return jsonData;
  }
  
  public String getStringData() {
    return stringData;
  }
}
