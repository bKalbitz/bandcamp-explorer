package com.bkalbitz.bandcamp_collection_explorer.collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bkalbitz.bandcamp_collection_explorer.collection.data.CollectionDTO;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/collection")
public class CollectionEndpoint {

  private final CollectionService service;

  public CollectionEndpoint(CollectionService service) {
    this.service = service;
  }

  @GetMapping("/{name}")
  public ResponseEntity<CollectionDTO> getUserById(@PathVariable String name,
      @RequestParam(required = false) String search) {
    CollectionDTO updateCollection = StringUtils.isBlank(search) ? service.getCollection(name)
        : service.updateCollection(name, search);
    return updateCollection != null ? ResponseEntity.ok(updateCollection) : ResponseEntity.notFound().build();
  }

}
