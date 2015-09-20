package main;

import main.http.HTTPException;
import main.http.HTTPGetRequest;
import main.http.HTTPPostRequest;
import main.http.HTTPResponse;
import main.http.HTTPServer;

public class Main {

  public static void main(String[] args) {
    try {
      new HTTPServer(11011);
      
      HTTPResponse response = new HTTPGetRequest("https://api.spotify.com/v1").sendAndGetResponse();
      System.out.println("response: " + response.getStatusCode() + ", " + response.getJSONData());
      
      response = new HTTPPostRequest("http://localhost:11011/terminate").sendAndGetResponse();
      System.out.println("response: " + response.getStatusCode() + ", " + response.getStringData());
    } catch (HTTPException e) {
      e.printStackTrace();
    }
  }
  
}
