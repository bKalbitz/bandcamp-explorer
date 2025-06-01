import { Injectable } from '@angular/core';
import { CollectionData, CollectionIntersection } from './collection-data';

@Injectable({
  providedIn: 'root'
})
export class CollectionExplorerService {
  url = 'http://localhost:8080/api/collection';
  urlIntersection = 'http://localhost:8080/api/collection-intersection?name=';

  constructor() { }

  async getCollectionData(name: string): Promise<CollectionData> {
    const data = await fetch(`${this.url}/${name}`);
    return await data.json() ?? {};
  }

  async searchCollectionData(name: string, search: string): Promise<CollectionData> {
    const data = await fetch(`${this.url}/${name}?search=${search}`);
    return await data.json() ?? {};
  }

  async getIntersection(names: string[]): Promise<CollectionIntersection>{
    let endpoint = `${this.urlIntersection}${names.join('&name=')}`;
    const data = await fetch(endpoint);
    return await data.json() ?? {};
  }
}
