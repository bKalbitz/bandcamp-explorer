package com.bkalbitz.bandcamp_collection_intersection.intersection.data;

public class CollectionIntersectionLoadingStartedDTO {
  private Integer id;
  private String location;

  public CollectionIntersectionLoadingStartedDTO() {
  }

  public CollectionIntersectionLoadingStartedDTO(Integer id, String location) {
    this.id = id;
    this.location = location;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}
