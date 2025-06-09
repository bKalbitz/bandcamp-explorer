package com.bkalbitz.bandcamp_collection_intersection.intersection.data;

import java.util.Set;

import com.bkalbitz.bandcamp_collection_intersection.intersection.IntersectionService.State;

public class CollectionIntersectionLoadingStateDTO {
  private Set<String> all;
  private Set<String> loaded;
  private Set<String> toLoad;
  private String state;
  private int relativeCompletion;
  private String location;

  public CollectionIntersectionLoadingStateDTO() {
  }

  public CollectionIntersectionLoadingStateDTO(State state, String location) {
    this.location = location;
    all = Set.copyOf(state.getAll());
    loaded = Set.copyOf(state.getLoaded());
    toLoad = Set.copyOf(state.getToLoad());
    this.state = state.getState();
    relativeCompletion = loaded.size() * 100 / all.size();
  }

  public Set<String> getAll() {
    return all;
  }

  public void setAll(Set<String> all) {
    this.all = Set.copyOf(all);
  }

  public Set<String> getLoaded() {
    return loaded;
  }

  public void setLoaded(Set<String> loaded) {
    this.loaded = Set.copyOf(loaded);
  }

  public Set<String> getToLoad() {
    return toLoad;
  }

  public void setToLoad(Set<String> toLoad) {
    this.toLoad = Set.copyOf(toLoad);
  }

  public int getRelativeCompletion() {
    return relativeCompletion;
  }

  public void setRelativeCompletion(int relativeCompletion) {
    this.relativeCompletion = relativeCompletion;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

}
