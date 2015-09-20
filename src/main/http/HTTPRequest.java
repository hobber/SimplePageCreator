package main.http;

import java.io.IOException;

import main.data.json.JSONContainer;
import main.data.json.JSONParseException;
import main.data.json.JSONParser;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public abstract class HTTPRequest {

  private static HttpClient client = HttpClientBuilder.create().build();
  
  protected static HTTPResponse sendAndGetResponse(HttpGet request) throws HTTPException {
    try {
      HttpResponse response = client.execute(request);
      int statusCode = response.getStatusLine().getStatusCode();
      
      Header contentType = response.getFirstHeader("Content-Type");
      if(contentType == null)
        throw new HTTPException("unknown response type");
      
      if(contentType.getValue().equals("application/json")) {
        try {
          JSONContainer data = JSONParser.parse(EntityUtils.toString(response.getEntity()));
          return new HTTPResponse(statusCode, data);
        } catch(JSONParseException e) {
          throw new HTTPException("failed to read JSON response", e);
        }
      }
      else
        throw new HTTPException("unsupported response type " + contentType.getValue());
      
    } catch (ClientProtocolException e) {
      throw new HTTPException("protocol exception while sending HTTP get request (" + request.getURI() + ")", e);
    } catch (IOException e) {
      throw new HTTPException("IO exception while sending HTTP get request (" + request.getURI() + ")", e);
    } 
  }
}
