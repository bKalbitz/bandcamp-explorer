package com.bkalbitz.bandcamp_collection_intersection.intersection;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bkalbitz.bandcamp_collection_intersection.intersection.IntersectionService.State;

@Service
public class AsyncLoaderService {

  @Autowired
  private CollectionService collectionService;

  @Async
  public void loadCollectionAsync(Set<String> names, State state) {
    for (String name : names) {
      collectionService.getCollection(name);
      state.entryFinished(name);
    }
  }
}
