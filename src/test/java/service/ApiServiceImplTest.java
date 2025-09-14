package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import exception.PostFetchException;
import java.util.List;
import model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webclient.PostWebClient;

@ExtendWith(MockitoExtension.class)
class ApiServiceImplTest {

  @Mock private PostWebClient webClient;

  @Mock private Gson gson;

  @InjectMocks private ApiServiceImpl apiService;

  private String jsonResponse;
  private Post[] postsArray;

  @BeforeEach
  void setUp() {
    jsonResponse = "[{\"userId\":1,\"id\":1,\"title\":\"Test Title\",\"body\":\"Test Body\"}]";
    postsArray = new Post[] {new Post(1, 1, "Test Title", "Test Body")};
  }

  @Test
  void fetchPosts_shouldReturnListOfPosts_whenApiCallIsSuccessful() throws Exception {
    when(webClient.getPosts()).thenReturn(jsonResponse);
    when(gson.fromJson(jsonResponse, Post[].class)).thenReturn(postsArray);

    List<Post> result = apiService.fetchPosts();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Test Title", result.get(0).title());
  }

  @Test
  void fetchPosts_shouldThrowPostFetchException_whenWebClientFails() throws Exception {
    when(webClient.getPosts()).thenThrow(new RuntimeException("API is down"));

    assertThrows(PostFetchException.class, () -> apiService.fetchPosts());
  }

  @Test
  void fetchPosts_shouldThrowPostFetchException_whenGsonFails() throws Exception {
    when(webClient.getPosts()).thenReturn(jsonResponse);
    when(gson.fromJson(jsonResponse, Post[].class))
        .thenThrow(new RuntimeException("JSON parsing error"));

    assertThrows(PostFetchException.class, () -> apiService.fetchPosts());
  }
}
