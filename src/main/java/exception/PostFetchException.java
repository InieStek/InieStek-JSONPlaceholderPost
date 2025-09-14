package exception;

public class PostFetchException extends RuntimeException {

  public PostFetchException(Throwable cause) {
    super("Error while downloading posts" + cause.getMessage(), cause);
  }
}
