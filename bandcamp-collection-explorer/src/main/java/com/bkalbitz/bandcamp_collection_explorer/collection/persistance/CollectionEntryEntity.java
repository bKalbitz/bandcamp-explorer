package com.bkalbitz.bandcamp_collection_explorer.collection.persistance;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "CollectionEntry")
@Entity
public class CollectionEntryEntity {
  @EmbeddedId
  private CollectionEntryId id;

  public CollectionEntryId getId() {
    return id;
  }

  public void setId(CollectionEntryId id) {
    this.id = id;
  }

}
