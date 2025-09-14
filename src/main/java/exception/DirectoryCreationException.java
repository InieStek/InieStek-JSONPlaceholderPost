package exception;

public class DirectoryCreationException extends RuntimeException {

  public DirectoryCreationException(Throwable cause) {
    super("Error while creating directory: " + cause.getMessage(), cause);
  }
}

