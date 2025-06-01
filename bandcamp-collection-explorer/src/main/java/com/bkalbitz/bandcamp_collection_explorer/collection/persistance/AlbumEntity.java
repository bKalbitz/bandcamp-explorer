package com.bkalbitz.bandcamp_collection_explorer.collection.persistance;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "Album")
@Entity
public class AlbumEntity {

  public static final String URL_SEPERATOR = ";";

  @EmbeddedId
  private AlbumId albumId;
  private String type;
  private String urls;
  private String artUrl;

  public AlbumId getAlbumId() {
    return albumId;
  }

  public void setAlbumId(AlbumId albumId) {
    this.albumId = albumId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrls() {
    return urls;
  }

  public void setUrls(String urls) {
    this.urls = urls;
  }

  public String getArtUrl() {
    return artUrl;
  }

  public void setArtUrl(String artUrl) {
    this.artUrl = artUrl;
  }

}
