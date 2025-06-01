package com.bkalbitz.bandcamp_collection_explorer.collection.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionEntryRepository extends JpaRepository<CollectionEntryEntity, CollectionEntryId> {

}
