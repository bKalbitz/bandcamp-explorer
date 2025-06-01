package com.bkalbitz.bandcamp_collection_explorer.collection.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntity;

public class CollectionDTO {
  static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MM yyyy HH:mm:ss zzz");

  private String name;
  private String fanId;
  private String lastUpdate;
  private List<ArtistDTO> artists;

  public CollectionDTO() {
    // default constructor;
  }

  public CollectionDTO(CollectionEntity entity) {
    this.name = entity.getName();
    this.fanId = entity.getFanId();
    this.lastUpdate = DATE_FORMAT.format(new Date(entity.getLastUpdate()));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFanId() {
    return fanId;
  }

  public void setFanId(String fanId) {
    this.fanId = fanId;
  }

  public String getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(String lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public Collection<ArtistDTO> getArtists() {
    return List.copyOf(artists);
  }

  public void setArtists(Collection<ArtistDTO> artists) {
    this.artists = new ArrayList<>(artists);
  }

}
