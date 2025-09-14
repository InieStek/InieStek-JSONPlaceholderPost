package service;

import com.google.gson.Gson;
import exception.PostFetchException;
import java.util.Arrays;
import java.util.List;
import model.Post;
import webclient.PostWebClient;

public class ApiServiceImpl implements ApiService{
  private final PostWebClient webClient;
  private final Gson gson;

  public ApiServiceImpl(PostWebClient webClient, Gson gson) {
    this.webClient = webClient;
    this.gson = gson;
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
}
