package com.bkalbitz.bandcamp_collection_explorer.collection.persistance;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Collection")
@Entity
public class CollectionEntity {

  @Id
  private String name;

  private String fanId;

  private Long lastUpdate;

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

  public Long getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Long lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}
