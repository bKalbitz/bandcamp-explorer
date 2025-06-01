package com.bkalbitz.bandcamp_collection_explorer.collection.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class ArtistDTOTest {

  @Test
  public void testAddAlbum() {
    ArtistDTO underTest = new ArtistDTO("artist");
    assertEquals("artist", underTest.getName());
    assertTrue(underTest.getAlbums().isEmpty());
    assertTrue(underTest.getUrls().isEmpty());

    AlbumDTO albumDTO = new AlbumDTO();
    albumDTO.setTitle("A album");
    albumDTO.setUrls(List.of("artist.bandcamp.com/music/1", "label.bandcamp.com/music/2"));
    underTest.addAlbum(albumDTO);

    assertEquals(1, underTest.getAlbums().size());
    assertEquals(2, underTest.getUrls().size());
    assertTrue(underTest.getUrls().contains("artist.bandcamp.com"));
    assertTrue(underTest.getUrls().contains("label.bandcamp.com"));

    albumDTO = new AlbumDTO();
    albumDTO.setTitle("B album");
    albumDTO.setUrls(List.of("artist.bandcamp.com/music/2"));
    underTest.addAlbum(albumDTO);

    assertEquals(2, underTest.getAlbums().size());
    assertEquals(2, underTest.getUrls().size());
    assertTrue(underTest.getUrls().contains("artist.bandcamp.com"));
    assertTrue(underTest.getUrls().contains("label.bandcamp.com"));
  }

}
