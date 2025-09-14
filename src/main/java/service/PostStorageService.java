package service;

import java.util.List;
import model.Post;

public interface PostStorageService {
  void savePosts(List<Post> posts);
}
