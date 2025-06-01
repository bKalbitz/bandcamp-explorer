package com.bkalbitz.bandcamp_collection_explorer.collection.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumEntity;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntity;

@Service
public class CollectionBuilder {
  public CollectionDTO build(CollectionEntity collectionEntity, Collection<AlbumEntity> albumEntities) {
    CollectionDTO result = new CollectionDTO(collectionEntity);
    Map<String, ArtistDTO> artistMap = new HashMap<>();
    for (AlbumEntity albumEntity : albumEntities) {
      AlbumDTO albumDTO = new AlbumDTO(albumEntity);
      ArtistDTO artist = artistMap.get(albumDTO.getArtist());
      if (artist == null) {
        artist = new ArtistDTO(albumDTO.getArtist());
        artistMap.put(albumDTO.getArtist(), artist);
      }
      artist.addAlbum(albumDTO);
    }
    result.setArtists(artistMap.values().stream().sorted((a1, a2) -> a1.getName().compareTo(a2.getName())).toList());
    return result;
  }
}
