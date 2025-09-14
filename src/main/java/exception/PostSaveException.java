package exception;

public class PostSaveException extends RuntimeException {

  public PostSaveException(Throwable cause) {
    super("Error while save posts" + cause.getMessage(), cause);
  }
}
