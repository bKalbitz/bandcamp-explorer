package com.bkalbitz.bandcamp_collection_explorer.collection.crawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.gson.JsonArray;

@ExtendWith(MockitoExtension.class)
public class BCRestEndpointTest {

  // @formatter:off
  private static final String API_RESULT = ""
      + "{\r\n"
      + "    \"tralbums\": [\r\n"
      + "        {\r\n"
      + "            \"item_type\": \"album\",\r\n"
      + "            \"item_title\": \"title1\",\r\n"
      + "            \"item_url\": \"https://band1.bandcamp.com/album/album1\",\r\n"
      + "            \"item_art_url\": \"album1.jpg\",\r\n"
      + "            \"band_name\": \"band1\"\r\n"
      + "        },\r\n"
      + "        {\r\n"
      + "            \"item_type\": \"album\",\r\n"
      + "            \"item_title\": \"title2\",\r\n"
      + "            \"item_url\": \"https://band2.bandcamp.com/album/album2\",\r\n"
      + "            \"item_art_url\": \"album2.jpg\",\r\n"
      + "            \"band_name\": \"band2\"\r\n"
      + "        }\r\n"
      + "    ]\r\n"
      + "}";
  // @formatter:on

  @Test
  void loadCollectionData() throws IOException, InterruptedException {
    try (MockedStatic<HttpClient> staticmock = Mockito.mockStatic(HttpClient.class)) {
      HttpClient httpClient = Mockito.mock(HttpClient.class);
      staticmock.when(HttpClient::newHttpClient).thenReturn(httpClient);
      HttpResponse response = Mockito.mock(HttpResponse.class);
      Mockito.when(httpClient.send(Mockito.any(), Mockito.any())).thenReturn(response);
      Mockito.when(response.body()).thenReturn(API_RESULT);

      BCRestEndpoint underTest = new BCRestEndpoint();
      JsonArray result = underTest.loadCollectionData("test", null);
      assertNotNull(result);
      assertEquals(2, result.size());
      assertEquals("title1", result.get(0).getAsJsonObject().getAsJsonPrimitive("item_title").getAsString());
      assertEquals("band2", result.get(1).getAsJsonObject().getAsJsonPrimitive("band_name").getAsString());
    }
  }

}
