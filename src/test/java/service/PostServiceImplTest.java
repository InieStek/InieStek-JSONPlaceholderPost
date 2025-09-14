package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import exception.PostFetchException;
import exception.PostSaveException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import webclient.PostWebClient;


class PostServiceImplTest {
  private PostWebClient webClient;
  private Gson gson;
  private Path tempDir;
  private PostServiceImpl service;

  @BeforeEach
  public void setUp() throws Exception {
    webClient = mock(PostWebClient.class);
    gson = new Gson();
    tempDir = Files.createTempDirectory("post_service_test");
    service = new PostServiceImpl(webClient, gson, tempDir);
  }

  @Test
  public void testFetchPosts_success() throws Exception {
    String json = "[{\"userId\":1,\"id\":1,\"title\":\"title1\",\"body\":\"body1\"}]";
    when(webClient.getPosts()).thenReturn(json);

    List<Post> posts = service.fetchPosts();

    assertNotNull(posts);
    assertEquals(1, posts.size());
    assertEquals(1, posts.getFirst().id());
    assertEquals("title1", posts.getFirst().title());
  }

  @Test
  public void testFetchPosts_throwsPostFetchException() throws Exception {
    when(webClient.getPosts()).thenThrow(new PostFetchException(new Exception()));

    assertThrows(PostFetchException.class, () -> service.fetchPosts());
  }

  @Test
  public void testSavePosts_and_filesCreated() throws Exception {
    Post post = new Post(1, 123, "Test Title", "Test Body");
    List<Post> posts = List.of(post);

    service.savePosts(posts);

    Path expectedFile = tempDir.resolve("123.json");
    assertTrue(Files.exists(expectedFile));

    String fileContent = Files.readString(expectedFile);
    assertTrue(fileContent.contains("\"id\":123"));
    assertTrue(fileContent.contains("\"title\":\"Test Title\""));
  }

  @Test
  public void testSavePosts_throwsPostSaveException_withMockedIOException() {
    Post post = new Post(1, 1, "title", "body");
    List<Post> posts = List.of(post);

    try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
      mockedFiles.when(() -> Files.writeString(any(Path.class), anyString()))
          .thenThrow(IOException.class);

      PostServiceImpl service = new PostServiceImpl(webClient, gson, Path.of("output"));
      assertThrows(PostSaveException.class, () -> service.savePosts(posts));
    }
  }

  @Test
  public void testFetchAndSavePosts_integration() throws Exception {
    String json = "[{\"userId\":1,\"id\":10,\"title\":\"title10\",\"body\":\"body10\"}]";
    when(webClient.getPosts()).thenReturn(json);

    service.fetchAndSavePosts();

    Path file = tempDir.resolve("10.json");
    assertTrue(Files.exists(file));

    String content = Files.readString(file);
    assertTrue(content.contains("\"id\":10"));
  }

  @Test
  public void testFetchPosts_withEmptyJsonArray() throws Exception {
    String json = "[]";
    when(webClient.getPosts()).thenReturn(json);

    List<Post> posts = service.fetchPosts();

    assertNotNull(posts);
    assertTrue(posts.isEmpty());
  }

  @Test
  public void testFetchPosts_withInvalidJson() throws Exception {
    String json = "[{\"id\":1,}]"; // Invalid JSON
    when(webClient.getPosts()).thenReturn(json);

    assertThrows(PostFetchException.class, () -> service.fetchPosts());
  }

  @Test
  public void testSavePosts_withEmptyList() throws Exception {
    List<Post> posts = List.of();

    service.savePosts(posts);

    try (var files = Files.list(tempDir)) {
        assertEquals(0, files.count());
    }
  }

  @Test
  public void testFetchAndSavePosts_whenFetchFails() throws Exception {
    when(webClient.getPosts()).thenThrow(new PostFetchException(new Exception()));

    assertThrows(PostFetchException.class, () -> service.fetchAndSavePosts());

    try (var files = Files.list(tempDir)) {
        assertEquals(0, files.count());
    }
  }

  @Test
  public void testFetchAndSavePosts_whenSaveFails() throws Exception {
    String json = "[{\"userId\":1,\"id\":1,\"title\":\"title1\",\"body\":\"body1\"}]";
    when(webClient.getPosts()).thenReturn(json);

    try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
      mockedFiles.when(() -> Files.writeString(any(Path.class), anyString()))
          .thenThrow(IOException.class);

      assertThrows(PostSaveException.class, () -> service.fetchAndSavePosts());
    }
  }
}
