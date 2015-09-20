package main.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServer implements HttpHandler {
  
  private HttpServer server;
  private int portNumber;
  
  public HTTPServer(int portNumber) throws HTTPException {
    this.portNumber = portNumber;
    
    if(createServer() == false) {
      HTTPPostRequest request = new HTTPPostRequest("http://localhost:"+portNumber+"/terminate");
      HTTPResponse response = request.sendAndGetResponse();
      if(response.wasSuccess())
        createServer();
      else
        throw new HTTPException("could not create HTTP server on port " + portNumber);
    }
  }
  
  private boolean createServer() {
    try {
      server = HttpServer.create(new InetSocketAddress(portNumber), 0);
      server.createContext("/", this);
      server.setExecutor(null);
      server.start();
      return true;      
    } catch(Exception e) {
      return false;
    }
  }
  
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String method = exchange.getRequestMethod();
    String uri = exchange.getRequestURI().toString();
    System.out.println("URI: " + uri + " (" + method + ")");
    
    try {
      if(method.equals("POST") && uri.equals("/terminate")) {
        byte[] response = "OK".getBytes();
        Headers headers = exchange.getResponseHeaders();
        headers.set("Content-Type","text/plain");
        exchange.sendResponseHeaders(HTTPResponse.CODE_OK, 0);
        OutputStream os = exchange.getResponseBody();
        os.write(response); 
        os.close();
        server.stop(0);
      }
      else {
        exchange.sendResponseHeaders(405, 0);
        System.err.println("unsupported " + method + " request " + uri);
      }
    } catch(Exception e) {
      System.err.println("exception during handling a " + method + " request " + uri);
    }
  }
}

