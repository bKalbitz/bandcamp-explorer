package com.bkalbitz.bandcamp_collection_intersection.intersection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkalbitz.bandcamp_collection_intersection.intersection.data.AlbumDTO;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.ArtistDTO;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.CollectionDTO;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.IntersectionDTO;
import com.bkalbitz.bandcamp_collection_intersection.intersection.data.IntersectionEntryDTO;

@Service
public class IntersectionService {

  private static final Map<Set<String>, State> STATE_MAP = new HashMap<>();
  private static final Map<Integer, Set<String>> ID_Map = new HashMap<>();
  private static int currentId = 0;

  @Autowired
  private AsyncLoaderService asyncService;
  @Autowired
  private CollectionService collectionService;

  private synchronized int registerState(Set<String> searched) {
    if (STATE_MAP.containsKey(searched)) {
      return STATE_MAP.get(searched).getId();
    }
    State state = new State(++currentId, searched);
    STATE_MAP.put(searched, state);
    ID_Map.put(state.getId(), searched);
    return state.getId();
  }

  public int loadIntersection(String[] names) {
    Set<String> nameSet = new HashSet<>(Arrays.asList(names));
    if (STATE_MAP.containsKey(nameSet)) {
      return STATE_MAP.get(nameSet).getId();
    }
    int id = registerState(nameSet);
    this.asyncService.loadCollectionAsync(nameSet, STATE_MAP.get(nameSet));
    return id;
  }

  public State getLoadingState(Integer id) {
    Set<String> names = ID_Map.get(id);
    return names == null ? null : STATE_MAP.get(names);
  }

  public IntersectionDTO getResult(Integer id) {
    State state = getLoadingState(id);
    if (state == null) {
      return null;
    }
    Set<String> names = state.getLoaded();
    Map<AlbumDTO, List<String>> collectionMap = new HashMap<>(names.size() * 100);
    for (String name : names) {
      CollectionDTO collection = collectionService.getCollection(name);
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

  public final static class State {
    private final Set<String> all = new HashSet<String>();
    private final Set<String> loaded = new HashSet<String>();
    private final Set<String> toLoad = new HashSet<String>();
    private String state = "pending";
    private final int id;

    public State(int id, Set<String> all) {
      this.id = id;
      this.all.addAll(all);
      this.toLoad.addAll(all);
    }

    public void entryFinished(String name) {
      loaded.add(name);
      toLoad.remove(name);
      if (toLoad.isEmpty()) {
        state = "done";
      }
    }

    public Set<String> getAll() {
      return all;
    }

    public Set<String> getLoaded() {
      return loaded;
    }

    public Set<String> getToLoad() {
      return toLoad;
    }

    public String getState() {
      return state;
    }

    public int getId() {
      return id;
    }

  }
}
