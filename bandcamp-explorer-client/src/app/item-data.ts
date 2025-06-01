export interface AlbumData {
    collectionUrls: string[];
    tags: string[];
    recomendetAlbums: RecommendedAlbumData[];
}

export interface RecommendedAlbumData {
    url: string;
    artUrl: string;
}