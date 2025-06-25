package com.bkalbitz.bandcamp_item_detail.album.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bkalbitz.bandcamp_item_detail.album.persistance.AlbumEntity;
import com.bkalbitz.bandcamp_item_detail.album.persistance.RecommendationEnitity;
import com.bkalbitz.bandcamp_item_detail.album.persistance.RecommendationId;

class AlbumDTOTest {

  @Test
  void createByEntities() {
    RecommendationEnitity enitity1 = new RecommendationEnitity(new RecommendationId("forAlbumUrl1", "albumUrl1"),
        "artUrl1");
    RecommendationEnitity enitity2 = new RecommendationEnitity(new RecommendationId("forAlbumUrl2", "albumUrl2"),
        "artUrl2");

    AlbumEntity albumEntity = new AlbumEntity();
    albumEntity.setCollectionUrls("url1;url2");
    albumEntity.setTags("tag1;tag2");
    albumEntity.setUrl("url");

    AlbumDTO underTest = new AlbumDTO(albumEntity, List.of(enitity1, enitity2));
    assertEquals(List.of("url1", "url2"), underTest.getCollectionUrls());
    assertEquals(List.of("tag1", "tag2"), underTest.getTags());
    assertEquals("url", underTest.getUrl());
    List<ReommendedAlbumDTO> recomendetAlbums = new ArrayList<>(underTest.getRecomendetAlbums());
    assertEquals("artUrl1", recomendetAlbums.get(0).getArtUrl());
    assertEquals("albumUrl1", recomendetAlbums.get(0).getUrl());
    assertEquals("artUrl2", recomendetAlbums.get(1).getArtUrl());
    assertEquals("albumUrl2", recomendetAlbums.get(1).getUrl());
  }

}
