package service;

import java.util.List;
import model.Post;

public interface ApiService {
  List<Post> fetchPosts();
}
