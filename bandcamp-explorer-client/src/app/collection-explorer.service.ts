import { Injectable } from '@angular/core';
import { CollectionData, CollectionIntersection, CollectionIntersectionLoadingStarted, CollectionIntersectionLoadingState } from './collection-data';

@Injectable({
  providedIn: 'root'
})
export class CollectionExplorerService {
  host = 'http://localhost:8080';
  url =  `${this.host}/api/collection`;

  constructor() { }

  async getCollectionData(name: string): Promise<CollectionData> {
    const data = await fetch(`${this.url}/${name}`);
    return await data.json() ?? {};
  }

  async searchCollectionData(name: string, search: string): Promise<CollectionData> {
    const data = await fetch(`${this.url}/${name}?search=${search}`);
    return await data.json() ?? {};
  }

}
