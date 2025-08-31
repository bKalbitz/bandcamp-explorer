package com.bkalbitz.bandcamp_collection_intersection.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CollectionServiceConfig {

    
  @Value("${collection.service.host}")
  private String host;

  public String getHost() {
    return host;
  }
}
