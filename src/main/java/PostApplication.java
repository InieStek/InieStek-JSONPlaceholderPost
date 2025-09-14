import com.google.gson.Gson;
import exception.PostFetchException;
import exception.PostSaveException;
import java.net.http.HttpClient;
import java.nio.file.Path;
import service.PostService;
import service.PostServiceImpl;
import webclient.PostWebClient;

public class PostApplication {

  public static void main(String[] args) {
    System.out.println("Starting post fetching process...");
    HttpClient client = HttpClient.newHttpClient();
    PostWebClient postWebClient = new PostWebClient(client);
    Gson gson = new Gson();
    Path outputDir = Path.of("output");

    PostService service = new PostServiceImpl(postWebClient, gson, outputDir);

    try {
      service.fetchAndSavePosts();
      System.out.println("Successfully fetched and saved posts to '" + outputDir.toAbsolutePath() + "'.");
    } catch (PostFetchException | PostSaveException e) {
      System.err.println(e.getMessage());
    }
  }

}
