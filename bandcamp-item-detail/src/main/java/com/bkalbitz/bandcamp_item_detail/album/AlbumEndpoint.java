package com.bkalbitz.bandcamp_item_detail.album;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bkalbitz.bandcamp_item_detail.album.data.AlbumDTO;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/album")
public class AlbumEndpoint {

  private final AlbumService service;

  public AlbumEndpoint(AlbumService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<AlbumDTO> getAlbumDetail(@RequestParam String bcUrl) {
    AlbumDTO result = service.getAlbum(bcUrl);
    return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
  }
}
