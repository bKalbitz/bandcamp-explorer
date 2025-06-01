package com.bkalbitz.bandcamp_collection_explorer.collection.persistance;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class CollectionEntryId implements Serializable {

  private static final long serialVersionUID = 1L;

  private String collectionName;
  private String albumTitle;
  private String albumBand;

  public CollectionEntryId() {
    super();
  }

  public CollectionEntryId(String collectionName, String albumTitle, String albumBand) {
    super();
    this.collectionName = collectionName;
    this.albumTitle = albumTitle;
    this.albumBand = albumBand;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public String getAlbumTitle() {
    return albumTitle;
  }

  public String getAlbumBand() {
    return albumBand;
  }

  @Override
  public int hashCode() {
    return Objects.hash(albumBand, albumTitle, collectionName);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CollectionEntryId other = (CollectionEntryId) obj;
    return Objects.equals(albumBand, other.albumBand) && Objects.equals(albumTitle, other.albumTitle)
        && Objects.equals(collectionName, other.collectionName);
  }

}
