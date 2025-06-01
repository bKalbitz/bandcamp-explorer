export interface CollectionData {
    name: string;
    fanId: string;
    artists: CollectionArtist[];
}

export interface CollectionArtist {
    name: string;
    urls: string[];
    albums: CollectionAlbum[];
}

export interface CollectionAlbum {
    title: string;
    artist: string;
    type: string;
    urls: string[];
    artUrl: string;
}

export interface CollectionIntersection {
    entries: CollectionIntersectionEntry[];
}

export interface CollectionIntersectionEntry {
    album: CollectionAlbum;
    inCollections: string[];
}