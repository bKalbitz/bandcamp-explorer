package com.bkalbitz.bandcamp_collection_explorer.collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/collection-intersection")
public class CollectionIntersectionEndpoint {

  private final CollectionService service;

  public CollectionIntersectionEndpoint(CollectionService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<IntersectionDTO> getUserById(@RequestParam String[] name) {
    return ResponseEntity.ok(service.getIntersection(name));
  }

}
