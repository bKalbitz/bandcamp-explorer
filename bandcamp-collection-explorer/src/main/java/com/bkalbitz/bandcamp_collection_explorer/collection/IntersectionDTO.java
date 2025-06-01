package com.bkalbitz.bandcamp_collection_explorer.collection;

import java.util.List;

public class IntersectionDTO {
  List<IntersectionEntryDTO> entries;

  public IntersectionDTO() {
  }

  public IntersectionDTO(List<IntersectionEntryDTO> entries) {
    this.entries = entries;
  }

  public List<IntersectionEntryDTO> getEntries() {
    return entries;
  }

  public void setEntries(List<IntersectionEntryDTO> entries) {
    this.entries = entries;
  }

}
