package service;

import java.util.List;
import model.Post;

public interface PostService {
  List<Post> fetchPosts();
  void savePosts(List<Post> posts);
  void fetchAndSavePosts();
}
