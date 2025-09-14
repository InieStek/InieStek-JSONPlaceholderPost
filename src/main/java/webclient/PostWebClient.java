package webclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import validators.StatusValidator;

public class PostWebClient {

  private final HttpClient httpClient;
  private final String apiUrl;

  public PostWebClient(HttpClient httpClient, String apiUrl) {
    this.httpClient = httpClient;
    this.apiUrl = apiUrl;
  }

  public String getPosts() throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(apiUrl))
        .GET()
        .build();

    HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

    StatusValidator.validateStatus(response);
    return response.body();
  }
}
