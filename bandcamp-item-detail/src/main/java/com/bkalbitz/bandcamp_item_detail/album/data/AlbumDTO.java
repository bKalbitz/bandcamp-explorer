package com.bkalbitz.bandcamp_item_detail.album.data;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.bkalbitz.bandcamp_item_detail.album.persistance.AlbumEntity;
import com.bkalbitz.bandcamp_item_detail.album.persistance.RecommendationEnitity;

public class AlbumDTO {
  private String url;
  private Collection<String> collectionUrls;
  private Collection<String> tags;
  private Collection<ReommendedAlbumDTO> recomendetAlbums;

  public AlbumDTO() {
  }

  public AlbumDTO(AlbumEntity entity, Collection<RecommendationEnitity> recommendations) {
    this.url = entity.getUrl();
    collectionUrls = Arrays
        .asList(StringUtils.splitByWholeSeparator(entity.getCollectionUrls(), AlbumEntity.URL_SEPERATOR));
    tags = Arrays.asList(StringUtils.splitByWholeSeparator(entity.getTags(), AlbumEntity.URL_SEPERATOR));
    recomendetAlbums = recommendations.stream().map(e -> new ReommendedAlbumDTO(e.getId().getAlbumUrl(), e.getArtUrl()))
        .toList();
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Collection<String> getCollectionUrls() {
    return collectionUrls;
  }

  public void setCollectionUrls(Collection<String> collectionUrls) {
    this.collectionUrls = collectionUrls;
  }

  public Collection<String> getTags() {
    return tags;
  }

  public void setTags(Collection<String> tags) {
    this.tags = tags;
  }

  public Collection<ReommendedAlbumDTO> getRecomendetAlbums() {
    return recomendetAlbums;
  }

  public void setRecomendetAlbums(Collection<ReommendedAlbumDTO> recomendetAlbums) {
    this.recomendetAlbums = recomendetAlbums;
  }

}
