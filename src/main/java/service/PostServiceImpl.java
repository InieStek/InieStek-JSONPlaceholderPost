package service;

import com.google.gson.Gson;
import exception.PostFetchException;
import exception.PostSaveException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import model.Post;
import validators.DirectoryValidator;
import webclient.PostWebClient;

public class PostServiceImpl implements PostService {

  private final PostWebClient webClient;
  private final Gson gson;
  private final Path outputDir;

  public PostServiceImpl(PostWebClient webClient, Gson gson, Path outputDir) {
    this.webClient = webClient;
    this.gson = gson;
    this.outputDir = outputDir;
  }


  @Override
  public List<Post> fetchPosts() {
    try {
      String json = webClient.getPosts();
      Post[] postModels = gson.fromJson(json, Post[].class);
      return Arrays.asList(postModels);
    } catch (Exception e) {
      throw new PostFetchException(e);
    }
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

  @Override
  public void fetchAndSavePosts() throws PostFetchException, PostSaveException {
    List<Post> posts = fetchPosts();
    savePosts(posts);
  }
}
