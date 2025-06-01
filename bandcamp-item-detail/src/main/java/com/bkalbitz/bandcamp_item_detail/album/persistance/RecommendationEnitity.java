package com.bkalbitz.bandcamp_item_detail.album.persistance;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "CollectionEntry")
@Entity
public class RecommendationEnitity {
  @EmbeddedId
  private RecommendationId id;
  private String artUrl;

  public RecommendationEnitity() {
  }

  public RecommendationEnitity(RecommendationId id, String artUrl) {
    this.id = id;
    this.artUrl = artUrl;
  }

  public RecommendationId getId() {
    return id;
  }

  public void setId(RecommendationId id) {
    this.id = id;
  }

  public String getArtUrl() {
    return artUrl;
  }

  public void setArtUrl(String artUrl) {
    this.artUrl = artUrl;
  }

}
