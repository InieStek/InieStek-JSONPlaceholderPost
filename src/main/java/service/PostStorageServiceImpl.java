package service;

import com.google.gson.Gson;
import exception.PostSaveException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import model.Post;
import validators.DirectoryValidator;

public class PostStorageServiceImpl implements PostStorageService{
  private final Gson gson;
  private final Path outputDir;

  public PostStorageServiceImpl(Gson gson, Path outputDir) {
    this.gson = gson;
    this.outputDir = outputDir;
  }

  @Override
  public void savePosts(List<Post> posts) {
    DirectoryValidator.ensureDirectoryExists(outputDir);

    try {
      for (Post post : posts) {
        Path file = outputDir.resolve(post.id() + ".json");
        Files.writeString(file, gson.toJson(post));
      }
    } catch (Exception e) {
      throw new PostSaveException(e);
    }
  }
}
