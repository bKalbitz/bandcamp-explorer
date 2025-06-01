package com.bkalbitz.bandcamp_collection_explorer.collection.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntity;

class CollectionDTOTest {

  @Test
  public void testCreateFromEntity() {
    CollectionEntity entity = new CollectionEntity();
    entity.setFanId("fanId");
    entity.setName("name");
    entity.setLastUpdate(0L);
    CollectionDTO underTest = new CollectionDTO(entity);
    assertEquals("fanId", underTest.getFanId());
    assertEquals("name", underTest.getName());
    assertEquals(CollectionDTO.DATE_FORMAT.format(new Date(0L)), underTest.getLastUpdate());
  }

}
