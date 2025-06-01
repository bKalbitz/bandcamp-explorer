import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CollectionAlbum } from '../collection-data';
import { ItemDetailService } from '../item-detail.service';
import { AlbumData } from '../item-data';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ButtonModule } from 'primeng/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-album-detail',
  imports: [ProgressSpinnerModule, ButtonModule],
  templateUrl: './album-detail.component.html',
  styleUrl: './album-detail.component.css'
})
export class AlbumDetailComponent implements OnChanges {
  @Input() albumData: CollectionAlbum | undefined;
  @Input() collectionName: string | undefined;
  dataReady: boolean = false;
  albumDetails: AlbumData | undefined
  tagSearchLink: string = ''

  constructor(private router: Router,
    private serive: ItemDetailService){}
  
  ngOnChanges(changes: SimpleChanges): void {
    const currentValue = changes['albumData']?.currentValue;
    const url = currentValue ? currentValue['urls'][0] : undefined;
    if (typeof url === 'string') {
      this.serive.getAlbumData(url).then(e => {
        this.albumDetails = e;
        this.tagSearchLink = this.albumDetails.tags && this.albumDetails.tags.length > 0 ? `https://bandcamp.com/discover/${this.albumDetails.tags.join('+')}` : '';
        this.dataReady = true;
      });
    }
  }

  intersection(): void {
    if(!this.albumDetails || this.albumDetails.collectionUrls.length < 2) {
      return;
    }
    const cut = 'https://bandcamp.com/'
    let names = [];
    for(const url of this.albumDetails.collectionUrls) {
      names.push(url.substring(cut.length));
    }
    this.router.navigate(['/collection-intersection'],{ queryParams: {currentCollection: this.collectionName, name: names} });
  }
}
