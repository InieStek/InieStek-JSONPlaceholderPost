package service;

import exception.PostFetchException;
import exception.PostSaveException;
import java.util.List;
import model.Post;

public class PostServiceImpl implements PostService {
  private final ApiService apiService;
  private final PostStorageService postStorageService;

  public PostServiceImpl(ApiService apiService, PostStorageService postStorageService) {
    this.apiService = apiService;
    this.postStorageService = postStorageService;
  }

  @Override
  public void fetchAndSavePosts() throws PostFetchException, PostSaveException {
    List<Post> posts = apiService.fetchPosts();
    postStorageService.savePosts(posts);
  }
}
