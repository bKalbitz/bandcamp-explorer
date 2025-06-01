package com.bkalbitz.bandcamp_item_detail.album.persistance;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecommendationRespository extends JpaRepository<RecommendationEnitity, RecommendationId> {

  //@formatter:off
  @Query("SELECT e FROM RecommendationEnitity e" 
      + " WHERE e.id.forAlbumUrl = ?1")
  // @formatter:on
  Collection<RecommendationEnitity> findAllForAlbumUrl(String url);
}
