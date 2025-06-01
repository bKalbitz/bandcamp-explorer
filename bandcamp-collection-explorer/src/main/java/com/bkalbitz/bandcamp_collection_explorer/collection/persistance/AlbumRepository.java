package com.bkalbitz.bandcamp_collection_explorer.collection.persistance;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, AlbumId> {

  //@formatter:off
  @Query("SELECT a FROM AlbumEntity a, CollectionEntryEntity e" 
      + " WHERE e.id.albumTitle = a.albumId.title"
      + " AND e.id.albumBand = a.albumId.band"
      + " AND e.id.collectionName = ?1")
  // @formatter:on
  Collection<AlbumEntity> findAllByCollection(String name);
}
