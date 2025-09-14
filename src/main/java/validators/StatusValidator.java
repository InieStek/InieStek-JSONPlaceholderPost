package validators;

import exception.HttpResponseException;
import java.net.http.HttpResponse;

public class StatusValidator {

  public static void validateStatus(HttpResponse<?> response) {
    int status = response.statusCode();
    if (status < 200 || status >= 300) {
      throw new HttpResponseException(response.statusCode(), response.body().toString());
    }
  }
}
