package service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import exception.PostFetchException;
import exception.PostSaveException;
import java.util.Collections;
import java.util.List;
import model.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

  @Mock private ApiService apiService;

  @Mock private PostStorageService postStorageService;

  @InjectMocks private PostServiceImpl postService;

  @Test
  void processPosts_shouldCallApiAndStorageServices() {
    List<Post> posts = Collections.singletonList(new Post(1, 1, "title", "body"));
    when(apiService.fetchPosts()).thenReturn(posts);

    postService.processPosts();

    verify(apiService).fetchPosts();
    verify(postStorageService).savePosts(posts);
  }

  @Test
  void processPosts_shouldThrowPostFetchException_whenApiFails() {
    when(apiService.fetchPosts()).thenThrow(new PostFetchException(new RuntimeException()));

    assertThrows(PostFetchException.class, () -> postService.processPosts());
  }

  @Test
  void syncException_whenStorageFails() {
    List<Post> posts = Collections.singletonList(new Post(1, 1, "title", "body"));
    when(apiService.fetchPosts()).thenReturn(posts);
    doThrow(new PostSaveException(new RuntimeException()))
        .when(postStorageService)
        .savePosts(posts);

    assertThrows(PostSaveException.class, () -> postService.processPosts());
  }
}
