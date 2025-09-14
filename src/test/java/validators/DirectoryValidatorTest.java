package validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exception.PostSaveException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DirectoryValidatorTest {

  @Test
  @DisplayName("Should create directory if it does not exist")
  void ensureDirectoryExists_shouldCreateDirectory_whenItDoesNotExist(@TempDir Path tempDir) {
    // given
    Path newDir = tempDir.resolve("newDir");

    // when
    DirectoryValidator.ensureDirectoryExists(newDir);

    // then
    assertTrue(Files.isDirectory(newDir));
  }

  @Test
  @DisplayName("Should not throw exception if directory already exists")
  void ensureDirectoryExists_shouldDoNothing_whenDirectoryExists(@TempDir Path tempDir) {
    // given
    // tempDir already exists

    // when
    DirectoryValidator.ensureDirectoryExists(tempDir);

    // then
    assertTrue(Files.isDirectory(tempDir));
  }

  @Test
  @DisplayName("Should throw PostSaveException if path is a file")
  void ensureDirectoryExists_shouldThrowException_whenPathIsAFile(@TempDir Path tempDir)
      throws IOException {
    // given
    Path filePath = tempDir.resolve("aFile");
    Files.createFile(filePath);

    // when & then
    PostSaveException exception =
        assertThrows(
            PostSaveException.class, () -> DirectoryValidator.ensureDirectoryExists(filePath));

    String expectedMessage =
        "Output path exists but is not a directory: " + filePath.toAbsolutePath();
    assertEquals(expectedMessage, exception.getMessage());
  }
}
