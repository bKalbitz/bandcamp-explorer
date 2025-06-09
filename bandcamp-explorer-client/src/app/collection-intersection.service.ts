import { Injectable } from '@angular/core';
import { CollectionIntersection, CollectionIntersectionLoadingStarted, CollectionIntersectionLoadingState } from './collection-data';

@Injectable({
  providedIn: 'root'
})
export class CollectionIntersectionService {

  host = 'http://localhost:8082';
  urlIntersection = `${this.host}/api/collection-intersection?name=`;

  constructor() { }

   async loadIntersection(names: string[]): Promise<CollectionIntersectionLoadingStarted>{
      let endpoint = `${this.urlIntersection}${names.join('&name=')}`;
      const data = await fetch(endpoint);
      return await data.json() ?? {};
    }
  
    async getIntersectionLoadingState(from: CollectionIntersectionLoadingStarted): Promise<CollectionIntersectionLoadingState> {
      let endpoint = `${this.host}${from.location}`;
      const data = await fetch(endpoint);
      return await data.json() ?? {};
    }
  
    async getIntersectionResult(from: CollectionIntersectionLoadingState): Promise<CollectionIntersection> {
      let endpoint = `${this.host}${from.location}`;
      const data = await fetch(endpoint);
      return await data.json() ?? {};
    }
}
