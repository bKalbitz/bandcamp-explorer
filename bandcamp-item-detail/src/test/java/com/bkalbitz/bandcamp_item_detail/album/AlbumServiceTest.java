package com.bkalbitz.bandcamp_item_detail.album;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bkalbitz.bandcamp_item_detail.album.data.AlbumDTO;
import com.bkalbitz.bandcamp_item_detail.album.data.ReommendedAlbumDTO;
import com.bkalbitz.bandcamp_item_detail.album.persistance.AlbumEntity;
import com.bkalbitz.bandcamp_item_detail.album.persistance.AlbumRepository;
import com.bkalbitz.bandcamp_item_detail.album.persistance.RecommendationEnitity;
import com.bkalbitz.bandcamp_item_detail.album.persistance.RecommendationId;
import com.bkalbitz.bandcamp_item_detail.album.persistance.RecommendationRespository;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

  @Mock
  private AlbumRepository albumRepository;
  @Mock
  private RecommendationRespository recommendationRespository;
  @InjectMocks
  private AlbumService underTest;

  @Test
  void getAlbum_persisted() {
    Mockito.when(albumRepository.existsById("test")).thenReturn(true);

    RecommendationEnitity enitity1 = new RecommendationEnitity(new RecommendationId("forAlbumUrl1", "albumUrl1"),
        "artUrl1");
    RecommendationEnitity enitity2 = new RecommendationEnitity(new RecommendationId("forAlbumUrl2", "albumUrl2"),
        "artUrl2");

    AlbumEntity albumEntity = new AlbumEntity();
    albumEntity.setCollectionUrls("url1;url2");
    albumEntity.setTags("tag1;tag2");
    albumEntity.setUrl("url");
    Mockito.when(albumRepository.getReferenceById("test")).thenReturn(albumEntity);
    Mockito.when(recommendationRespository.findAllForAlbumUrl("test")).thenReturn(List.of(enitity1, enitity2));

    AlbumDTO result = underTest.getAlbum("test");
    assertEquals(List.of("url1", "url2"), result.getCollectionUrls());
    assertEquals(List.of("tag1", "tag2"), result.getTags());
    assertEquals("url", result.getUrl());
    List<ReommendedAlbumDTO> recomendetAlbums = new ArrayList<>(result.getRecomendetAlbums());
    assertEquals("artUrl1", recomendetAlbums.get(0).getArtUrl());
    assertEquals("albumUrl1", recomendetAlbums.get(0).getUrl());
    assertEquals("artUrl2", recomendetAlbums.get(1).getArtUrl());
    assertEquals("albumUrl2", recomendetAlbums.get(1).getUrl());
  }
}
