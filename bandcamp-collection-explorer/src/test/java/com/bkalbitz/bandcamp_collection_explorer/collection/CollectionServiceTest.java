package com.bkalbitz.bandcamp_collection_explorer.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bkalbitz.bandcamp_collection_explorer.collection.crawler.BCRestEndpoint;
import com.bkalbitz.bandcamp_collection_explorer.collection.crawler.BCWebCrawler;
import com.bkalbitz.bandcamp_collection_explorer.collection.data.AlbumDTO;
import com.bkalbitz.bandcamp_collection_explorer.collection.data.ArtistDTO;
import com.bkalbitz.bandcamp_collection_explorer.collection.data.CollectionBuilder;
import com.bkalbitz.bandcamp_collection_explorer.collection.data.CollectionDTO;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumEntity;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumId;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumRepository;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntity;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntryEntity;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntryRepository;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest {

  @Mock
  private CollectionRepository collectionRepository;
  @Mock
  private CollectionEntryRepository collectionEntryRepository;
  @Mock
  private AlbumRepository albumRepository;
  @Mock
  private BCWebCrawler bcWebsite;
  @Mock
  private BCRestEndpoint bcAPI;
  @Mock
  private CollectionBuilder collectionBuilder;

  @InjectMocks
  private CollectionService underTest;

  @Test
  void testGetCollection() {
    Mockito.when(collectionRepository.existsById("test")).thenReturn(true);
    CollectionEntity collectionEntity = Mockito.mock(CollectionEntity.class);
    List<AlbumEntity> albumEntities = List.of();
    Mockito.when(collectionRepository.getReferenceById("test")).thenReturn(collectionEntity);
    Mockito.when(albumRepository.findAllByCollection("test")).thenReturn(albumEntities);
    CollectionDTO result = Mockito.mock(CollectionDTO.class);
    Mockito.when(collectionBuilder.build(collectionEntity, albumEntities)).thenReturn(result);
    assertEquals(result, underTest.getCollection("test"));
  }

  @Test
  void testUpdateCollection() {
    Mockito.when(bcWebsite.parseFanId("test")).thenReturn("fanId");
    CollectionEntity collectionEntity = new CollectionEntity();
    collectionEntity.setFanId("fanId");
    collectionEntity.setName("test");
    Mockito.when(collectionRepository.save(Mockito.any())).thenReturn(collectionEntity);
    JsonArray bcCollection = new JsonArray(2);
    JsonObject album = new JsonObject();
    album.addProperty("item_type", "item_type");
    album.addProperty("item_url", "item_url");
    album.addProperty("album_title", "album_title");
    album.addProperty("band_name", "band_name");
    album.addProperty("item_art_url", "item_art_url");
    bcCollection.add(album);
    bcCollection.add(album);
    Mockito.when(bcAPI.loadCollectionData("fanId", null)).thenReturn(bcCollection);
    // TODO test also the true path here
    Mockito.when(albumRepository.existsById(Mockito.any())).thenReturn(false);
    AlbumEntity albumEntity = new AlbumEntity();
    albumEntity.setAlbumId(new AlbumId("album_title", "band_name"));
    Mockito.when(albumRepository.save(Mockito.any())).thenReturn(albumEntity);
    // TODO test also the true path here
    Mockito.when(collectionEntryRepository.existsById(Mockito.any())).thenReturn(false);
    CollectionEntryEntity collectionEntryEntity = new CollectionEntryEntity();
    Mockito.when(collectionEntryRepository.save(Mockito.any())).thenReturn(collectionEntryEntity);
    CollectionDTO result = Mockito.mock(CollectionDTO.class);
    Mockito.when(collectionBuilder.build(Mockito.any(), Mockito.any())).thenReturn(result);
    assertEquals(result, underTest.updateCollection("test", null));

  }

  @Test
  public void getIntersection() {
    Mockito.when(collectionRepository.existsById("test1")).thenReturn(true);
    CollectionEntity collectionEntity1 = Mockito.mock(CollectionEntity.class);
    List<AlbumEntity> albumEntities1 = List.of();
    Mockito.when(collectionRepository.getReferenceById("test1")).thenReturn(collectionEntity1);
    Mockito.when(albumRepository.findAllByCollection("test1")).thenReturn(albumEntities1);
    CollectionDTO result1 = Mockito.mock(CollectionDTO.class);
    Mockito.when(collectionBuilder.build(Mockito.eq(collectionEntity1), Mockito.eq(albumEntities1)))
        .thenReturn(result1);

    Mockito.when(collectionRepository.existsById("test2")).thenReturn(true);
    CollectionEntity collectionEntity2 = Mockito.mock(CollectionEntity.class);
    List<AlbumEntity> albumEntities2 = List.of();
    Mockito.when(collectionRepository.getReferenceById("test2")).thenReturn(collectionEntity2);
    Mockito.when(albumRepository.findAllByCollection("test2")).thenReturn(albumEntities2);
    CollectionDTO result2 = Mockito.mock(CollectionDTO.class);
    Mockito.when(collectionBuilder.build(Mockito.eq(collectionEntity2), Mockito.eq(albumEntities2)))
        .thenReturn(result2);

    ArtistDTO result1Artist1 = Mockito.mock(ArtistDTO.class);
    ArtistDTO result2Artist1 = Mockito.mock(ArtistDTO.class);
    AlbumDTO artist1Album1 = new AlbumDTO();
    artist1Album1.setArtist("artist1");
    artist1Album1.setTitle("album1");
    AlbumDTO artist1Album2 = new AlbumDTO();
    artist1Album2.setArtist("artist1");
    artist1Album2.setTitle("album2");
    Mockito.when(result1Artist1.getAlbums()).thenReturn(List.of(artist1Album1));
    Mockito.when(result2Artist1.getAlbums()).thenReturn(List.of(artist1Album1, artist1Album2));
    ArtistDTO result1Artist2 = Mockito.mock(ArtistDTO.class);
    ArtistDTO result2Artist2 = Mockito.mock(ArtistDTO.class);
    AlbumDTO artist2Album1 = new AlbumDTO();
    artist2Album1.setArtist("artist2");
    artist2Album1.setTitle("album1");
    AlbumDTO artist2Album2 = new AlbumDTO();
    artist2Album2.setArtist("artist2");
    artist2Album2.setTitle("album2");
    Mockito.when(result1Artist2.getAlbums()).thenReturn(List.of(artist2Album1, artist2Album2));
    Mockito.when(result2Artist2.getAlbums()).thenReturn(List.of(artist2Album2));

    Mockito.when(result1.getArtists()).thenReturn(List.of(result1Artist1, result1Artist2));
    Mockito.when(result2.getArtists()).thenReturn(List.of(result2Artist1, result2Artist2));

    IntersectionDTO result = underTest.getIntersection(new String[] { "test1", "test2" });
    assertEquals(2, result.getEntries().size());
    for (IntersectionEntryDTO entry : result.getEntries()) {
      assertTrue(entry.getAlbum().equals(artist1Album1) || entry.getAlbum().equals(artist2Album2));
      assertEquals(2, entry.getInCollections().size());
    }
  }
}
