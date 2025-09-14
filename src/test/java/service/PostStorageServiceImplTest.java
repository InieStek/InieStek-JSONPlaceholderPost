package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import exception.PostSaveException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostStorageServiceImplTest {

  @Mock private Gson gson;

  private PostStorageServiceImpl postStorageService;

  @TempDir Path tempDir;

  private Post post;
  private String postJson;

  @BeforeEach
  void setUp() {
    postStorageService = new PostStorageServiceImpl(gson, tempDir);
    post = new Post(1, 1, "Test Title", "Test Body");
    postJson = "{\"id\":1,\"userId\":1,\"title\":\"Test Title\",\"body\":\"Test Body\"}";
  }

  @Test
  void savePosts_shouldSavePostToFileSystem_whenCalled() throws IOException {
    when(gson.toJson(post)).thenReturn(postJson);

    postStorageService.savePosts(List.of(post));

    Path expectedFile = tempDir.resolve("1.json");
    assertTrue(Files.exists(expectedFile));
    assertEquals(postJson, Files.readString(expectedFile));
  }

  @Test
  void savePosts_shouldThrowPostSaveException_whenFileWriteFails() {
    when(gson.toJson(any(Post.class))).thenThrow(new RuntimeException("Gson serialization error"));

    assertThrows(PostSaveException.class, () -> postStorageService.savePosts(Collections.singletonList(post)));
  }
}
