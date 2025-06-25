package com.bkalbitz.bandcamp_collection_explorer.collection.crawler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BCWebCrawlerTest {

  private static final String WEBSITE_HTML = "...whatever htmlcode id=\"follow-unfollow_fanId\" ...whatever html code";

  @Test
  void parseFanId() throws IOException, InterruptedException {
    try (MockedStatic<HttpClient> staticmock = Mockito.mockStatic(HttpClient.class)) {
      HttpClient httpClient = Mockito.mock(HttpClient.class);
      staticmock.when(HttpClient::newHttpClient).thenReturn(httpClient);
      HttpResponse response = Mockito.mock(HttpResponse.class);
      Mockito.when(httpClient.send(Mockito.any(), Mockito.any())).thenReturn(response);
      Mockito.when(response.body()).thenReturn(WEBSITE_HTML);
      BCWebCrawler underTest = new BCWebCrawler();
      assertEquals("fanId", underTest.parseFanId("test"));
    }
  }

}
