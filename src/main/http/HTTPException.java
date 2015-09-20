package main.http;

public class HTTPException extends Exception {

  private static final long serialVersionUID = -6374516785848141686L;

  public HTTPException(String message) {
    super(message);
  }
  
  public HTTPException(String message, Throwable reason) {
    super(message, reason);
  }

}
