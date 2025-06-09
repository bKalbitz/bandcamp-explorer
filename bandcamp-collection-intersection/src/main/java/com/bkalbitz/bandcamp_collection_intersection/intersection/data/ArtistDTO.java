package com.bkalbitz.bandcamp_collection_intersection.intersection.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class ArtistDTO {

  private static final String URL_ENDS = ".bandcamp.com";
  private String name;
  private Set<String> urls = new TreeSet<String>();
  private List<AlbumDTO> albums = new LinkedList<AlbumDTO>();

  public ArtistDTO() {
  }

  public ArtistDTO(String name) {
    this.name = name;
  }

  void addAlbum(AlbumDTO albumDTO) {
    albums.add(albumDTO);
    albums.sort((a1, a2) -> a1.getTitle().compareTo(a2.getTitle()));
    for (String url : albumDTO.getUrls()) {
      if (StringUtils.contains(url, URL_ENDS)) {
        urls.add(StringUtils.substring(url, 0, StringUtils.indexOf(url, URL_ENDS) + URL_ENDS.length()));
      }
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<String> getUrls() {
    return Set.copyOf(urls);
  }

  public void setUrls(Set<String> urls) {
    this.urls = new HashSet<>(urls);
  }

  public Collection<AlbumDTO> getAlbums() {
    return List.copyOf(albums);
  }

  public void setAlbums(Collection<AlbumDTO> albums) {
    this.albums = new ArrayList<>(albums);
  }

}
