package com.bkalbitz.bandcamp_collection_intersection.intersection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bkalbitz.bandcamp_collection_intersection.intersection.IntersectionService.State;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.AlbumDTO;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.ArtistDTO;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.CollectionDTO;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.IntersectionDTO;

@ExtendWith(MockitoExtension.class)
class IntersectionServiceTest {

  @Mock
  private AsyncLoaderService asyncLoaderService;

  @Mock
  private CollectionService collectionService;

  @InjectMocks
  private IntersectionService underTest;

  private String[] names = { "name1" + System.currentTimeMillis(), "name2" + System.currentTimeMillis() };

  @Test
  void getResult() {
    int id = underTest.loadIntersection(names);
    State loadingState = underTest.getLoadingState(id);
    assertNotNull(loadingState);
    assertEquals(id, loadingState.getId());
    assertEquals(Set.of(names[0], names[1]), loadingState.getAll());
    assertEquals("pending", loadingState.getState());
    assertEquals(Set.of(names[0], names[1]), loadingState.getToLoad());
    assertTrue(loadingState.getLoaded().isEmpty());

    loadingState.entryFinished(names[0]);
    loadingState = underTest.getLoadingState(id);
    assertEquals(id, loadingState.getId());
    assertNotNull(loadingState);
    assertEquals(Set.of(names[0], names[1]), loadingState.getAll());
    assertEquals("pending", loadingState.getState());
    assertEquals(Set.of(names[1]), loadingState.getToLoad());
    assertEquals(Set.of(names[0]), loadingState.getLoaded());

    loadingState.entryFinished(names[1]);
    loadingState = underTest.getLoadingState(id);
    assertEquals(id, loadingState.getId());
    assertNotNull(loadingState);
    assertEquals(Set.of(names[0], names[1]), loadingState.getAll());
    assertEquals("done", loadingState.getState());
    assertTrue(loadingState.getToLoad().isEmpty());
    assertEquals(Set.of(names[0], names[1]), loadingState.getLoaded());

    CollectionDTO collection1 = new CollectionDTO();
    ArtistDTO arist1 = new ArtistDTO();
    AlbumDTO album1 = new AlbumDTO();
    album1.setArtist("artist1");
    album1.setTitle("album1");
    arist1.setAlbums(List.of(album1));
    collection1.setArtists(List.of(arist1));

    ArtistDTO arist2 = new ArtistDTO();
    AlbumDTO album2 = new AlbumDTO();
    album2.setArtist("artist2");
    album2.setTitle("album2");
    arist2.setAlbums(List.of(album2));
    CollectionDTO collection2 = new CollectionDTO();
    collection2.setArtists(List.of(arist1, arist2));

    Mockito.when(collectionService.getCollection(names[0])).thenReturn(collection1);
    Mockito.when(collectionService.getCollection(names[1])).thenReturn(collection2);
    IntersectionDTO result = underTest.getResult(id);
    assertNotNull(result);
    assertEquals(1, result.getEntries().size());
    assertEquals(album1, result.getEntries().get(0).getAlbum());
    assertEquals(2, result.getEntries().get(0).getInCollections().size());

  }

}
