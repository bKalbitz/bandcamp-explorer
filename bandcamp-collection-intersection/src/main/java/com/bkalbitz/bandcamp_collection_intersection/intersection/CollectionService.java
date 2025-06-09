package com.bkalbitz.bandcamp_collection_intersection.intersection;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.bkalbitz.bandcamp_collection_intersection.intersection.data.CollectionDTO;

@Service
public class CollectionService {

  private final RestClient restClient;

  public CollectionService() {
    this.restClient = RestClient.builder().baseUrl("http://localhost:8080").build();
  }

  public CollectionDTO getCollection(String name) {
    return restClient.get().uri("/api/collection/{name}", name).retrieve().body(CollectionDTO.class);
  }
}
