package com.youyi.infra.http;

import com.youyi.common.util.GsonUtil;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/09
 */
public class HttpUtil {

    private static final String CONTENT_TYPE = "Content-Type";

    private static final String APPLICATION_JSON = "application/json";

    private static final String ACCEPT = "Accept";

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static CompletableFuture<String> sendPostRequest(String url, Object requestBody) {
        String body = GsonUtil.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .header(ACCEPT, APPLICATION_JSON)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body);
    }
}
