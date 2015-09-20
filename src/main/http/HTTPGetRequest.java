package main.http;

import org.apache.http.client.methods.HttpGet;

public class HTTPGetRequest extends HTTPRequest {

  private HttpGet request;
  
  public HTTPGetRequest(String url) {
    request = new HttpGet(url);
  }
  
  @Override
  public HTTPResponse sendAndGetResponse() throws HTTPException {
    return sendAndGetResponse(request);
  }
}
