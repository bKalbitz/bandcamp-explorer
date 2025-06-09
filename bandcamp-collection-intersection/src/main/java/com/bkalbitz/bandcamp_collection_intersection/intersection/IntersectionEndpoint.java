package com.bkalbitz.bandcamp_collection_intersection.intersection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bkalbitz.bandcamp_collection_intersection.intersection.IntersectionService.State;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.CollectionIntersectionLoadingStartedDTO;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.CollectionIntersectionLoadingStateDTO;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.IntersectionDTO;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/collection-intersection")
public class IntersectionEndpoint {

  private final IntersectionService service;

  public IntersectionEndpoint(IntersectionService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<CollectionIntersectionLoadingStartedDTO> loadIntersection(@RequestParam String[] name) {
    int id = service.loadIntersection(name);
    return ResponseEntity.status(202)
        .body(new CollectionIntersectionLoadingStartedDTO(id, "/api/collection-intersection/loading-state/" + id));
  }

  @GetMapping("/loading-state/{id}")
  public ResponseEntity<CollectionIntersectionLoadingStateDTO> getState(@PathVariable Integer id) {
    State loadingState = service.getLoadingState(id);
    return loadingState != null
        ? ResponseEntity.status(202)
            .body(new CollectionIntersectionLoadingStateDTO(loadingState, "/api/collection-intersection/result/" + id))
        : ResponseEntity.notFound().build();
  }

  @GetMapping("/result/{id}")
  public ResponseEntity<IntersectionDTO> getResult(@PathVariable Integer id) {
    IntersectionDTO result = service.getResult(id);
    return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
  }

}
