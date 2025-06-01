package com.bkalbitz.bandcamp_collection_explorer.collection.persistance;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class AlbumId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String title;
  private String band;

  public AlbumId() {
    super();
  }

  public AlbumId(String title, String band) {
    super();
    this.title = title;
    this.band = band;
  }

  public String getTitle() {
    return title;
  }

  public String getBand() {
    return band;
  }

  @Override
  public int hashCode() {
    return Objects.hash(band, title);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AlbumId other = (AlbumId) obj;
    return Objects.equals(band, other.band) && Objects.equals(title, other.title);
  }

}
