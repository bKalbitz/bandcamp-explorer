package com.bkalbitz.bandcamp_collection_explorer.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.bkalbitz.bandcamp_collection_explorer.collection.crawler.BCRestEndpoint;
import com.bkalbitz.bandcamp_collection_explorer.collection.crawler.BCWebCrawler;
import com.bkalbitz.bandcamp_collection_explorer.collection.data.AlbumDTO;
import com.bkalbitz.bandcamp_collection_explorer.collection.data.ArtistDTO;
import com.bkalbitz.bandcamp_collection_explorer.collection.data.CollectionBuilder;
import com.bkalbitz.bandcamp_collection_explorer.collection.data.CollectionDTO;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumEntity;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumId;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.AlbumRepository;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntity;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntryEntity;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntryId;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionEntryRepository;
import com.bkalbitz.bandcamp_collection_explorer.collection.persistance.CollectionRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

@Service
public class CollectionService {

  @Autowired
  private CollectionRepository collectionRepository;
  @Autowired
  private CollectionEntryRepository collectionEntryRepository;
  @Autowired
  private AlbumRepository albumRepository;
  @Autowired
  private BCWebCrawler bcWebsite;
  @Autowired
  private BCRestEndpoint bcAPI;
  @Autowired
  private CollectionBuilder collectionBuilder;

  public CollectionDTO updateCollection(String name, @Nullable String searchParam) {
    String fanId = bcWebsite.parseFanId(name);
    if (StringUtils.isBlank(fanId)) {
      return null;
    }
    CollectionEntity collectionEntity = persistCollection(name, fanId);
    JsonArray bcCollection = bcAPI.loadCollectionData(fanId, searchParam);
    if (bcCollection == null) {
      return null;
    }
    Collection<AlbumEntity> albumEntities = new ArrayList<AlbumEntity>(bcCollection.size());
    for (JsonElement tralbum : bcCollection) {
      JsonObject album = tralbum.getAsJsonObject();
      AlbumEntity albumEntity = persistAlbum(album);
      albumEntities.add(albumEntity);
      persistCollection(collectionEntity, albumEntity);
    }
    return collectionBuilder.build(collectionEntity, albumEntities);
  }

  private CollectionEntity persistCollection(String name, String fanId) {
    CollectionEntity collectionEntity = new CollectionEntity();
    collectionEntity.setFanId(fanId);
    collectionEntity.setName(name);
    collectionEntity.setLastUpdate(System.currentTimeMillis());
    return collectionRepository.save(collectionEntity);
  }

  private AlbumEntity persistAlbum(JsonObject album) {
    String type = getPropAsString(album, "item_type");
    String url = getPropAsString(album, "item_url");
    String title = getPropAsString(album, "album_title");
    if (StringUtils.isBlank(title)) {
      title = getPropAsString(album, "item_title");
    }
    String band = getPropAsString(album, "band_name");
    String art = getPropAsString(album, "item_art_url");
    return persistAlbum(type, url, title, band, art);
  }

  private AlbumEntity persistAlbum(String type, String url, String title, String band, String art) {
    AlbumId albumId = new AlbumId(title, band);
    AlbumEntity albumEntity;
    if (albumRepository.existsById(albumId)) {
      albumEntity = albumRepository.getReferenceById(albumId);
      if (!StringUtils.contains(albumEntity.getUrls(), url)) {

        List<String> urls = new ArrayList<>(
            Arrays.asList(StringUtils.split(albumEntity.getUrls(), AlbumEntity.URL_SEPERATOR)));
        urls.add(url);
        urls.sort(String::compareTo);
        albumEntity.setUrls(String.join(AlbumEntity.URL_SEPERATOR, urls));
      }
    } else {
      albumEntity = new AlbumEntity();
      albumEntity.setAlbumId(albumId);
      albumEntity.setUrls(url);
    }
    albumEntity.setType(type);
    albumEntity.setArtUrl(art);
    return albumRepository.save(albumEntity);
  }

  private CollectionEntryEntity persistCollection(CollectionEntity collectionEntity, AlbumEntity albumEntity) {
    CollectionEntryId collectionEntryId = new CollectionEntryId(collectionEntity.getName(),
        albumEntity.getAlbumId().getTitle(), albumEntity.getAlbumId().getBand());
    if (collectionEntryRepository.existsById(collectionEntryId)) {
      return collectionEntryRepository.getReferenceById(collectionEntryId);
    }
    CollectionEntryEntity collectionEntryEntity = new CollectionEntryEntity();
    collectionEntryEntity.setId(collectionEntryId);
    return collectionEntryRepository.save(collectionEntryEntity);
  }

  private String getPropAsString(JsonObject json, String name) {
    JsonElement jsonElement = json.get(name);
    return jsonElement == null || jsonElement instanceof JsonNull ? null : jsonElement.getAsString();
  }

  public CollectionDTO getCollection(String name) {
    if (!collectionRepository.existsById(name)) {
      return updateCollection(name, null);
    }
    CollectionEntity collectionEntity = collectionRepository.getReferenceById(name);
    Collection<AlbumEntity> albumEntities = albumRepository.findAllByCollection(name);
    return collectionBuilder.build(collectionEntity, albumEntities);
  }

  public IntersectionDTO getIntersection(String[] names) {
    Map<AlbumDTO, List<String>> collectionMap = new HashMap<>(names.length * 100);
    for (String name : names) {
      CollectionDTO collection = getCollection(name);
      if (collection != null) {
        for (ArtistDTO arist : collection.getArtists()) {
          for (AlbumDTO album : arist.getAlbums()) {
            if (!collectionMap.containsKey(album)) {
              collectionMap.put(album, new LinkedList<String>());
            }
            collectionMap.get(album).add(name);
          }
        }
      }
    }
    List<IntersectionEntryDTO> entries = collectionMap.entrySet().stream().filter(e -> e.getValue().size() > 1)
        .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
        .map(e -> new IntersectionEntryDTO(e.getKey(), e.getValue())).toList();

    return new IntersectionDTO(entries);
  }

}
