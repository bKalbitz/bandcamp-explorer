package com.bkalbitz.bandcamp_collection_explorer.collection.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumEntity;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumId;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntity;

class CollectionBuilderTest {

  @Test
  void testBuild() {
    CollectionEntity collectionEntity = new CollectionEntity();
    collectionEntity.setFanId("fanId");
    collectionEntity.setLastUpdate(0L);
    collectionEntity.setName("name");
    Collection<AlbumEntity> albumEntities = List.of(crateAlbum("A title", "A Band"), crateAlbum("B title", "A Band"),
        crateAlbum("C title", "B Band"));
    CollectionDTO underTest = new CollectionBuilder().build(collectionEntity, albumEntities);
    assertEquals("name", underTest.getName());
    assertEquals("fanId", underTest.getFanId());
    assertEquals(2, underTest.getArtists().size());
    List<ArtistDTO> artists = (List<ArtistDTO>) underTest.getArtists();
    assertEquals("A Band", artists.get(0).getName());
    assertEquals(2, artists.get(0).getAlbums().size());
    assertEquals("B Band", artists.get(1).getName());
    assertEquals(1, artists.get(1).getAlbums().size());
  }

  private static AlbumEntity crateAlbum(String title, String band) {
    AlbumEntity entity = new AlbumEntity();
    entity.setAlbumId(new AlbumId(title, band));
    entity.setUrls("https://" + band + ".bandcamp.com/music/" + title);
    return entity;
  }
}
