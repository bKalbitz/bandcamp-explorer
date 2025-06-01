package com.bkalbitz.bandcamp_item_detail.album.persistance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Album")
@Entity
public class AlbumEntity {

  public static final String URL_SEPERATOR = ";";

  @Id
  private String url;
  private String collectionUrls;
  private String tags;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCollectionUrls() {
    return collectionUrls;
  }

  public void setCollectionUrls(String collectionUrls) {
    this.collectionUrls = collectionUrls;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

}
