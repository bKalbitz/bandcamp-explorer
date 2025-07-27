import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlbumDetailComponent } from './album-detail.component';
import { ItemDetailService } from '../item-detail.service';
import { provideRouter } from '@angular/router';
import { routes } from '../app.routes';
import { By } from '@angular/platform-browser';
import { DataView } from 'primeng/dataview';
import { SimpleChange } from '@angular/core';

describe('AlbumDetailComponent', () => {
  let data = {
        "url": "https://artist.bandcamp.com/album/album-1",
        "collectionUrls": [
            "https://bandcamp.com/collection1",
            "https://bandcamp.com/collection2",
            "https://bandcamp.com/collection3"
        ],
        "tags": [
            "tag1",
            "tag2",
            "tag3"
        ],
        "recomendetAlbums": [
            {
                "url": "https://artist-a.bandcamp.com/album/album-a1",
                "artUrl": "https://placehold.co/600x400"
            },
            {
                "url": "https://artist-b.bandcamp.com/album/album-b1",
                "artUrl": "hhttps://placehold.co/600x400"
            },
            {
                "url": "https://artist-c.bandcamp.com/album/album-c1",
                "artUrl": "https://placehold.co/600x400"
            }
        ]
    };
  let collectionAlbumData =  {
        "title": "album-1",
        "artist": "artist-1",
        "type": "album",
        "urls": [
            "https://artist-1.bandcamp.com/album/album-1"
        ],
        "artUrl": "https://placehold.co/600x400"
    };
  let component: AlbumDetailComponent;
  let fixture: ComponentFixture<AlbumDetailComponent>;

  beforeEach(async () => {
    const service = new ItemDetailService();
    spyOn(service, 'getAlbumData').and.resolveTo(data);
    await TestBed.configureTestingModule({
      imports: [AlbumDetailComponent],
       providers: [provideRouter(routes),
      {
         provide: ItemDetailService,
         useValue: service
      }
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlbumDetailComponent);
    component = fixture.componentInstance;
    component.collectionName = 'collection2';
    component.albumData = collectionAlbumData;
    component.ngOnChanges({
      albumData: new SimpleChange(null, collectionAlbumData, true),
      });
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('has headline', () => {
    const h1 = fixture.debugElement.query(By.css('h3'));
    expect(h1).toBeTruthy();
    expect(h1.nativeElement.innerText).toBe('album-1');
  });

  it('should have album art', () => {
    const div = fixture.debugElement.query(By.css('div, .bc-ce-album-detail-image'));
    expect(div).toBeTruthy();
    const img = div.query(By.css('img'));
    expect(img).toBeTruthy();
    expect(img.nativeElement.attributes['alt']?.value).toBe('Album cover: album-1');
    expect(img.nativeElement.attributes['src']?.value).toBe('https://placehold.co/600x400');
  });

  it('should have one url', () => {
    const urlsDiv = fixture.debugElement.query(By.css('div, .bc-ce-album-detail-urls'));
    expect(urlsDiv).toBeTruthy();
    const links = urlsDiv.queryAll(By.css('a'));
    expect(links.length).toBe(1);
    expect(links[0].nativeElement.attributes['href']?.value).toBe('https://artist-1.bandcamp.com/album/album-1');
  });

  it('should be in three collections', () => {
    fixture.detectChanges();
    expect(component.dataReady).toBeTruthy();
    const collectionsDiv = fixture.debugElement.query(By.css('div.bc-ce-album-detail-collections.bc-ce-flex-row'));
    expect(collectionsDiv).toBeTruthy();
    const links = collectionsDiv.queryAll(By.css('a'));
    expect(links.length).toBe(3);
    expect(links[0].nativeElement.innerText).toBe('https://bandcamp.com/collection1');
    expect(links[1].nativeElement.innerText).toBe('https://bandcamp.com/collection2');
    expect(links[2].nativeElement.innerText).toBe('https://bandcamp.com/collection3');
  });

  it('should have three tags', () => {
    fixture.detectChanges();
    expect(component.dataReady).toBeTruthy();
    const tagDiv = fixture.debugElement.query(By.css('div.bc-ce-album-detail-tags'));
    expect(tagDiv).toBeTruthy();
    const tags = tagDiv.queryAll(By.css('div.bc-ce-tag'));
    expect(tags.length).toBe(3);
    expect(tags[0].nativeElement.innerText).toBe('tag1');
    expect(tags[1].nativeElement.innerText).toBe('tag2');
    expect(tags[2].nativeElement.innerText).toBe('tag3');

    const link = tagDiv.query(By.css('a'));
    expect(link).toBeTruthy();
    expect(link.nativeElement.attributes['href']?.value).toBe('https://bandcamp.com/discover/tag1+tag2+tag3');
  });

  it('has three recommended albums', () => {
    fixture.detectChanges();
    expect(component.dataReady).toBeTruthy();
    const recommendedDiv = fixture.debugElement.query(By.css('div.bc-ce-album-detail-recommended.bc-ce-flex-row'));
    expect(recommendedDiv).toBeTruthy();
    const links = recommendedDiv.queryAll(By.css('a'));
    expect(links.length).toBe(3);
    expect(links[0].nativeElement.attributes['href']?.value).toBe('https://artist-a.bandcamp.com/album/album-a1');
    expect(links[1].nativeElement.attributes['href']?.value).toBe('https://artist-b.bandcamp.com/album/album-b1');
    expect(links[2].nativeElement.attributes['href']?.value).toBe('https://artist-c.bandcamp.com/album/album-c1');
  });

});
