package com.bkalbitz.bandcamp_collection_explorer.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bkalbitz.bandcamp_collection_explorer.collection.crawler.BCRestEndpoint;
import com.bkalbitz.bandcamp_collection_explorer.collection.crawler.BCWebCrawler;
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
}
