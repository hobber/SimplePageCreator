package main.http;

import org.apache.http.client.methods.HttpPost;

public class HTTPPostRequest extends HTTPRequest {

  private HttpPost request;

  public HTTPPostRequest(String url) {
    request = new HttpPost(url);
  }
  
  @Override
  public HTTPResponse sendAndGetResponse() throws HTTPException {
    return sendAndGetResponse(request);
  } 
}
