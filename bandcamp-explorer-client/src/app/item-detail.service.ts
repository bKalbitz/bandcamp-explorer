import { Injectable } from '@angular/core';
import { AlbumData } from './item-data';

@Injectable({
  providedIn: 'root'
})
export class ItemDetailService {
  url = 'http://localhost:8081/api/album?bcUrl='
  constructor() { }

  
    async getAlbumData(albumUrl: string): Promise<AlbumData> {
      const data = await fetch(`${this.url}${albumUrl}`);
      return await data.json() ?? {};
    }
}
