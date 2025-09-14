import com.google.gson.Gson;
import config.AppConfig;
import exception.ConfigLoadException;
import exception.PostFetchException;
import exception.PostSaveException;
import java.net.http.HttpClient;
import java.nio.file.Path;
import java.nio.file.Paths;
import service.ApiService;
import service.ApiServiceImpl;
import service.PostService;
import service.PostServiceImpl;
import service.PostStorageService;
import service.PostStorageServiceImpl;
import webclient.PostWebClient;

public class PostApplication {

  public static void main(String[] args) {
    try {
      System.out.println("Starting post fetching process...");
      AppConfig appConfig = new AppConfig();
      HttpClient client = HttpClient.newHttpClient();
      Gson gson = new Gson();

      String apiUrl = appConfig.getApiUrl();
      Path path = Paths.get(appConfig.getOutputDir());

      PostWebClient postWebClient = new PostWebClient(client, apiUrl);
      ApiService apiService = new ApiServiceImpl(postWebClient, gson);
      PostStorageService postStorageService = new PostStorageServiceImpl(gson, path);

      PostService service = new PostServiceImpl(apiService, postStorageService);
      service.fetchSavePosts();
      System.out.println("Successfully fetched and saved posts to '" + path.toAbsolutePath() + "'.");
    } catch (PostFetchException | PostSaveException | ConfigLoadException e) {
      System.err.println(e.getMessage());
    }
  }

}
