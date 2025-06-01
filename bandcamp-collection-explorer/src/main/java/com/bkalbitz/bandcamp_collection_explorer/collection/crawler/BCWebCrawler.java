package com.bkalbitz.bandcamp_collection_explorer.collection.crawler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BCWebCrawler {
  private static final String BC_DOMAIN = "https://bandcamp.com/";
  private static final String ID_MARKER = "id=\"follow-unfollow_";

  public String parseFanId(String name) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BC_DOMAIN + name)).GET().build();
    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      String collectionHTML = response.body();
      int cut = StringUtils.indexOf(collectionHTML, ID_MARKER);
      if (cut < 0) {
        return null;
      }
      collectionHTML = StringUtils.substring(collectionHTML, cut + ID_MARKER.length());
      return StringUtils.substring(collectionHTML, 0, StringUtils.indexOf(collectionHTML, '\"'));
    } catch (IOException | InterruptedException e) {
      return null;
    }
  }
}
