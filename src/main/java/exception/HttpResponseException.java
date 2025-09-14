package exception;

public class HttpResponseException extends RuntimeException {

  private final int statusCode;
  private final String responseBody;

  public HttpResponseException(int statusCode, String responseBody) {
    this.statusCode = statusCode;
    this.responseBody = responseBody;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public String getResponseBody() {
    return responseBody;
  }
}
