package main;

import main.http.HTTPException;
import main.http.HTTPGetRequest;
import main.http.HTTPResponse;



public class Main {

  public static void main(String[] args) {
    try {
      HTTPResponse response = new HTTPGetRequest("https://api.spotify.com/v1").sendAndGetResponse();
      System.out.println("response: " + response.getStatusCode() + ", " + response.getData());
    } catch (HTTPException e) {
      e.printStackTrace();
    }
  }
  
}
