package main.http;

import main.data.json.JSONContainer;

public class HTTPResponse {

  int statusCode;
  JSONContainer data;
  
  public HTTPResponse(int statusCode, JSONContainer data) {
    this.statusCode = statusCode;
    this.data = data;
  }
  
  public int getStatusCode() {
    return statusCode; 
  }
  
  public JSONContainer getData() {
    return data;
  }
}
