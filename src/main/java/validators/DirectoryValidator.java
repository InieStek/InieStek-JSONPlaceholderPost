package validators;

import exception.DirectoryCreationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryValidator {

  public static void ensureDirectoryExists(Path dir){
    try {
      if (Files.notExists(dir)) {
        Files.createDirectories(dir);
      }
    } catch (IOException e) {
      throw new DirectoryCreationException(e);
    }
  }

}
