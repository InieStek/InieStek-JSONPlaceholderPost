package webclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import validators.StatusValidator;

public class PostWebClient {

  private final HttpClient httpClient;

  public PostWebClient(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public String getPosts() throws Exception {
    String url = "https://jsonplaceholder.typicode.com/posts";

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();

    HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

    StatusValidator.validateStatus(response);
    return response.body();
  }
}
