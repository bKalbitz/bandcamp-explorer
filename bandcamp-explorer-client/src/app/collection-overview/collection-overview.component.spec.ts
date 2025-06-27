import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionOverviewComponent } from './collection-overview.component';
import { ActivatedRoute, provideRouter } from '@angular/router';
import { routes } from '../app.routes';
import { By } from '@angular/platform-browser';
import { CollectionExplorerService } from '../collection-explorer.service';
import { of } from 'rxjs';

describe('CollectionOverviewComponent', () => {
  let component: CollectionOverviewComponent;
  let fixture: ComponentFixture<CollectionOverviewComponent>;
  let data = {
    "name": "test",
    "fanId": "1234567",
    "lastUpdate": "08 06 2025 20:49:07 CEST",
    "artists": [
        {
            "name": "artist-1",
            "urls": [
                "https://artist-1.bandcamp.com"
            ],
            "albums": [
                {
                    "title": "album-1",
                    "artist": "artist-1",
                    "type": "album",
                    "urls": [
                        "https://artist-1.bandcamp.com/album/album-1"
                    ],
                    "artUrl": "https://placehold.co/600x400"
                },
				        {
                    "title": "album-2",
                    "artist": "artist-1",
                    "type": "album",
                    "urls": [
                        "https://artist-1.bandcamp.com/album/album-2"
                    ],
                    "artUrl": "https://placehold.co/600x400"
                }
            ]
        },
        {
            "name": "artist-2",
            "urls": [
                "https://artist-2.bandcamp.com"
            ],
            "albums": [
                {
                    "title": "album-1",
                    "artist": "artist-2",
                    "type": "album",
                    "urls": [
                        "https://artist-2.bandcamp.com/album/album-1"
                    ],
                    "artUrl": "https://placehold.co/600x400"
                }
            ]
        }
    ]
};

  beforeEach(async () => {
    const service = new CollectionExplorerService();
    spyOn(service, 'getCollectionData').and.resolveTo(data);
    await TestBed.configureTestingModule({
      imports: [CollectionOverviewComponent],
      providers: [provideRouter(routes), {
        provide: ActivatedRoute,
        useValue: {
          snapshot: {
            params: {
              name: 'test'
            }
          }
        }
      },
      {
         provide: CollectionExplorerService,
         useValue: service
      }
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollectionOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('has headline', () => {
    const h1 = fixture.debugElement.query(By.css('h1'));
    expect(h1).toBeTruthy();
    expect(h1.nativeElement.innerText).toBe('Your collection:');
  });

  it('collection name set', () => {
    expect(component.collectionName).toBe('test');
  });

  it('should have 2 arists', () => {
    expect(component.artistCount).toBe(2);
  });

  it('should have 3 albums', () => {
    expect(component.albumCount).toBe(3);
  });

  it('should have sum text', () => {
    fixture.detectChanges(); // need to call this, that the test runs through. Need to be investigated if this is a concurrence problem.
    const p = fixture.debugElement.query(By.css('p, .bc-ce-text'));
    expect(p).toBeTruthy();
    expect(p.nativeElement.innerText).toBe('2 atrists and 3 albums found.');
  });

  it('should have collection tree node', () => {
    fixture.detectChanges();
    const collectionNode = fixture.debugElement.query(By.css('li[data-id="collection-test"]'));
    expect(collectionNode).toBeTruthy();
    expect(collectionNode.nativeElement.innerText).toBe('test');
  });

  it('should have 2 artist nodes after clicking the collection node', () => {
    fixture.detectChanges();
    const collectionNode = fixture.debugElement.query(By.css('li[data-id="collection-test"]'));
    expect(collectionNode).toBeTruthy();
    let aristNode = fixture.debugElement.query(By.css('li[data-id="artist-artist-1"]'));
    expect(aristNode).toBeFalsy();
    collectionNode.query(By.css('button')).triggerEventHandler('click');
    fixture.detectChanges();
    aristNode = fixture.debugElement.query(By.css('li[data-id="artist-artist-1"]'));
    expect(aristNode).toBeTruthy();
    expect(aristNode.nativeElement.innerText).toBe('artist-1');
    aristNode = fixture.debugElement.query(By.css('li[data-id="artist-artist-2"]'));
    expect(aristNode).toBeTruthy();
    expect(aristNode.nativeElement.innerText).toBe('artist-2');
  });

  it('arist 1 should have 2 albums', () => {
    fixture.detectChanges();
    const collectionNode = fixture.debugElement.query(By.css('li[data-id="collection-test"]'));
    expect(collectionNode).toBeTruthy();
    collectionNode.query(By.css('button')).triggerEventHandler('click');
    fixture.detectChanges();
    const aristNode = fixture.debugElement.query(By.css('li[data-id="artist-artist-1"]'));
    expect(aristNode).toBeTruthy();
    aristNode.query(By.css('button')).triggerEventHandler('click');
    fixture.detectChanges();
    let albumNode = fixture.debugElement.query(By.css('li[data-id="artist-artist-1-album-album-1"]'));
    expect(albumNode).toBeTruthy();
    expect(albumNode.nativeElement.innerText).toBe('album-1');
    albumNode = fixture.debugElement.query(By.css('li[data-id="artist-artist-1-album-album-2"]'));
    expect(albumNode).toBeTruthy();
    expect(albumNode.nativeElement.innerText).toBe('album-2');
  });

    it('arist 2 should have 1 albums', () => {
    fixture.detectChanges();
    const collectionNode = fixture.debugElement.query(By.css('li[data-id="collection-test"]'));
    expect(collectionNode).toBeTruthy();
    collectionNode.query(By.css('button')).triggerEventHandler('click');
    fixture.detectChanges();
    const aristNode = fixture.debugElement.query(By.css('li[data-id="artist-artist-2"]'));
    expect(aristNode).toBeTruthy();
    aristNode.query(By.css('button')).triggerEventHandler('click');
    fixture.detectChanges();
    let albumNode = fixture.debugElement.query(By.css('li[data-id="artist-artist-2-album-album-1"]'));
    expect(albumNode).toBeTruthy();
    expect(albumNode.nativeElement.innerText).toBe('album-1');
  });
});
