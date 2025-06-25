package com.bkalbitz.bandcamp_collection_intersection.intersection.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class ArtistDTOTest {

  @Test
  public void addAlbum() {
    ArtistDTO underTest = new ArtistDTO();
    assertTrue(underTest.getAlbums().isEmpty());
    assertTrue(underTest.getUrls().isEmpty());

    AlbumDTO albumDTO = new AlbumDTO();
    albumDTO.setTitle("Z");
    albumDTO.setUrls(List.of("artist1.bandcamp.com/album/x"));
    underTest.addAlbum(albumDTO);
    assertEquals(1, underTest.getAlbums().size());
    assertEquals(1, underTest.getUrls().size());

    albumDTO = new AlbumDTO();
    albumDTO.setTitle("Y");
    albumDTO.setUrls(List.of("artist1.bandcamp.com/album/y"));
    underTest.addAlbum(albumDTO);
    assertEquals(2, underTest.getAlbums().size());
    assertEquals(1, underTest.getUrls().size());

    albumDTO = new AlbumDTO();
    albumDTO.setTitle("z");
    albumDTO.setUrls(List.of("artist2.bandcamp.com/album/z"));
    underTest.addAlbum(albumDTO);
    assertEquals(3, underTest.getAlbums().size());
    assertEquals(2, underTest.getUrls().size());
  }

}
