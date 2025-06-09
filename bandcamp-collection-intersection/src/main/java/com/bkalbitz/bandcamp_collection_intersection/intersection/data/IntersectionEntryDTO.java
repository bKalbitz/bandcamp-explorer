package com.bkalbitz.bandcamp_collection_intersection.intersection.data;

import java.util.List;

public class IntersectionEntryDTO {
  private AlbumDTO album;
  private List<String> inCollections;

  public IntersectionEntryDTO() {
  }

  public IntersectionEntryDTO(AlbumDTO album, List<String> inCollections) {
    this.album = album;
    this.inCollections = inCollections;
  }

  public AlbumDTO getAlbum() {
    return album;
  }

  public void setAlbum(AlbumDTO album) {
    this.album = album;
  }

  public List<String> getInCollections() {
    return inCollections;
  }

  public void setInCollections(List<String> inCollections) {
    this.inCollections = inCollections;
  }

}
