package com.bkalbitz.bandcamp_collection_explorer.collection.crawler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

@Service
public class BCRestEndpoint {

  private static final String COLLECTION_SEARCH = "https://bandcamp.com/api/fancollection/1/search_items";

  public JsonArray loadCollectionData(String fanId, @Nullable String searchParam) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(COLLECTION_SEARCH))
        .POST(HttpRequest.BodyPublishers.ofString("{\"fan_id\":" + fanId + ",\"search_key\":\""
            + StringUtils.defaultIfBlank(searchParam, "") + "\",\"search_type\":\"collection\"}"))
        .build();
    String responseString = null;
    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      responseString = response.body();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    try {
      if (StringUtils.isBlank(responseString)) {
        return null;
      }
      JsonObject result = new Gson().fromJson(responseString, JsonObject.class);
      JsonArray tralbums = result.getAsJsonArray("tralbums");

      if (tralbums == null || tralbums.isEmpty()) {
        return null;
      }

      return tralbums;
    } catch (JsonSyntaxException | ClassCastException e) {
      return null;
    }
  }
}
