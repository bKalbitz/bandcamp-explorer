import { Injectable } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NavigationDataService {
  currentUrl = '/';
  previewUrl: string | undefined;
  stateMap = new Map<string, NavigationData>();
  constructor( private route : Router) {
     route.events.subscribe(e => {
      if(e instanceof NavigationEnd) {
        this.previewUrl = this.currentUrl;
        this.currentUrl = e.url;
      }
     });
   }

   registerState<T = any>(data: T): void {
    this.stateMap.set(this.currentUrl, {
      url: this.currentUrl,
      data: data
    });
   }

   getCurrentState(): NavigationData {
    const state = this.stateMap.get(this.currentUrl);
    return state ? state :  {
      url: this.currentUrl,
      data: undefined
    };
   }
}

export interface NavigationData<T = any> {
  url: string;
  data?: T
}
