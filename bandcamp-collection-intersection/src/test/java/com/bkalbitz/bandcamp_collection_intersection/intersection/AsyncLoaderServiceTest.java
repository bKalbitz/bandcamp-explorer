package com.bkalbitz.bandcamp_collection_intersection.intersection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bkalbitz.bandcamp_collection_intersection.intersection.IntersectionService.State;

@ExtendWith(MockitoExtension.class)
class AsyncLoaderServiceTest {

  @Mock
  private CollectionService collectionService;

  @InjectMocks
  private AsyncLoaderService underTest;

  @Test
  void loadCollectionAsync() {
    Set<String> names = Set.of("name1", "name2");
    State state = new State(0, names);
    underTest.loadCollectionAsync(names, state);
    assertEquals(names, state.getLoaded());
  }

}
