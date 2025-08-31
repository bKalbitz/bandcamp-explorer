package com.bkalbitz.bandcamp_collection_intersection.intersection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.bkalbitz.bandcamp_collection_intersection.config.CollectionServiceConfig;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.CollectionDTO;

@Service
public class CollectionService {


  private final RestClient restClient;

  public CollectionService(CollectionServiceConfig config) {
    this.restClient = RestClient.builder().baseUrl(config.getHost()).build();
  }

  public CollectionDTO getCollection(String name) {
    return restClient.get().uri("/api/collection/{name}", name).retrieve().body(CollectionDTO.class);
  }
}
