package com.bkalbitz.bandcamp_item_detail.album.persistance;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class RecommendationId implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String forAlbumUrl;
  private String albumUrl;

  public RecommendationId() {
  }

  public RecommendationId(String forAlbumUrl, String albumUrl) {
    this.forAlbumUrl = forAlbumUrl;
    this.albumUrl = albumUrl;
  }

  public String getForAlbumUrl() {
    return forAlbumUrl;
  }

  public void setForAlbumUrl(String forAlbumUrl) {
    this.forAlbumUrl = forAlbumUrl;
  }

  public String getAlbumUrl() {
    return albumUrl;
  }

  public void setAlbumUrl(String albumUrl) {
    this.albumUrl = albumUrl;
  }

  @Override
  public int hashCode() {
    return Objects.hash(albumUrl, forAlbumUrl);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RecommendationId other = (RecommendationId) obj;
    return Objects.equals(albumUrl, other.albumUrl) && Objects.equals(forAlbumUrl, other.forAlbumUrl);
  }

}
