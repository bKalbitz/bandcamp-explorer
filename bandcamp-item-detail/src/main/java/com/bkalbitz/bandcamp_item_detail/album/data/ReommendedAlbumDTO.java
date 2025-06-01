package com.bkalbitz.bandcamp_item_detail.album.data;

public class ReommendedAlbumDTO {
  private String url;
  private String artUrl;

  public ReommendedAlbumDTO() {
  }

  public ReommendedAlbumDTO(String url, String artUrl) {
    this.url = url;
    this.artUrl = artUrl;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getArtUrl() {
    return artUrl;
  }

  public void setArtUrl(String artUrl) {
    this.artUrl = artUrl;
  }

}
