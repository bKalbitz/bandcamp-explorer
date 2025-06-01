package com.bkalbitz.bandcamp_collection_explorer.collection.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumEntity;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumId;

class AlbumDTOTest {

  @Test
  void testCreateByEntity() {
    AlbumEntity entity = new AlbumEntity();
    entity.setAlbumId(new AlbumId("title", "band"));
    entity.setUrls("https://a.com;https://b.com");
    entity.setArtUrl("https://image.jpg");
    entity.setType("album");

    AlbumDTO underTest = new AlbumDTO(entity);
    assertEquals("title", underTest.getTitle());
    assertEquals("band", underTest.getArtist());
    assertEquals("https://image.jpg", underTest.getArtUrl());
    assertEquals(2, underTest.getUrls().size());
    assertTrue(underTest.getUrls().contains("https://a.com"));
    assertTrue(underTest.getUrls().contains("https://b.com"));
  }
}
