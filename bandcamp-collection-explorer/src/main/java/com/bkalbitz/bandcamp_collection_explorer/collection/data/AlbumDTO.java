package com.bkalbitz.bandcamp_collection_explorer.collection.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumEntity;

public class AlbumDTO {

  private String title;
  private String artist;
  private String type;
  private Collection<String> urls;
  private String artUrl;

  public AlbumDTO() {

  }

  public AlbumDTO(AlbumEntity entity) {
    title = entity.getAlbumId().getTitle();
    artist = entity.getAlbumId().getBand();
    type = entity.getType();
    artUrl = entity.getArtUrl();
    urls = Arrays.asList(StringUtils.splitByWholeSeparator(entity.getUrls(), AlbumEntity.URL_SEPERATOR));
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Collection<String> getUrls() {
    return urls;
  }

  public void setUrls(Collection<String> urls) {
    this.urls = urls;
  }

  public String getArtUrl() {
    return artUrl;
  }

  public void setArtUrl(String artUrl) {
    this.artUrl = artUrl;
  }

  @Override
  public int hashCode() {
    return Objects.hash(artist, title);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AlbumDTO other = (AlbumDTO) obj;
    return Objects.equals(artist, other.artist) && Objects.equals(title, other.title);
  }

}
