import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionIntersectionComponent } from './collection-intersection.component';
import { ActivatedRoute, provideRouter } from '@angular/router';
import { CollectionIntersectionService } from '../collection-intersection.service';
import { By } from '@angular/platform-browser';
import { routes } from '../app.routes';

describe('CollectionIntersectionComponent', () => {

  let dataRequest = {
        "id": 1,
        "location": "/api/collection-intersection/loading-state/1"
    };
  
  let requestComplete = {
        "all": [
            "collection-1",
            "collection-2",
            "collection-3"
        ],
        "loaded": [
            "collection-1",
            "collection-2",
            "collection-3"
        ],
        "toLoad": [],
        "state": "done",
        "relativeCompletion": 100,
        "location": "/api/collection-intersection/result/1"
    };

  let collectionsData = {
        "entries": [
            {
                "album": {
                    "title": "album-1",
                    "artist": "artist-1",
                    "type": "album",
                    "urls": [
                        "https://arist-1.bandcamp.com/album/album-1"
                    ],
                    "artUrl": "https://placehold.co/600x400"
                },
                "inCollections": [
                    "collection-1",
                    "collection-2",
                    "collection-3"
                ]
            },
            {
                "album": {
                    "title": "album-2",
                    "artist": "artist-1",
                    "type": "album",
                    "urls": [
                        "https://arist-1.bandcamp.com/album/album-2"
                    ],
                    "artUrl": "https://placehold.co/600x400"
                },
                "inCollections": [
                    "collection-1",
                    "collection-3"
                ]
            },
            {
                "album": {
                    "title": "album-3",
                    "artist": "artist-2",
                    "type": "album",
                    "urls": [
                        "https://arist-2.bandcamp.com/album/album-3"
                    ],
                    "artUrl": "https://placehold.co/600x400"
                },
                "inCollections": [
                    "collection-2",
                    "collection-3"
                ]
            }
        ]
    };
  let component: CollectionIntersectionComponent;
  let fixture: ComponentFixture<CollectionIntersectionComponent>;

  beforeEach(async () => {
    let service = jasmine.createSpyObj('CollectionIntersectionService', ['loadIntersection', 'getIntersectionLoadingState', 'getIntersectionResult']);
    service.loadIntersection.and.returnValue(Promise.resolve(dataRequest));
    service.getIntersectionLoadingState.and.returnValue(Promise.resolve(requestComplete));
    service.getIntersectionResult.and.returnValue(Promise.resolve(collectionsData));

    await TestBed.configureTestingModule({
      imports: [CollectionIntersectionComponent],
      providers: [provideRouter(routes), {
        provide: ActivatedRoute,
        useValue: {
          snapshot: {
            queryParams: {
              currentColelction: 'collection-1',
              name: ['collection-1','collection-2','collection-3']
            }
          }
        }
      },
      {
         provide: CollectionIntersectionService,
         useValue: service
      }
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollectionIntersectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('has headline', () => {
    const h1 = fixture.debugElement.query(By.css('h1'));
    expect(h1).toBeTruthy();
    expect(h1.nativeElement.innerText).toBe('Collection Intersection');
  });

  it('has three entries in data', async () => {
     while(component.loadingState < 100) {
      await new Promise(resolve => setTimeout(resolve, 200));
    }
    await fixture.whenStable();
    expect(component.dataReady).toBeTruthy();
    expect(component.entries?.length).toBe(3);
  });


  it('should display three album entries when data is ready', async () => {
    while(component.loadingState < 100) {
      await new Promise(resolve => setTimeout(resolve, 200));
    }
    await fixture.whenStable();
    fixture.detectChanges();
    
    const albumEntries = fixture.debugElement.queryAll(By.css('.bc-ce-intersection-entry'));
    expect(albumEntries.length).toBe(3);

    let albumInfo = albumEntries[0].queryAll(By.css('.bc-ce-intersection-entry-album p'));
    expect(albumInfo[0].nativeElement.textContent).toBe('album-1');
    expect(albumInfo[1].nativeElement.textContent).toBe('by artist-1');

    let albumLink = albumEntries[0].query(By.css('a'));
    expect(albumLink.nativeElement.href).toBe('https://arist-1.bandcamp.com/album/album-1');

    let inCollection = albumEntries[0].queryAll(By.css('div.bc-ce-intersection-collections a'));
    expect(inCollection.length).toBe(3);
    expect(inCollection[0].nativeElement.textContent).toBe('collection-1');
    expect(inCollection[1].nativeElement.textContent).toBe('collection-2');
    expect(inCollection[2].nativeElement.textContent).toBe('collection-3');

    albumInfo = albumEntries[1].queryAll(By.css('.bc-ce-intersection-entry-album p'));
    expect(albumInfo[0].nativeElement.textContent).toBe('album-2');
    expect(albumInfo[1].nativeElement.textContent).toBe('by artist-1');

    albumLink = albumEntries[1].query(By.css('a'));
    expect(albumLink.nativeElement.href).toBe('https://arist-1.bandcamp.com/album/album-2');

    inCollection = albumEntries[1].queryAll(By.css('div.bc-ce-intersection-collections a'));
    expect(inCollection.length).toBe(2);
    expect(inCollection[0].nativeElement.textContent).toBe('collection-1');
    expect(inCollection[1].nativeElement.textContent).toBe('collection-3');


    albumInfo = albumEntries[2].queryAll(By.css('.bc-ce-intersection-entry-album p'));
    expect(albumInfo[0].nativeElement.textContent).toBe('album-3');
    expect(albumInfo[1].nativeElement.textContent).toBe('by artist-2');

    albumLink = albumEntries[2].query(By.css('a'));
    expect(albumLink.nativeElement.href).toBe('https://arist-2.bandcamp.com/album/album-3');

    inCollection = albumEntries[2].queryAll(By.css('div.bc-ce-intersection-collections a'));
    expect(inCollection.length).toBe(2);
    expect(inCollection[0].nativeElement.textContent).toBe('collection-2');
    expect(inCollection[1].nativeElement.textContent).toBe('collection-3');

  });

});
