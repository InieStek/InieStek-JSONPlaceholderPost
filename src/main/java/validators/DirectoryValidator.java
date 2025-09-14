package validators;

import exception.DirectoryCreationException;
import exception.PostSaveException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryValidator {

  public static void ensureDirectoryExists(Path dir) {
    try {
      if (Files.isRegularFile(dir)) {
        throw new PostSaveException(
            "Output path exists but is not a directory: " + dir.toAbsolutePath());
      }
      Files.createDirectories(dir);
    } catch (IOException e) {
      throw new DirectoryCreationException(e);
    }
  }
}
